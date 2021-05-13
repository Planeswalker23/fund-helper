package io.walkers.planes.fundhelper.service.task.processor;

import io.walkers.planes.fundhelper.entity.model.WebsiteNoticeModel;
import io.walkers.planes.fundhelper.service.task.TaskHandler;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 默认任务模板处理器
 *
 * @author planeswalker23
 */
public class TaskProcessor {

    /**
     * 执行处理方法
     *
     * @return Boolean
     */
    public static void process(List<WebsiteNoticeModel> data, TaskHandler directTaskHandler, TaskHandler delayTaskHandler) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        data.forEach(model -> {
            // 应已激活的数据：直接任务执行器
            // 即将激活的数据：插入 Redis 过期键，监听过期事件
            TaskHandler taskHandler = model.match() ? directTaskHandler : delayTaskHandler;
            model.handle(taskHandler);
        });
    }
}
