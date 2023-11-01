package org.jeecg.modules.cable.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsulationComputeBo {


    BigDecimal fireInsulationRadius = BigDecimal.ZERO;// 粗芯绝缘总半径
    BigDecimal fireInsulationWeight = BigDecimal.ZERO;// 粗芯绝缘重量
    BigDecimal fireInsulationMoney = BigDecimal.ZERO;// 粗芯绝缘金额

    BigDecimal zeroInsulationRadius = BigDecimal.ZERO;// 细芯绝缘总半径
    BigDecimal zeroInsulationWeight = BigDecimal.ZERO;// 细芯绝缘重量
    BigDecimal zeroInsulationMoney = BigDecimal.ZERO;// 细芯绝缘金额
    BigDecimal insulationFireThickness = BigDecimal.ZERO;// 粗芯绝缘厚度
    BigDecimal insulationZeroThickness = BigDecimal.ZERO;// 细芯绝缘厚度

    BigDecimal insulationWeight;// 绝缘重量
    BigDecimal insulationMoney;// 绝缘金额

}
