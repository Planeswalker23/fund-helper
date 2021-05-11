package io.walkers.planes.fundhelper.service.notice.impl;

import io.walkers.planes.fundhelper.service.notice.NoticeMessage;
import io.walkers.planes.fundhelper.service.notice.NoticeMethod;
import io.walkers.planes.fundhelper.service.notice.Notification;
import io.walkers.planes.fundhelper.service.notice.NotificationSelector;
import io.walkers.planes.fundhelper.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基于邮件通知，仅会被 {@link NotificationSelector} 使用
 *
 * @author planeswalker23
 */
@Slf4j
@Service(value = NoticeMethod.MAIL)
public class NotificationByMail implements Notification {

    @Value("${spring.mail.username}")
    private String sender;
    @Resource
    private JavaMailSender mailSender;

    @Override
    public Boolean match(String noticeMethod) {
        return NoticeMethod.MAIL.equals(noticeMethod);
    }

    @Override
    public Boolean notice(NoticeMessage noticeMessage) {
        SimpleMailMessage simpleMailMessage = this.convert2SimpleMailMessage(noticeMessage);
        mailSender.send(simpleMailMessage);
        log.info("邮件发送成功 -> 邮件实体类内容: {}", JacksonUtil.toJson(simpleMailMessage));
        return Boolean.TRUE;
    }

    /**
     * 类型转换
     *
     * @param noticeMessage 通知信息
     * @return SimpleMailMessage
     */
    private SimpleMailMessage convert2SimpleMailMessage(NoticeMessage noticeMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(noticeMessage.getTitle());
        simpleMailMessage.setText(noticeMessage.getContent());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(noticeMessage.getReceiver());
        return simpleMailMessage;
    }
}
