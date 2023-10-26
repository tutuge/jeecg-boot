package org.jeecg.common.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcUser {
    @Schema(description = "主键ID")
    private Integer ecuId;// 主键ID

    /**
     * 关联sysUser
     */
    @Schema(description = "关联sysUser")
    private String userId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "管理员类型")
    private Integer typeId;// 管理员类型

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "用户名称")
    private String ecUsername;// 用户名称

    @Schema(description = "代号")
    private String code;// 代号

    @Schema(description = "手机号")
    private String ecPhone;// 手机号

    @Schema(description = "密码")
    private String ecPwd;// 密码

    @Schema(description = "头像")
    private String ecHeadimg;// 头像

    @Schema(description = "性别 0 保密 1 男 2 女")
    private Integer sex;// 性别 0 保密 1 男 2 女

    @Schema(description = "简介")
    private String introduction;// 简介

    @Schema(description = "利润")
    private BigDecimal profit;// 利润

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间
}
