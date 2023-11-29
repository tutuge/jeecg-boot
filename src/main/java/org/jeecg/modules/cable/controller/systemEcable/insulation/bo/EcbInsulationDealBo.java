package org.jeecg.modules.cable.controller.systemEcable.insulation.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "绝缘")
@Data
public class EcbInsulationDealBo {

    @Schema(description = "绝缘ID")
    private Integer ecbiId;

    @Schema(description = "简介")
    @NotBlank(message = "简介不得为空")
    private String abbreviation;

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空")
    private String fullName;


    @Schema(description = "单价")
    @NotNull(message = "单价不得为空")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    @NotNull(message = "密度不得为空")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "说明")
    @NotNull(message = "说明不得为空")
    private String description = "";
}
