package io.walkers.planes.fundhelper.service.mail;

import io.walkers.planes.fundhelper.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 邮件服务层
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String sender;
    @Resource
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     *
     * @param sendMail 邮件入参
     */
    public void sendSimpleEmail(SendMail sendMail) {
        // 发送邮件，新建邮件对象
        SimpleMailMessage newMessage = new SimpleMailMessage();
        newMessage.setSubject(sendMail.getTitle());
        newMessage.setText(sendMail.getContent());
        newMessage.setFrom(sender);
        newMessage.setTo(sendMail.getReceiver());
        mailSender.send(newMessage);
        log.info("邮件发送成功 -> 邮件实体类内容: {}", JacksonUtil.toJson(newMessage));
    }
}
