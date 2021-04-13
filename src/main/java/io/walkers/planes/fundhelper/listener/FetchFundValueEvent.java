package io.walkers.planes.fundhelper.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 获取基金净值, 计算日增长率事件
 *
 * @author planeswalker23
 */
public class FetchFundValueEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public FetchFundValueEvent(Object source) {
        super(source);
    }
}
