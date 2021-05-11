package io.walkers.planes.fundhelper.service.mail;

import lombok.Data;

/**
 * 发送邮件信息类
 *
 * @author planeswalker23
 */
@Data
public class SendMail {
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 标题
     */
    private String title;
    /**
     * 邮件内容
     */
    private String content;
}
