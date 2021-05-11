package io.walkers.planes.fundhelper.service.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 通知选择器，业务方使用该类进行通知
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class NotificationSelector implements ApplicationContextAware {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 模板方法，执行通知方法
     * @param noticeMessage 通知消息体
     * @return Boolean
     */
    public Boolean doNotice(NoticeMessage noticeMessage) {
        try {
            Notification notification = applicationContext.getBean(noticeMessage.getNoticeMethod(), Notification.class);
            if (notification.match(noticeMessage.getNoticeMethod())) {
                return notification.notice(noticeMessage);
            }
        } catch (BeansException e) {
            log.warn("通知方式[{}]非法，请检查", noticeMessage.getNoticeMethod());
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
