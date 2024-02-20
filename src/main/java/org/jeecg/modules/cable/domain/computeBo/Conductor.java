package org.jeecg.modules.cable.domain.computeBo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "导体")
@Data
public class Conductor {

    @Schema(description = "导体ID")
    private Integer id;

    @Schema(description = "材料名称")
    private String fullName;

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;

    @Schema(description = "粗芯根数")
    private Integer fireRootNumber;

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;

    @Schema(description = "细芯根数")
    private Integer zeroRootNumber;

    @Schema(description = "细芯绞系数")
    private BigDecimal zeroStrand;
}
