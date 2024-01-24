package org.jeecg.modules.cable.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema(description = "填充物以内的计算")
@AllArgsConstructor
@NoArgsConstructor
public class InternalComputeBo {

    BigDecimal fireRadius = BigDecimal.ZERO;// 粗芯层半径
    BigDecimal fireWeight = BigDecimal.ZERO;// 粗芯层重量
    BigDecimal fireMoney = BigDecimal.ZERO;// 粗芯层金额

    BigDecimal zeroRadius = BigDecimal.ZERO;// 细芯材料总半径
    BigDecimal zeroWeight = BigDecimal.ZERO;// 细芯材料重量
    BigDecimal zeroMoney = BigDecimal.ZERO;// 细芯材料金额

    BigDecimal materialWeight = BigDecimal.ZERO;// 材料总重量
    BigDecimal materialMoney = BigDecimal.ZERO;// 材料总金额

}
