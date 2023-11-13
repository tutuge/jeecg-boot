package org.jeecg.modules.cable.controller.userCommon.taxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "获取税点")
public class UTaxPointBaseBo {

    @Schema(description = "系统发票税点id")
    @NotNull(message = "系统发票税点ID不得为空")
    private Integer ecdtId;//系统发票税点id
}
