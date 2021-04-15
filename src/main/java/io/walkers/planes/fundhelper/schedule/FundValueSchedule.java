package io.walkers.planes.fundhelper.schedule;

import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.listener.DownloadFundValueEvent;
import io.walkers.planes.fundhelper.listener.RecalculateNullIncreaseRateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基金净值定时任务
 *
 * @author planeswalker23
 */
@Slf4j
@Component
public class FundValueSchedule {

    @Resource
    private FundDao fundDao;
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 新增最新的基金净值，触发时机，每天00:00
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void dailyAddFundValue() {
        log.info("Scheduled of Daily job started, publish event of DownloadFundValueEvent.");
        List<FundModel> funds = fundDao.selectAll();
        if (CollectionUtils.isEmpty(funds)) {
            return;
        }
        funds.forEach(fund -> applicationContext.publishEvent(new DownloadFundValueEvent(this, fund, 1)));
    }

    /**
     * 更新日增长率为 null 的基金净值，触发时机每天00:01
     */
    @Scheduled(cron = "0 1 0 */1 * ?")
    public void dailyUpdateNullIncreaseRate() {
        log.info("Scheduled of Daily job started, publish event of RecalculateNullIncreaseRateEvent.");
        List<FundModel> funds = fundDao.selectAll();
        if (CollectionUtils.isEmpty(funds)) {
            return;
        }
        funds.forEach(fund -> applicationContext.publishEvent(new RecalculateNullIncreaseRateEvent(this, fund)));
    }
}
