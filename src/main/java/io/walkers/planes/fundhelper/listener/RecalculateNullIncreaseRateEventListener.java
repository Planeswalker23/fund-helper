package io.walkers.planes.fundhelper.listener;

import com.google.common.collect.Lists;
import io.walkers.planes.fundhelper.dao.FundValueDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import io.walkers.planes.fundhelper.service.FundValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 补偿计算日增长率事件监听器
 *
 * @author planeswalker23
 */
@Async
@Slf4j
@Service
public class RecalculateNullIncreaseRateEventListener implements ApplicationListener<RecalculateNullIncreaseRateEvent> {

    @Resource
    private FundValueDao fundValueDao;
    @Resource
    private FundValueService fundValueService;

    @Override
    public void onApplicationEvent(RecalculateNullIncreaseRateEvent event) {
        FundModel fund = event.getFund();
        if (fund == null) {
            log.warn("补偿计算日增长率事件结束，原因：事件入参为空");
            return;
        }
        // 查询日增长率为空的 FundValueModel
        List<FundValueModel> nullIncreaseRateFundValues = fundValueDao.selectNullIncreaseRateByFundId(fund.getId());
        if (!CollectionUtils.isEmpty(nullIncreaseRateFundValues)) {
            nullIncreaseRateFundValues.forEach(targetFundValue -> {
                // 查询前一天基金净值
                FundValueModel formerOneDayFundValue = fundValueDao.selectByFundIdValueDate(targetFundValue.getFundId(), targetFundValue.getValueDate());
                // 计算日增长率(入参 List 有顺序 -> 日期倒叙)
                fundValueService.calculateIncreaseRate(Lists.newArrayList(targetFundValue, formerOneDayFundValue));
            });
        }
        fundValueDao.batchUpdateIncreaseRateById(nullIncreaseRateFundValues);
        log.info("补偿计算日增长率事件结束，代码为{}的日增长率计算成功", fund.getCode());
    }
}
