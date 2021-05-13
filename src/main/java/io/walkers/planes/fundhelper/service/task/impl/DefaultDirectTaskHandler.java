package io.walkers.planes.fundhelper.service.task.impl;

import io.walkers.planes.fundhelper.service.task.TaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 默认直接任务执行器
 *
 * @author planeswalker23
 */
@Slf4j
@Component
public class DefaultDirectTaskHandler implements TaskHandler {
    @Override
    public Boolean handle() {
        log.info("直接任务执行器开始执行");
        return null;
    }
}
