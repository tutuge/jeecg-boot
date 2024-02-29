package org.jeecg.modules.system.controller.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "编辑平台用户")
public class PlatformUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;


    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realname;


    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "生日")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    @Schema(description = "性别")
    private Integer sex;

    /**
     * 电子邮件
     */
    @Schema(description = "电子邮件")
    private String email;

    @Schema(description = "电话")
    private String phone;

    /**
     * 座机号
     */
    @Schema(description = "座机号")
    private String telephone;

    /**
     * 简介
     */
    @Schema(description = "简介")
    private String introduction;


    /**
     * 微信账户
     */
    @Schema(description = "微信账户")
    private String wxAccount;

    /**
     * qq账户
     */
    @Schema(description = "qq账户")
    private String qqAccount;

}
