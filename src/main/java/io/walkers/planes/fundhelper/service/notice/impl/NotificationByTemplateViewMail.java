package io.walkers.planes.fundhelper.service.notice.impl;

import io.walkers.planes.fundhelper.service.notice.NoticeMessage;
import io.walkers.planes.fundhelper.service.notice.NoticeMethod;
import io.walkers.planes.fundhelper.service.notice.Notification;
import io.walkers.planes.fundhelper.service.notice.NotificationSelector;
import io.walkers.planes.fundhelper.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 基于邮件通知，仅会被 {@link NotificationSelector} 使用
 *
 * @author planeswalker23
 */
@Slf4j
@Service(value = NoticeMethod.TEMPLATE_VIEW_MAIL)
public class NotificationByTemplateViewMail implements Notification {

    @Value("${spring.mail.username}")
    private String sender;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private TemplateEngine templateEngine;

    @Override
    public Boolean match(String noticeMethod) {
        return NoticeMethod.TEMPLATE_VIEW_MAIL.equals(noticeMethod);
    }

    @Override
    public Boolean notice(NoticeMessage noticeMessage) {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = this.convert2MimeMessage(noticeMessage);
        } catch (MessagingException e) {
            log.error("创建复杂邮件对象失败, 原因: {}", e.getMessage());
            return Boolean.FALSE;
        }
        mailSender.send(mimeMessage);
        log.info("视图模板邮件发送成功 -> 内容: {}", JacksonUtil.toJson(mimeMessage));
        return Boolean.TRUE;
    }

    /**
     * 类型转换
     *
     * @param noticeMessage 通知信息
     * @return MimeMessage
     */
    private MimeMessage convert2MimeMessage(NoticeMessage noticeMessage) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(noticeMessage.getTitle());
        helper.setFrom(sender);
        helper.setTo(noticeMessage.getReceiver());
        helper.setSentDate(new Date());

        // 引入 Thymeleaf 的 Context
        Context context = new Context();
        // 设置模板中的变量
        context.setVariable("from", sender);
        context.setVariable("to", noticeMessage.getReceiver());
        context.setVariable("context", noticeMessage.getContent());
        context.setVariable("title", noticeMessage.getTitle());
        String process = templateEngine.process("mail-template.html", context);
        helper.setText(process,true);
        return mimeMessage;
    }
}
