package io.walkers.planes.fundhelper.entity.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 页面信息
 *
 * @author planeswalker23
 */
@Data
@Builder
public class PageModel implements Serializable {
    /**
     * 页面代码(页面路径，文件名)
     */
    private String pageCode;
    /**
     * 页面名称
     */
    private String pageName;
    /**
     * icon 样式
     */
    private String iconClass;
}
