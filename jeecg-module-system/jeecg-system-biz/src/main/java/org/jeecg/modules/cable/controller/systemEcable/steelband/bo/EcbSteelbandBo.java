package org.jeecg.modules.cable.controller.systemEcable.steelband.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "屏蔽")
@Data
public class EcbSteelbandBo {

    @NotNull(message = "屏蔽ID不得为空")
    @Schema(description = "屏蔽ID")
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
