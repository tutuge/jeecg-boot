package org.jeecg.modules.cable.domain.computeVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConductorVo {

    @Schema(description = "导体id")
    private Integer id;

    @Schema(description = "导体名称")
    private String conductorFullName;

    @Schema(description = "材料类型ID")
    private Integer materialTypeId;

    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    private Integer materialType;

    @Schema(description = "导体材料类型名称")
    private String materialTypeFullName;


    @Schema(description = "导体外径")
    private BigDecimal conductorDiameter = BigDecimal.ZERO;

    @Schema(description = "粗芯外径")
    private BigDecimal fireDiameter = BigDecimal.ZERO;

    @Schema(description = "细芯外径")
    private BigDecimal zeroDiameter = BigDecimal.ZERO;

    @Schema(description = "粗芯重量")
    private BigDecimal fireWeight = BigDecimal.ZERO;

    @Schema(description = "粗芯金额")
    private BigDecimal fireMoney = BigDecimal.ZERO;

    @Schema(description = "细芯重量")
    private BigDecimal zeroWeight = BigDecimal.ZERO;

    @Schema(description = "细芯金额")
    private BigDecimal zeroMoney = BigDecimal.ZERO;

    @Schema(description = "导体重量")
    private BigDecimal conductorWeight = BigDecimal.ZERO;

    @Schema(description = "导体金额")
    private BigDecimal conductorMoney = BigDecimal.ZERO;
}
