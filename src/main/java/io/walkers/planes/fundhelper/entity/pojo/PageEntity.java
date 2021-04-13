package io.walkers.planes.fundhelper.entity.pojo;

import lombok.Data;

/**
 * 分页实体
 *
 * @author planeswalker23
 */
@Data
public class PageEntity {
    /**
     * 页码
     */
    private Integer pageNum = 1;
    /**
     * 每页数目
     */
    private Integer pageSize = 10;
}
