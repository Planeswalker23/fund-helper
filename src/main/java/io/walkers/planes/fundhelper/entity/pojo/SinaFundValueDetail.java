package io.walkers.planes.fundhelper.entity.pojo;

import lombok.Data;

/**
 * Fund Value Detail in Sina
 *
 * @author planeswalker23
 */
@Data
public class SinaFundValueDetail {
    /**
     * 发布日期
     */
    private String fbrq;
    /**
     * 累计净值
     */
    private String ljjz;
}
