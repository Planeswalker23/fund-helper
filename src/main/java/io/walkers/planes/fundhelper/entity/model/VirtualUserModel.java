package io.walkers.planes.fundhelper.entity.model;

import io.walkers.planes.fundhelper.entity.dict.MessageDict;
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
    @NotBlank(message = MessageDict.ACCOUNT_CAN_NOT_BE_NULL)
    private String account;
    /**
     * 密码
     */
    @NotBlank(message = MessageDict.PASSWORD_CAN_NOT_BE_NULL)
    private String password;
    /**
     * 创建时间
     */
    private java.util.Date createDate;
    /**
     * 更新时间
     */
    private java.util.Date updateDate;
    /**
     * 邮箱
     */
    private String mailbox;
}
