package io.walkers.planes.fundhelper.service;

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
     * 发送邮件
     *
     * @param emailDto 邮件入参
     */
    public void sendSimpleEmail(Object emailDto) {
        log.info("开始发送邮件 -> 传入邮件实体类参数内容: {}", JacksonUtil.toJson(emailDto));
        // 发送邮件，新建邮件对象
        SimpleMailMessage newMessage = new SimpleMailMessage();
        // 设置邮件主题
        newMessage.setSubject(emailDto.getTitle() + "，来自用户邮箱: " + emailDto.getSender());
        // 设置邮件内容
        newMessage.setText(emailDto.getContent());
        // 设置发件人
        newMessage.setFrom(sender);
        // 设置收件人
        newMessage.setTo(emailDto.getAccepter());
        mailSender.send(newMessage);
        log.info("邮件发送成功 -> 邮件实体类内容: {}", JacksonUtil.toJson(newMessage));
    }
}
