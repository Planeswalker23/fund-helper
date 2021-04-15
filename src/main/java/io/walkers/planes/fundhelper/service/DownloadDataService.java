package io.walkers.planes.fundhelper.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.walkers.planes.fundhelper.config.FundDataSource;
import io.walkers.planes.fundhelper.dao.FundValueDao;
import io.walkers.planes.fundhelper.entity.dict.FundTypeDict;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import io.walkers.planes.fundhelper.entity.pojo.EastMoneyResult;
import io.walkers.planes.fundhelper.entity.pojo.SinaResult;
import io.walkers.planes.fundhelper.util.RestTemplateUtil;
import io.walkers.planes.fundhelper.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取数据服务层
 * based on {@link Jsoup}
 * based on {@link RestTemplate}
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class DownloadDataService {

    private static final String TITLE = "fundDetail-tit";
    private static final String INFO = "infoOfFund";
    @Resource
    private FundDataSource fundDataSource;
    @Resource
    private FundValueDao fundValueDao;

    public FundModel fetchFundByCode(String code) {
        if (!StringUtils.hasText(code)) {
            throw new RuntimeException("获取基金详情失败，原因：基金代码入参为空");
        }
        String path = fundDataSource.getDetailPathPrefix() + code + fundDataSource.getDetailPathSuffix();
        EastMoneyResult result = new EastMoneyResult();
        try {
            Document doc = Jsoup.connect(path).get();
            // 基金名称
            Node nameNode = doc.getElementsByClass(TITLE).get(0).childNode(0).childNode(0);
            result.setName(nameNode.outerHtml().replace("\n", ""));

            Node infoNode = doc.getElementsByClass(INFO).get(0).childNode(1).childNode(0);
            // 基金类型
            Node typeNode = infoNode.childNode(0).childNode(0).childNode(1).childNode(0);
            result.setType(typeNode.outerHtml());
            // 基金经理
            Node managerNode = infoNode.childNode(0).childNode(2).childNode(1).childNode(0);
            result.setManager(managerNode.outerHtml());
            // 成立日期
            Node establishDateNode = infoNode.childNode(1).childNode(0).childNode(1);
            result.setEstablishDate(establishDateNode.outerHtml().replace("：", ""));
        } catch (Exception e) {
            log.error("获取基金详情失败，路由为：{}，原因： {}", path, e.getMessage(), e);
            throw new RuntimeException(String.format("获取基金详情失败，请检查基金代码{%s}是否正确", code));
        }
        FundTypeDict fundType = FundTypeDict.containsValue(result.getType());
        return FundModel.builder()
                .name(result.getName())
                .code(code)
                .type(fundType.name())
                .manager(result.getManager())
                .establishDate(java.sql.Date.valueOf(result.getEstablishDate()))
                .build();
    }

    /**
     * 获取基金净值数据
     *
     * @param fund 基金信息
     * @return List
     */
    public List<FundValueModel> fetchFundDetails(FundModel fund) {
        return this.fetchFundDetails(fund, Integer.MAX_VALUE);
    }

    /**
     * 获取基金净值数据，指定最大页数
     *
     * @param fund 基金信息
     * @param page 页码
     * @return List
     */
    public List<FundValueModel> fetchFundDetails(FundModel fund, Integer page) {
        // 请求参数绑定
        Map<String, Object> params = Maps.newHashMap();
        params.put("symbol", fund.getCode());

        Map<String, FundValueModel> fundValueMapWithDateKey = Maps.newLinkedHashMapWithExpectedSize(1000);
        for (int i = 1; i <= page; i++) {
            // 设置页码
            params.put("page", i);
            // 获取数据
            SinaResult result = RestTemplateUtil.doGet(fundDataSource.getValuePath(), SinaResult.class, params);
            // 中断循环的判断
            if (result == null || result.isEmpty()) {
                break;
            }
            // 数据源实体 FundValueDetail 转换为 FundValueModel
            result.getData().forEach(detail -> {
                java.sql.Date valueDate = TimeUtil.string2JavSqlDate(detail.getFbrq());
                FundValueModel fundValue = FundValueModel.builder()
                        .fundId(fund.getId())
                        .value(new BigDecimal(detail.getLjjz()))
                        .valueDate(valueDate)
                        .build();
                // 基金成立日的日增长率为0
                if (fund.getEstablishDate().getTime() == valueDate.getTime()) {
                    fundValue.setIncreaseRate(BigDecimal.ZERO);
                }
                fundValueMapWithDateKey.put(detail.getFbrq(), fundValue);
            });
        }
        if (CollectionUtils.isEmpty(fundValueMapWithDateKey)) {
            return null;
        }
        // 查询该基金已存在的基金净值，返回结果过滤已存在的基金净值
        List<String> existFundValueDateList = fundValueDao.selectByFundId(fund.getId())
                .stream().map(fundValueModel -> fundValueModel.getValueDate().toString()).collect(Collectors.toList());
        List<FundValueModel> result = Lists.newArrayListWithExpectedSize(fundValueMapWithDateKey.size());
        fundValueMapWithDateKey.values().forEach(fundValueModel -> {
            if (!existFundValueDateList.contains(fundValueModel.getValueDate().toString())) {
                result.add(fundValueModel);
            }
        });
        return result;
    }
}
