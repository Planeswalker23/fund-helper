package io.walkers.planes.fundhelper.service;

import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 基金净值服务
 *
 * @author planeswalker23
 * @see FundValueModel
 */
@Slf4j
@Service
public class FundValueService {

    /**
     * 计算日增长率的方法
     *
     * @param fundValues 基金详情
     */
    public void calculateIncreaseRate(List<FundValueModel> fundValues) {
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
