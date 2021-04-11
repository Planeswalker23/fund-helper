package io.walkers.planes.fundhelper.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.walkers.planes.fundhelper.config.FundDataSource;
import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.dao.FundValueDao;
import io.walkers.planes.fundhelper.dao.OptionalFundRelationDao;
import io.walkers.planes.fundhelper.entity.dict.FundTypeDict;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import io.walkers.planes.fundhelper.entity.model.OptionalFundRelationModel;
import io.walkers.planes.fundhelper.entity.pojo.PageEntity;
import io.walkers.planes.fundhelper.entity.pojo.SinaFundValueDetail;
import io.walkers.planes.fundhelper.entity.pojo.SinaResult;
import io.walkers.planes.fundhelper.listener.CalculateIncreaseRateEvent;
import io.walkers.planes.fundhelper.util.RestTemplateUtil;
import io.walkers.planes.fundhelper.util.SessionUtil;
import io.walkers.planes.fundhelper.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 执行 HTTP 请求服务
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class FundService {

    @Resource
    private FundDataSource fundDataSource;
    @Resource
    private FundDao fundDao;
    @Resource
    private FundValueDao fundValueDao;
    @Resource
    private OptionalFundRelationDao optionalFundRelationDao;
    @Resource
    private CrawlerService crawlerService;
    @Resource
    private ApplicationContext applicationContext;

    @Transactional(rollbackFor = Exception.class)
    public void createFundByCode(String code) {
        // 根据 code 查询基金实体
        FundModel fund = fundDao.selectByCode(code);
        if (fund != null) {
            log.info("Fund data of code {} already exist", code);
            return;
        }
        // 获取基金数据
        fund = crawlerService.getFundByCode(code);
        fundDao.insertSelective(fund);
        log.info("Fund data create successfully, code is {}", code);

        // 获取基金净值数据
        this.saveFundDetails(fund);
        log.info("Fund value data create successfully, code is {}", code);

        // 发布事件：计算日增长率
        applicationContext.publishEvent(new CalculateIncreaseRateEvent(fund.getId()));
    }

    /**
     * 获取基金净值数据并保存
     *
     * @param fund 基金信息
     */
    private void saveFundDetails(FundModel fund) {
        // 请求参数绑定
        Map<String, Object> params = Maps.newHashMap();
        params.put("symbol", fund.getCode());

        Map<String, FundValueModel> fundValueMapWithDateKey = Maps.newLinkedHashMapWithExpectedSize(1000);
        for (int i = 1; ; i++) {
            // 设置页码
            params.put("page", i);
            // 获取数据
            SinaResult result = RestTemplateUtil.doGet(fundDataSource.getValuePath(), SinaResult.class, params);
            // 中断循环的判断
            if (result == null || result.isEmpty()) {
                break;
            }
            this.buildFundValueBySinaFundDetail(fund, result.getData(), fundValueMapWithDateKey);
        }
        if (!CollectionUtils.isEmpty(fundValueMapWithDateKey)) {
            fundValueDao.insertBatchWithoutIncreaseRate(Lists.newArrayList(fundValueMapWithDateKey.values()));
        }
    }

    /**
     * 数据源实体 FundValueDetail 转换为 FundValueModel
     *
     * @param fund                    归属基金
     * @param sinaFundValueDetails    数据源
     * @param fundValueMapWithDateKey 转换结果
     */
    private void buildFundValueBySinaFundDetail(FundModel fund, List<SinaFundValueDetail> sinaFundValueDetails, Map<String, FundValueModel> fundValueMapWithDateKey) {
        sinaFundValueDetails.forEach(detail -> {
            FundValueModel fundValue = FundValueModel.builder()
                    .fundId(fund.getId())
                    .value(new BigDecimal(detail.getLjjz()))
                    .build();
            fundValue.setValueDate(TimeUtil.string2Date(detail.getFbrq()));
            fundValueMapWithDateKey.put(detail.getFbrq(), fundValue);
        });
    }

    /**
     * 查询所有 FundModel
     *
     * @param pageEntity 分页条件
     * @return PageInfo
     */
    public PageInfo<FundModel> selectAll(PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNum(), pageEntity.getPageSize());
        return new PageInfo<>(this.postFormatData(fundDao.selectAll()));
    }

    /**
     * 查询所有自选基金
     *
     * @param pageEntity 分页条件
     * @return PageInfo
     */
    public PageInfo<FundModel> selectAllOptionalFund(PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNum(), pageEntity.getPageSize());
        List<OptionalFundRelationModel> relations = optionalFundRelationDao.selectByVirtualUserId(SessionUtil.getLoginUser().getId());
        List<Long> fundIds = Lists.transform(relations, OptionalFundRelationModel::getFundId);
        List<FundModel> optionalFunds = CollectionUtils.isEmpty(fundIds) ? Lists.newArrayList() : fundDao.selectByIds(fundIds);
        return new PageInfo<>(this.postFormatData(optionalFunds));
    }

    /**
     * 根据关键词查询所有 FundModel
     *
     * @param keyword    关键词
     * @param pageEntity 分页条件
     * @return PageInfo
     */
    public PageInfo<FundModel> selectAllByKeyword(String keyword, PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNum(), pageEntity.getPageSize());
        return new PageInfo<>(this.postFormatData(fundDao.selectByKeyword(keyword)));
    }

    /**
     * 后置处理：格式化
     *
     * @param sourceData 源数据
     * @return List
     */
    private List<FundModel> postFormatData(List<FundModel> sourceData) {
        if (CollectionUtils.isEmpty(sourceData)) {
            return sourceData;
        }
        sourceData.forEach(source -> {
            // 基金类型展示为中文
            source.setType(FundTypeDict.valueOf(source.getType()).getLabel());
        });
        return sourceData;
    }
}
