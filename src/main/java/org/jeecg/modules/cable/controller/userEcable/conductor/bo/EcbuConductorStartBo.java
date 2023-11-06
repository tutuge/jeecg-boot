package org.jeecg.modules.cable.controller.userEcable.conductor.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description  = "导体")
@Data
public class EcbuConductorStartBo {

    @NotNull(message = "导体ID不得为空")
    @Schema(description  = "系统导体ID")
    private Integer ecbcId;
}
