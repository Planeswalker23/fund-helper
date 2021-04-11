package io.walkers.planes.fundhelper.listener;

import io.walkers.planes.fundhelper.dao.FundValueDao;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 计算日增长率事件监听器
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class CalculateIncreaseRateListener implements ApplicationListener<CalculateIncreaseRateEvent> {

    @Resource
    private FundValueDao fundValueDao;

    @Override
    public void onApplicationEvent(CalculateIncreaseRateEvent event) {
        log.info("Start to calculate increaseRate of fund, id: {}", event.getSource());
        this.execute((Long) event.getSource());
        log.info("End with calculate increaseRate of fund, id: {}", event.getSource());
    }

    /**
     * 执行计算日增长率的方法
     *
     * @param fundId 基金ID
     */
    private void execute(Long fundId) {
        List<FundValueModel> fundValues = fundValueDao.selectByFundId(fundId);
        if (!CollectionUtils.isEmpty(fundValues)) {
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
            fundValueDao.batchUpdateIncreaseRateById(fundValues);
        }
    }
}
