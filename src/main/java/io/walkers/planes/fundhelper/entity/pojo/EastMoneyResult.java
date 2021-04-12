package io.walkers.planes.fundhelper.entity.pojo;

import lombok.Data;

/**
 * 天天基金爬取结果
 *
 * @author planeswalker23
 */
@Data
public class EastMoneyResult {
    /**
     * 基金名称
     */
    private String name;
    /**
     * 基金类型
     */
    private String type;
    /**
     * 基金经理
     */
    private String manager;
    /**
     * 成立日期
     */
    private String establishDate;
}
