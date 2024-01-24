package org.jeecg.modules.cable.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalComputeBo {

    BigDecimal externalRadius = BigDecimal.ZERO;// 外部总半径

    BigDecimal materialWeight = BigDecimal.ZERO;// 当前材料重量
    BigDecimal materialMoney = BigDecimal.ZERO;// 当前材料金额
}
