package org.jeecg.modules.cable.controller.userEcable.micatape.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "云母带")
@Data
public class EcbuMicatapeStartBo {

    @NotNull(message = "云母带ID不得为空")
    @Schema(description = "云母带ID")
    private Integer ecbmId;
}
