package org.jeecg.modules.cable.domain.computeVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "外部材料")
@Data
public class ExternalVo {

    @Schema(description = "名称")
    private String fullName;

    @Schema(description = "外径")
    private BigDecimal diameter = BigDecimal.ZERO;

    @Schema(description = "重量")
    private BigDecimal weight = BigDecimal.ZERO;

    @Schema(description = "金额")
    private BigDecimal money = BigDecimal.ZERO;
}
