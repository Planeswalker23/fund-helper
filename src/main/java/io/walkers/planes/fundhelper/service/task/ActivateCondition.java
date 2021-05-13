package io.walkers.planes.fundhelper.service.task;

import java.util.Date;

/**
 * 满足激活条件的接口
 *
 * @author planeswalker23
 */
public interface ActivateCondition {

    /**
     * 是否满足条件
     *
     * @return Boolean
     */
    Boolean match();

    /**
     * 获取激活时间
     * @return Date
     */
    Date activeDate();
}
