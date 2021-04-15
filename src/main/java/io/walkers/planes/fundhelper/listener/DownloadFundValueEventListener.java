package io.walkers.planes.fundhelper.listener;

import io.walkers.planes.fundhelper.dao.FundValueDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import io.walkers.planes.fundhelper.service.DownloadDataService;
import io.walkers.planes.fundhelper.service.FundValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取基金净值事件监听器
 *
 * @author planeswalker23
 */
@Async
@Slf4j
@Service
public class DownloadFundValueEventListener implements ApplicationListener<DownloadFundValueEvent> {

    @Resource
    private FundValueDao fundValueDao;
    @Resource
    private DownloadDataService downloadDataService;
    @Resource
    private FundValueService fundValueService;

    @Override
    public void onApplicationEvent(DownloadFundValueEvent event) {
        FundModel fund = event.getFund();
        if (fund == null) {
            log.warn("End with fetch fund value event, caused by empty parameter of fund");
            return;
        }
        // 获取基金净值
        List<FundValueModel> fundValues = downloadDataService.fetchFundDetails(fund, event.getPage());

        // 防止插入空列表导致 SQLException
        if (CollectionUtils.isEmpty(fundValues)) {
            return;
        }

        // 计算日增长率
        fundValueService.calculateIncreaseRate(fundValues);

        // 持久化数据
        fundValueDao.insertBatchWithoutIncreaseRate(fundValues);

        log.info("End with fetch fund value event, fund value data create successfully, fund code: {}", fund.getCode());
    }
}
