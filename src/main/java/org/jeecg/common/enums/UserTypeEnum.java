package org.jeecg.common.enums;

/**
 * 1是后台管理员 2是平台用户 3是普通用户
 */
public enum UserTypeEnum {

    ADMIN(1),
    PLATFORM(2),
    USER(3);


    /**
     * 用户类型
     */
    Integer userType;

    UserTypeEnum(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserType() {
        return userType;
    }
}
