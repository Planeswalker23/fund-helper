package io.walkers.planes.fundhelper.entity.model;

import lombok.Data;

/**
 * 自选基金与用户关联表
 *
 * @author 范逸东
 */
@Data
public class OptionalFundRelationModel {
    /**
     * 用户ID
     */
    private Long virtualUserId;
    /**
     * 基金ID
     */
    private Long fundId;
    /**
     * 创建时间
     */
    private java.util.Date createDate;
    /**
     * 更新时间
     */
    private java.util.Date updateDate;
}
