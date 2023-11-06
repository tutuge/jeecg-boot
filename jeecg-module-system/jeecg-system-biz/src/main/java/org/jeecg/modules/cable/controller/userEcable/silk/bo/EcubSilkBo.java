package org.jeecg.modules.cable.controller.userEcable.silk.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "丝型号")
@Data
public class EcubSilkBo {

    @NotNull(message = "丝型号ID不得为空")
    @Schema(description = "丝型号ID")
    private Integer ecbsbId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "说明")
    private String description = "";
}
