package io.walkers.planes.fundhelper.service.notice;

/**
 * 通知方式枚举类
 *
 * @author planeswalker23
 */
public interface NoticeMethod {

    /**
     * 简单邮件
     */
    String SIMPLE_MAIL = "SimpleMail";
    /**
     * 视图模板邮件
     */
    String TEMPLATE_VIEW_MAIL = "TemplateViewMail";
    /**
     * 网页
     */
    String WEBSITE = "Website";
    /**
     * 手机短信
     */
    String PHONE_NOTE = "PhoneNote";
    /**
     * 手机电话
     */
    String PHONE_CALL = "PhoneCall";
}
