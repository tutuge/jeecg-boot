package org.jeecg.modules.cable.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema(description = "云母带计算")
@AllArgsConstructor
@NoArgsConstructor
public class MicaTapeComputeBo {

    BigDecimal fireMicaTapeRadius = BigDecimal.ZERO;// 粗芯云母带半径
    BigDecimal fireMicaTapeWeight = BigDecimal.ZERO;// 粗芯云母带重量
    BigDecimal fireMicaTapeMoney = BigDecimal.ZERO;// 粗芯云母带金额
    BigDecimal zeroMicaTapeRadius = BigDecimal.ZERO;// 细芯云母带半径
    BigDecimal zeroMicaTapeWeight = BigDecimal.ZERO;// 细芯云母带重量
    BigDecimal zeroMicaTapeMoney = BigDecimal.ZERO;// 细芯云母带金额
    BigDecimal micaTapeThickness = BigDecimal.ZERO;//云母带厚度
    BigDecimal micaTapeWeight = BigDecimal.ZERO;// 云母带重量
    BigDecimal micaTapeMoney = BigDecimal.ZERO;// 云母带金额

}
