package io.walkers.planes.fundhelper.service.notice;

/**
 * 通知方式枚举类
 *
 * @author 范逸东
 */
public interface NoticeMethod {

    /**
     * 邮件
     */
    String MAIL = "Mail";
    /**
     * 手机短信
     */
    String PHONE_NOTE = "PhoneNote";
    /**
     * 手机电话
     */
    String PHONE_CALL = "PhoneCall";
}
