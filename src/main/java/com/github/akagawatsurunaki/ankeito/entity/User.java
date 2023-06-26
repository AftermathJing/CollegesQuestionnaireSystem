package com.github.akagawatsurunaki.ankeito.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserRole;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserStatus;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class User extends Model<User> {

    /**
     * 用户UUID, 最大40个字符
     */
    String id;

    /**
     * 用户名, 最大36个字符
     */
    String username;

    /**
     * 密码, 最大20个字符
     */
    String password;

    /**
     * 开始时间
     */
    Date startTime;

    /**
     * 截止时间
     */
    Date stopTime;

    /**
     * 用户的角色
     */
    UserRole userRole;

    /**
     * 是否启用
     */
    UserStatus userStatus;

    /**
     * 创建人
     */
    String createdBy;

    /**
     * 创建时间
     */
    Date creationTime;

    /**
     * 最后修改人
     */
    String lastUpdatedBy;

    /**
     * 最后修改时间
     */
    Date lastUpdateTime;

}
