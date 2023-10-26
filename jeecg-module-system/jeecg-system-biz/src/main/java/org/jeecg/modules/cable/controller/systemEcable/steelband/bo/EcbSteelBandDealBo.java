package org.jeecg.modules.cable.controller.systemEcable.steelband.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "钢带")
@Data
public class EcbSteelBandDealBo {

    @Schema(description = "钢带ID")
    private Integer ecbsbId;

    @Schema(description = "简介")
    @NotBlank(message = "简介不得为空")
    private String abbreviation;//简介

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空")
    private String fullName;//全称


    @Schema(description = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "说明")
    private String description = "";
}
