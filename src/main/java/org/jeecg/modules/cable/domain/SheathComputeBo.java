package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SheathComputeBo {
    BigDecimal totalSheathRadius = BigDecimal.ZERO;// 护套总半径
    BigDecimal totalSheathVolume = BigDecimal.ZERO;// 护套总体积
    BigDecimal innerSheathRadius = BigDecimal.ZERO;// 护套内半径
    BigDecimal innerSheathVolume = BigDecimal.ZERO;// 护套内体积
    BigDecimal remainSheathVolume = BigDecimal.ZERO;// 护套体积
    BigDecimal sheathWeight = BigDecimal.ZERO;// 护套重量
    BigDecimal sheathMoney = BigDecimal.ZERO;// 护套金额
}
