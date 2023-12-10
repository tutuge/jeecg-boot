package org.jeecg.modules.cable.controller.user.user.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.validation.Telephone;

@Schema(description = "用户注册")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuUserRegisterBo {

    /**
     * 登录账号 如果是app注册，可以没有用户名，拿手机号替代
     */
    private String username;

    @NotBlank(message = "密码不得为空")
    private String password;

    @Schema(description = "电话")
    @NotBlank(message = "电话不得为空")
    @Telephone
    private String ecPhone;

    @Schema(description = "验证码")
    private String smsCode;

    @Schema(description = "公司名称")
    @NotBlank(message = "公司名称不得为空")
    private String companyName;

    @Schema(description = "公司地址")
    //@NotBlank(message = "公司地址不得为空")
    private String addressDesc;
}
