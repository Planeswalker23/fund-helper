package io.walkers.planes.fundhelper.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 计算日增长率时间
 *
 * @author planeswalker23
 */
public class CalculateIncreaseRateEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public CalculateIncreaseRateEvent(Object source) {
        super(source);
    }
}
