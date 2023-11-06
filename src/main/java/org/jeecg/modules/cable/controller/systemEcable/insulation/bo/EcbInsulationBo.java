package org.jeecg.modules.cable.controller.systemEcable.insulation.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "绝缘")
@Data
public class EcbInsulationBo {

    @NotNull(message = "绝缘ID不得为空")
    @Schema(description = "绝缘ID")
    private Integer ecbiId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "说明")
    private String description = "";
}
