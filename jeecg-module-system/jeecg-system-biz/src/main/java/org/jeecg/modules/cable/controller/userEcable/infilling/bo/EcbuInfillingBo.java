package org.jeecg.modules.cable.controller.userEcable.infilling.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "填充物")
@Data
public class EcbuInfillingBo {

    @NotNull(message = "填充物ID不得为空")
    @Schema(description = "系统填充物ID")
    private Integer ecbinId;

    @Schema(description = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "说明")
    private String description = "";
}
