package org.jeecg.modules.cable.controller.user.user.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuUserDealMineBo {


    @Schema(description = "用户名称")
    @NotBlank(message = "用户名称不得为空")
    private String ecUsername;//用户名称


    @Schema(description = "用户密码")
    @NotBlank(message = "用户密码不得为空")
    private String ecPassword;
}
