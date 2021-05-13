package io.walkers.planes.fundhelper.service.task;

/**
 * 任务执行器接口
 *
 * @author planeswalker23
 */
public interface TaskHandler {

    /**
     * 执行任务
     *
     * @return Boolean
     */
    Boolean handle();
}
