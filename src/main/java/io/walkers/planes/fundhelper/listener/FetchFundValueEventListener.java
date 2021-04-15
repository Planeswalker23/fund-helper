package io.walkers.planes.fundhelper.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.walkers.planes.fundhelper.config.FundDataSource;
import io.walkers.planes.fundhelper.dao.FundValueDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import io.walkers.planes.fundhelper.entity.pojo.SinaFundValueDetail;
import io.walkers.planes.fundhelper.entity.pojo.SinaResult;
import io.walkers.planes.fundhelper.util.RestTemplateUtil;
import io.walkers.planes.fundhelper.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 获取基金净值, 计算日增长率事件监听器
 *
 * @author planeswalker23
 */
@Async
@Slf4j
@Service
public class FetchFundValueEventListener implements ApplicationListener<FetchFundValueEvent> {

    @Resource
    private FundValueDao fundValueDao;
    @Resource
    private FundDataSource fundDataSource;

    @Override
    public void onApplicationEvent(FetchFundValueEvent event) {
        FundModel fund = (FundModel) event.getSource();
        if (fund == null) {
            log.warn("Parameter fund is null");
            return;
        }
        // 获取基金净值
        List<FundValueModel> fundValues = this.fetchFundDetails(fund);

        // 计算日增长率
        this.calculateIncreaseRate(fundValues);

        // 持久化数据
        fundValueDao.insertBatchWithoutIncreaseRate(fundValues);

        log.info("End with fetch fund value event, Fund value data create successfully, fund code: {}", fund.getCode());
    }

    /**
     * 获取基金净值数据
     *
     * @param fund 基金信息
     * @return List
     */
    private List<FundValueModel> fetchFundDetails(FundModel fund) {
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
        return Lists.newArrayList(fundValueMapWithDateKey.values());
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
                    .valueDate(TimeUtil.string2JavSqlDate(detail.getFbrq()))
                    .build();
            fundValueMapWithDateKey.put(detail.getFbrq(), fundValue);
        });
    }

    /**
     * 计算日增长率的方法
     *
     * @param fundValues 基金详情
     */
    private void calculateIncreaseRate(List<FundValueModel> fundValues) {
        for (int i = 0; i < fundValues.size() - 1; i++) {
            FundValueModel currentDateFundValue = fundValues.get(i);
            FundValueModel formerDateFundValue = fundValues.get(i + 1);
            // (今日净值 - 昨日净值) / 昨日净值 * 100
            BigDecimal increaseRate = currentDateFundValue.getValue()
                    .subtract(formerDateFundValue.getValue())
                    .divide(formerDateFundValue.getValue(), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            currentDateFundValue.setIncreaseRate(increaseRate);
        }
    }
}
