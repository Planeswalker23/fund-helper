package io.walkers.planes.fundhelper.entity.model;

import io.walkers.planes.fundhelper.service.task.ModelAggregation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 网页通知模型
 *
 * @author planeswalker23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteNoticeModel implements Serializable, ModelAggregation {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long virtualUserId;
    /**
     * 通知标题
     */
    private String title;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 阅读状态
     * False-0-未读
     * True-1-已读
     */
    private Boolean readStatus;
    /**
     * 创建时间
     */
    private java.util.Date createDate;
    /**
     * 更新时间
     */
    private java.util.Date updateDate;
    /**
     * 激活时间
     */
    private java.util.Date activeDate;

    @Override
    public Date activeDate() {
        return this.activeDate;
    }
}
