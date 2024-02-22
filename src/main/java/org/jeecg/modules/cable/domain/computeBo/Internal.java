package org.jeecg.modules.cable.domain.computeBo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "内部材料")
@Data
public class Internal {

    @Schema(description = "内部材料ID")
    private Integer id;

    @Schema(description = "材料名称")
    private String fullName;

    @Schema(description = "材料类型ID")
    private Integer materialTypeId;

    @Schema(description = "材料类型名称")
    private String materialTypeName;

    @Schema(description = "系数")
    private BigDecimal factor;

    @Schema(description = "粗芯材料厚度")
    private BigDecimal fireThickness;

    @Schema(description = "细芯材料厚度")
    private BigDecimal zeroThickness;
}
