package io.walkers.planes.fundhelper.service.notice.impl;

import io.walkers.planes.fundhelper.dao.VirtualUserDao;
import io.walkers.planes.fundhelper.dao.WebsiteNoticeDao;
import io.walkers.planes.fundhelper.entity.model.VirtualUserModel;
import io.walkers.planes.fundhelper.entity.model.WebsiteNoticeModel;
import io.walkers.planes.fundhelper.service.notice.NoticeMessage;
import io.walkers.planes.fundhelper.service.notice.NoticeMethod;
import io.walkers.planes.fundhelper.service.notice.Notification;
import io.walkers.planes.fundhelper.service.notice.NotificationSelector;
import io.walkers.planes.fundhelper.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 网页通知，仅会被 {@link NotificationSelector} 使用
 *
 * @author planeswalker23
 */
@Slf4j
@Service(value = NoticeMethod.WEBSITE)
public class NotificationByWebsite implements Notification {

    @Resource
    private VirtualUserDao virtualUserDao;
    @Resource
    private WebsiteNoticeDao websiteNoticeDao;

    @Override
    public Boolean match(String noticeMethod) {
        return NoticeMethod.WEBSITE.equals(noticeMethod);
    }

    @Override
    public Boolean notice(NoticeMessage noticeMessage) {
        // 根据邮箱查询用户
        VirtualUserModel userByMailbox = virtualUserDao.selectByMailbox(noticeMessage.getReceiver());
        WebsiteNoticeModel websiteNotice = WebsiteNoticeModel.builder()
                .title(noticeMessage.getTitle())
                .content(noticeMessage.getContent())
                .virtualUserId(userByMailbox.getId())
                .readStatus(Boolean.FALSE)
                .build();
        websiteNotice.setActiveDate(noticeMessage.getActiveDate());
        websiteNoticeDao.insert(websiteNotice);
        log.info("网页通知成功 -> 内容: {}", JacksonUtil.toJson(noticeMessage));
        return Boolean.TRUE;
    }
}
