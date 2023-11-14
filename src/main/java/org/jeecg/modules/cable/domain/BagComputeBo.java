package org.jeecg.modules.cable.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BagComputeBo {

    BigDecimal bagRadius = BigDecimal.ZERO;// 加上了内部导体、云母等之后的包带半径
    BigDecimal bagWeight = BigDecimal.ZERO;// 包带重量
    BigDecimal bagMoney = BigDecimal.ZERO;// 包带金额
}
