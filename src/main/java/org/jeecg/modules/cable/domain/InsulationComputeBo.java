package org.jeecg.modules.cable.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsulationComputeBo {


    private BigDecimal fireInsulationRadius = BigDecimal.ZERO;// 粗芯绝缘总半径
    private BigDecimal fireInsulationWeight = BigDecimal.ZERO;// 粗芯绝缘重量
    private BigDecimal fireInsulationMoney = BigDecimal.ZERO;// 粗芯绝缘金额

    private BigDecimal zeroInsulationRadius = BigDecimal.ZERO;// 细芯绝缘总半径
    private BigDecimal zeroInsulationWeight = BigDecimal.ZERO;// 细芯绝缘重量
    private BigDecimal zeroInsulationMoney = BigDecimal.ZERO;// 细芯绝缘金额
    private BigDecimal insulationFireThickness = BigDecimal.ZERO;// 粗芯绝缘厚度
    private BigDecimal insulationZeroThickness = BigDecimal.ZERO;// 细芯绝缘厚度

    private BigDecimal insulationWeight = BigDecimal.ZERO;// 绝缘重量
    private BigDecimal insulationMoney = BigDecimal.ZERO;// 绝缘金额

}
