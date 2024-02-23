package org.jeecg.modules.cable.domain.computeVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InfillVo {

    @Schema(description = "材料类型ID")
    private Integer materialTypeId;

    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    private Integer materialType;

    @Schema(description = "导体材料类型名称")
    private String materialTypeFullName;

    @Schema(description = "材料id")
    private Integer id;

    @Schema(description = "名称")
    private String fullName;

    @Schema(description = "填充物外径")
    private BigDecimal externalDiameter = BigDecimal.ZERO;

    @Schema(description = "填充物外径")
    private BigDecimal infillingDiameter = BigDecimal.ZERO;

    @Schema(description = "填充物重量")
    private BigDecimal infillingWeight = BigDecimal.ZERO;

    @Schema(description = "填充物金额")
    private BigDecimal infillingMoney = BigDecimal.ZERO;
}
