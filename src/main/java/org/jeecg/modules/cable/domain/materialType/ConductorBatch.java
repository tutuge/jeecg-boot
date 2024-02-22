package org.jeecg.modules.cable.domain.materialType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConductorBatch extends MaterialTypeBatch {

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;
}
