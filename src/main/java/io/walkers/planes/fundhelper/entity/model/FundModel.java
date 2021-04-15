package io.walkers.planes.fundhelper.entity.model;

import io.walkers.planes.fundhelper.entity.dict.FundTypeDict;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Model of Fund
 *
 * @author planeswalker23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundModel implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 代码
     */
    private String code;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 基金类型
     *
     * @see FundTypeDict
     */
    private String type;
    /**
     * 基金经理
     */
    private String manager;
    /**
     * 成立日期
     */
    private java.sql.Date establishDate;
}
