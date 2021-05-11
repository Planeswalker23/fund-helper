package io.walkers.planes.fundhelper.service.notice;

import lombok.Data;

/**
 * 通知信息实体类
 *
 * @author planeswalker23
 */
@Data
public class NoticeMessage {
    /**
     * 通知接收方
     */
    private String receiver;
    /**
     * 通知标题
     */
    private String title;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 通知方式
     * {@link NoticeMethod}
     */
    private String noticeMethod;
}
