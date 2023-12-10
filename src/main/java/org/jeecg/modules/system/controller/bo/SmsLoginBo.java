package org.jeecg.modules.system.controller.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.jeecg.common.validation.Telephone;

@Schema(description = "短信验证登录bo")
@Data
public class SmsLoginBo {

    @Schema(description = "电话号码")
    @Telephone
    @NotBlank(message = "电话号码为空")
    private String mobile;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不得为空")
    private String captcha;
}
