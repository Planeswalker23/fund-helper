package io.walkers.planes.fundhelper.service.notice;

/**
 * 通知接口
 *
 * @author planeswalker23
 */
public interface Notification {

    /**
     * 匹配通知方式
     *
     * @param noticeMethod 通知方式
     * @return Boolean
     */
    Boolean match(String noticeMethod);

    /**
     * 通知
     *
     * @param noticeMessage 通知信息
     * @return Boolean
     */
    Boolean notice(NoticeMessage noticeMessage);
}
