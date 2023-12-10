package org.jeecg.modules.system.controller.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.jeecg.common.validation.Telephone;

@Schema(description = "短信发送bo")
@Data
public class SmsBo {

    @Schema(description = "电话号码")
    @Telephone
    @NotBlank(message = "电话号码为空")
    private String mobile;

    @Schema(description = "手机号模式 登录模式: 2  注册模式: 1")
    @NotBlank(message = "模式不得为空")
    private String smsMode;
}
