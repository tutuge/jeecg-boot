package org.jeecg.modules.cable.controller.systemEcable.sheath.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "护套")
@Data
public class EcbSheathStartBo {

    @NotNull(message = "护套ID不得为空")
    @Schema(description = "护套ID")
    private Integer ecbsId;
}
