package org.jeecg.modules.cable.domain.computeBo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "外部材料")
@Data
public class External {

    @Schema(description = "外部材料ID")
    private Integer id;

    @Schema(description = "材料名称")
    private String fullName;

    @Schema(description = "材料类型ID")
    private Integer materialTypeId;

    @Schema(description = "材料类型名称")
    private String materialTypeName;

    @Schema(description = "系数")
    private BigDecimal factor;

    @Schema(description = "材料厚度")
    private BigDecimal thickness;
}
