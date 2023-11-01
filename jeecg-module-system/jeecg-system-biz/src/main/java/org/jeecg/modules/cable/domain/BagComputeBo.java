package org.jeecg.modules.cable.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BagComputeBo {

    BigDecimal bagRadius = BigDecimal.ZERO;// 包带半径
    BigDecimal bagWeight = BigDecimal.ZERO;// 包带重量
    BigDecimal bagMoney = BigDecimal.ZERO;// 包带金额
}
