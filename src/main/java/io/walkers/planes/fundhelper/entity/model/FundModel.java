package io.walkers.planes.fundhelper.entity.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Model of Fund
 *
 * @author planeswalker23
 */
@Data
@Builder
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
    @NotBlank(message = "Fund code can not be empty`")
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
     * @see io.walkers.planes.fundhelper.entity.dict.FundTypeDict
     */
    private String type;
    /**
     * 基金经理
     */
    private String manager;
}
