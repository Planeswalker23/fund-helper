package io.walkers.planes.fundhelper.service.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 通知选择器，业务方使用该类进行通知
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class NotificationSelector {

    @Resource
    private Map<String, Notification> notificationMap;

    /**
     * 模板方法，执行通知方法
     * @param noticeMessage 通知消息体
     * @return Boolean
     */
    public Boolean doNotice(NoticeMessage noticeMessage) {
        // 防止依赖注入为空
        if (notificationMap == null) {
            return Boolean.FALSE;
        }
        Notification notification = notificationMap.get(noticeMessage.getNoticeMethod());
        // 校验通知方式存在且匹配
        if (notification != null && notification.match(noticeMessage.getNoticeMethod())) {
            // 执行通知逻辑
            return notification.notice(noticeMessage);
        }
        log.warn("通知方式[{}]非法，请检查通知方式实现类: [{}]", noticeMessage.getNoticeMethod(), notificationMap);
        return Boolean.FALSE;
    }
}
