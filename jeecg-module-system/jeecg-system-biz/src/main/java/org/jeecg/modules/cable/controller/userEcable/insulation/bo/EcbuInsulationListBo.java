package org.jeecg.modules.cable.controller.userEcable.insulation.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "绝缘查询")
@Data
public class EcbuInsulationListBo {

    @NotNull(message = "是否启用不得为空")
    @Schema(description = "是否启用")
    private Boolean startType;
}
