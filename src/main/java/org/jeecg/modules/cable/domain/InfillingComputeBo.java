package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfillingComputeBo {

    BigDecimal externalDiameter = BigDecimal.ZERO;//填充物之后的外径
    BigDecimal wideDiameter = BigDecimal.ZERO;// 粗芯直径
    BigDecimal fineDiameter = BigDecimal.ZERO;// 细芯直径
    BigDecimal infillingWeight = BigDecimal.ZERO;// 填充物重量
    BigDecimal infillingMoney = BigDecimal.ZERO;// 填充物金额
}
