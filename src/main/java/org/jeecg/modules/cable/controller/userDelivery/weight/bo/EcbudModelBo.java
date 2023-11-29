package org.jeecg.modules.cable.controller.userDelivery.weight.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "表头")
@Data
public class EcbudModelBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecbudId;
}
