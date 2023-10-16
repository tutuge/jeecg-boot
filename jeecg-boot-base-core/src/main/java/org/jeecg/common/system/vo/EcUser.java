package org.jeecg.common.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcUser {
    private Integer ecuId;//主键ID

    /**
     * 关联sysUser
     */
    private String userId;

    private Integer ecCompanyId;//公司ID

    private Integer typeId;//管理员类型

    private Boolean startType;//是否启用

    private String ecUsername;//用户名称

    private String code;//代号

    private String ecPhone;//手机号

    private String ecPwd;//密码

    private String ecHeadimg;//头像

    private Integer sex;//性别 0 保密 1 男 2 女

    private String introduction;//简介

    private BigDecimal profit;//利润

    private Long addTime;//添加时间
}
