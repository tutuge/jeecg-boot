package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SteelBandComputeBo {


    BigDecimal totalSteelbandVolume = BigDecimal.ZERO;// 钢带总体积
    BigDecimal innerSteelbandRadius = BigDecimal.ZERO;// 钢带内半径
    BigDecimal innerSteelbandVolume = BigDecimal.ZERO;// 钢带内部体积
    BigDecimal remainSteelbandVolume = BigDecimal.ZERO;// 钢带体积

    BigDecimal steelbandWeight = BigDecimal.ZERO;// 钢带重量
    BigDecimal steelbandMoney = BigDecimal.ZERO;// 钢带金额
}
