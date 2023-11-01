package org.jeecg.modules.cable.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicatapeComputeBo {

    BigDecimal fireMicatapeRadius = BigDecimal.ZERO;// 粗芯云母带半径
    BigDecimal fireMicatapeWeight = BigDecimal.ZERO;// 粗芯云母带重量
    BigDecimal fireMicatapeMoney = BigDecimal.ZERO;// 粗芯云母带金额
    BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;// 细芯云母带半径
    BigDecimal zeroMicatapeWeight = BigDecimal.ZERO;// 细芯云母带重量
    BigDecimal zeroMicatapeMoney = BigDecimal.ZERO;// 细芯云母带金额
    BigDecimal micatapeThickness = BigDecimal.ZERO;//云母带厚度
    BigDecimal micatapeWeight = BigDecimal.ZERO;// 云母带重量
    BigDecimal micatapeMoney = BigDecimal.ZERO;// 云母带金额

}
