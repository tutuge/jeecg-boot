package org.jeecg.modules.cable.controller.systemEcable.materials.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "材料")
@Data
public class EcbMaterialsDealBo {

    @Schema(description = "材料ID")
    private Integer id;

    @Schema(description = "1 铜 2 铝")
    private Integer conductorType;

    @Schema(description = "材料类型ID")
    @NotNull(message = "材料类型ID不得为空")
    private Integer materialId;

    @Schema(description = "简介")
    private String abbreviation;

    @Schema(description = "全称")
    @NotBlank(message = "材料名称不得为空")
    private String fullName;

    @Schema(description = "单价")
    @NotNull(message = "单价不得为空")
    private BigDecimal unitPrice = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "密度")
    @NotNull(message = "密度不得为空")
    private BigDecimal density = BigDecimal.ZERO.stripTrailingZeros();

    @Schema(description = "电阻")
    private BigDecimal resistivity;

    @Schema(description = "说明")
    private String description = "";
}
