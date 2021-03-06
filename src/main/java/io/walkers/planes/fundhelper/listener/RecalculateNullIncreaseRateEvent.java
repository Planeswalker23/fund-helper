package io.walkers.planes.fundhelper.listener;

import io.walkers.planes.fundhelper.entity.model.FundModel;
import org.springframework.context.ApplicationEvent;

/**
 * 补偿计算日增长率事件
 * 计算为 null 的基金净值
 *
 * @author planeswalker23
 */
public class RecalculateNullIncreaseRateEvent extends ApplicationEvent {

    /**
     * 目标基金信息
     */
    private final FundModel fund;

    public RecalculateNullIncreaseRateEvent(Object source, FundModel fund) {
        super(source);
        this.fund = fund;
    }

    public FundModel getFund() {
        return fund;
    }
}
