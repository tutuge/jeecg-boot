package org.jeecg.modules.cable.controller.user.user.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.poi.excel.annotation.Excel;

@Schema(description = "用户注册")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuUserRegisterBo {

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不得为空")
    private String username;

    @NotBlank(message = "登录密码不得为空")
    private String password;

    @Schema(description = "电话")
    @NotBlank(message = "电话不得为空")
    private String ecPhone;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不得为空")
    private String smsCode;

    @Schema(description = "公司名称")
    @NotBlank(message = "公司名称不得为空")
    private String companyName;

    @Schema(description = "公司地址")
    @NotBlank(message = "公司地址不得为空")
    private String addressDesc;
}
