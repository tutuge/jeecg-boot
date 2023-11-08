package org.jeecg.modules.cable.controller.systemEcable.conductor.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "导体")
@Data
public class EcbConductorDealBo {

    @Schema(description = "导体ID")
    private Integer ecbcId;

    @Schema(description = "1 铜 2 铝")
    private Integer conductorType;

    @Schema(description = "简介")
    private String abbreviation;//简介

    @Schema(description = "全称")
    private String fullName;//全称

    @Schema(description = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "电阻")
    private BigDecimal resistivity;//电阻

    @Schema(description = "说明")
    private String description = "";
}
