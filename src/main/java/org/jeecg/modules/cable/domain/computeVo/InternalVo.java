package org.jeecg.modules.cable.domain.computeVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InternalVo {

    @Schema(description = "名称")
    private String fullName;

    @Schema(description = "内部材料粗芯半径")
    private BigDecimal fireDiameter = BigDecimal.ZERO;

    @Schema(description = "内部材料细芯半径")
    private BigDecimal zeroDiameter = BigDecimal.ZERO;

    @Schema(description = "内部材料重量")
    private BigDecimal weight = BigDecimal.ZERO;

    @Schema(description = "内部材料金额")
    private BigDecimal money = BigDecimal.ZERO;
}
