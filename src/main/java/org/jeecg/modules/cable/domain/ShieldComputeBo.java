package org.jeecg.modules.cable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema(description = "屏蔽计算")
@AllArgsConstructor
@NoArgsConstructor
public class ShieldComputeBo {

    @Schema(description = "屏蔽外径")
    BigDecimal shieldDiameter = BigDecimal.ZERO;
    @Schema(description = "屏蔽重量")
    BigDecimal shieldWeight = BigDecimal.ZERO;
    @Schema(description = "屏蔽金额")
    BigDecimal shieldMoney = BigDecimal.ZERO;
}
