package org.jeecg.modules.cable.controller.systemEcable.infilling.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "填充物")
@Data
public class EcbInfillingBaseBo {

    @NotNull(message = "填充物ID不得为空")
    @Schema(description = "填充物ID")
    private Integer ecbinId;

}
