package io.walkers.planes.fundhelper.schedule;

import io.walkers.planes.fundhelper.dao.WebsiteNoticeDao;
import io.walkers.planes.fundhelper.entity.model.WebsiteNoticeModel;
import io.walkers.planes.fundhelper.service.task.impl.DefaultDelayTaskHandler;
import io.walkers.planes.fundhelper.service.task.impl.DefaultDirectTaskHandler;
import io.walkers.planes.fundhelper.service.task.processor.TaskProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务：激活任务（每日执行）
 *
 * @author planeswalker23
 */
@Slf4j
@Component
public class DailyTaskProcessorSchedule {

    @Resource
    private DefaultDirectTaskHandler defaultDirectTaskHandler;
    @Resource
    private DefaultDelayTaskHandler defaultDelayTaskHandler;
    @Resource
    private WebsiteNoticeDao websiteNoticeDao;

    /**
     * 触发待激活任务
     * 触发时机：每天00:00
     */
    @Scheduled(cron = "1 * * * * ?")
    public void dailyActiveTask() {
        List<WebsiteNoticeModel> data = websiteNoticeDao.selectActivatedWebsiteNotices();
        TaskProcessor.process(data, defaultDirectTaskHandler, defaultDelayTaskHandler);
    }
}
