package org.jeecg.modules.cable.controller.userEcable.infilling.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description  = "填充物")
@Data
public class EcbuInfillingStartBo {

    @NotNull(message = "填充物ID不得为空")
    @Schema(description  = "系统填充物ID")
    private Integer ecbinId;
}
