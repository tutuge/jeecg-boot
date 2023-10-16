package org.jeecg.modules.cable.controller.userEcable.insulation.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "导体")
@Data
public class EcbuInsulationStartBo {

    @NotNull(message = "绝缘ID不得为空")
    @Schema(description = "绝缘ID")
    private Integer ecbiId;
}
