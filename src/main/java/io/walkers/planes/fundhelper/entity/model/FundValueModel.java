package io.walkers.planes.fundhelper.entity.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Model of Fund Value
 *
 * @author planeswalker23
 */
@Data
@Builder
public class FundValueModel implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private java.util.Date createDate;
    /**
     * 更新时间
     */
    private java.util.Date updateDate;
    /**
     * Fund Model id
     */
    private Long fundId;
    /**
     * 净值日期
     */
    private java.sql.Date valueDate;
    /**
     * 净值
     */
    private BigDecimal value;
    /**
     * 日增长率
     */
    private BigDecimal increaseRate;
}
