package org.jeecg.common.enums;

import lombok.Getter;

/**
 * 1是后台管理员 2是平台用户 3是普通用户
 */
@Getter
public enum UserTypeEnum {
    //0是后台管理员 1是平台用户主账号 2是平台用户的子账号 3 游客
    ADMIN(0),
    PLATFORM(1),
    USER(2),
    VISITOR(3),
    ;


    /**
     * 用户类型
     */
    final Integer userType;

    UserTypeEnum(Integer userType) {
        this.userType = userType;
    }

}
