package org.jeecg.modules.cable.controller.systemEcable.conductor.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "导体")
@Data
public class EcbConductorStartBo {

    @NotNull(message = "导体ID不得为空")
    @Schema(description = "导体ID")
    private Integer ecbcId;
}
