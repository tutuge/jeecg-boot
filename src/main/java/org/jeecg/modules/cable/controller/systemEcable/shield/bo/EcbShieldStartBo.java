package org.jeecg.modules.cable.controller.systemEcable.shield.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "屏蔽")
@Data
public class EcbShieldStartBo {

    @NotNull(message = "屏蔽ID不得为空")
    @Schema(description = "屏蔽ID")
    private Integer ecbsId;
}
