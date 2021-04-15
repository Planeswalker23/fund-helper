package io.walkers.planes.fundhelper.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 虚拟用户模型
 *
 * @author planeswalker23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualUserModel implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 账户
     */
    @NotBlank(message = "账户不能为空")
    private String account;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 创建时间
     */
    private java.util.Date createDate;
    /**
     * 更新时间
     */
    private java.util.Date updateDate;
}
