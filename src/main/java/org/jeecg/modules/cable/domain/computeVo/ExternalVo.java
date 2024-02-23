package org.jeecg.modules.cable.domain.computeVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "外部材料")
@Data
public class ExternalVo {

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

    @Schema(description = "外径")
    private BigDecimal diameter = BigDecimal.ZERO;

    @Schema(description = "重量")
    private BigDecimal weight = BigDecimal.ZERO;

    @Schema(description = "金额")
    private BigDecimal money = BigDecimal.ZERO;
}
