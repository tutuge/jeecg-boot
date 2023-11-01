package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryBo {

    BigDecimal price = BigDecimal.ZERO;// 快递总价
    DeliveryObj objectDelivery = new DeliveryObj();
}
