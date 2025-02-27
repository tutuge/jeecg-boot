package org.jeecg.modules.cable.controller.userEcable.bag.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "包带")
@Data
public class EcbuBagBo {

    @NotNull(message = "包带ID不得为空")
    @Schema(description = "包带ID")
    private Integer ecbubId;

    @Schema(description = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "说明")
    private String description = "";
}
