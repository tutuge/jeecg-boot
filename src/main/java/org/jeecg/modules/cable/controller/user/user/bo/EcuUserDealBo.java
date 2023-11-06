package org.jeecg.modules.cable.controller.user.user.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "用户")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuUserDealBo {


    @Schema(description = "主键ID")
    private Integer ecuId;//主键ID

    @Schema(description = "管理员类型")
    @NotNull(message = "管理员类型不得为空")
    private Integer typeId;//管理员类型


    @Schema(description = "用户名称")
    @NotBlank(message = "用户名称不得为空")
    private String ecUsername;//用户名称

    @Schema(description = "代号")
    @NotBlank(message = "代号不得为空")
    private String code;//代号

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不得为空")
    private String ecPhone;//手机号

    @Schema(description = "利润")
    private BigDecimal profit;//利润
}
