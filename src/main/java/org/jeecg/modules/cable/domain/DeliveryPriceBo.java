package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPriceBo {

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal unitPrice = BigDecimal.ZERO;
}
