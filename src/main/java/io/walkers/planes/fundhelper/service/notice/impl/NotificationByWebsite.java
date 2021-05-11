package io.walkers.planes.fundhelper.service.notice.impl;

import io.walkers.planes.fundhelper.service.notice.NoticeMessage;
import io.walkers.planes.fundhelper.service.notice.NoticeMethod;
import io.walkers.planes.fundhelper.service.notice.Notification;
import io.walkers.planes.fundhelper.service.notice.NotificationSelector;
import io.walkers.planes.fundhelper.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 网页通知，仅会被 {@link NotificationSelector} 使用
 *
 * @author planeswalker23
 */
@Slf4j
@Service(value = NoticeMethod.WEBSITE)
public class NotificationByWebsite implements Notification {

    @Override
    public Boolean match(String noticeMethod) {
        return NoticeMethod.WEBSITE.equals(noticeMethod);
    }

    @Override
    public Boolean notice(NoticeMessage noticeMessage) {
        // todo 网页通知实现
        log.info("网页通知成功 -> 内容: {}", JacksonUtil.toJson(noticeMessage));
        return Boolean.TRUE;
    }
}
