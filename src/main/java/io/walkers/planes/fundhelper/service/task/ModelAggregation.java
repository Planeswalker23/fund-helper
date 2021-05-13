package io.walkers.planes.fundhelper.service.task;

import io.walkers.planes.fundhelper.util.TimeUtil;

import java.util.Date;

/**
 * 需要具备秒级触发能力的模型需要实现该接口
 *
 * @author planeswalker23
 */
public interface ModelAggregation extends ActivateCondition {

    /**
     * 获取激活时间
     *
     * @return Date
     */
    @Override
    Date activeDate();

    /**
     * 是否满足条件
     *
     * @return Boolean
     */
    @Override
    default Boolean match() {
        Date startOfDay = TimeUtil.startOfDay(this.activeDate());
        // 应已激活（今天第一秒大于激活时间）
        return startOfDay.after(this.activeDate());
    }

    /**
     * 执行任务
     *
     * @param taskHandler 任务执行器
     */
    default void handle(TaskHandler taskHandler) {
        taskHandler.handle();
    }
}
