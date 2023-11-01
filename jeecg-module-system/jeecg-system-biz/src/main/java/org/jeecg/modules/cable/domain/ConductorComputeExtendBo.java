package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConductorComputeExtendBo extends ConductorComputeBo {

    /**
     * 粗芯重量
     */
    private BigDecimal fireWeight;
    /**
     * 细芯重量
     */
    private BigDecimal zeroWeight;
    /**
     * 粗芯金额
     */
    private BigDecimal fireMoney;
    /**
     * 细芯金额
     */
    private BigDecimal zeroMoney;
    /**
     * 导体重量
     */
    private BigDecimal conductorWeight;

    /**
     * 导体金额
     */
    private BigDecimal conductorMoney;

    public ConductorComputeExtendBo(BigDecimal fireRadius, BigDecimal zeroRadius, BigDecimal fireDiameter,
                                    BigDecimal zeroDiameter, BigDecimal externalDiameter, BigDecimal fireWeight,
                                    BigDecimal zeroWeight, BigDecimal fireMoney, BigDecimal zeroMoney,
                                    BigDecimal conductorWeight, BigDecimal conductorMoney) {

        this.fireRadius = fireRadius;
        this.zeroRadius = zeroRadius;
        this.fireDiameter = fireDiameter;
        this.zeroDiameter = zeroDiameter;
        this.externalDiameter = externalDiameter;
        this.fireWeight = fireWeight;
        this.zeroWeight = zeroWeight;
        this.fireMoney = fireMoney;
        this.zeroMoney = zeroMoney;
        this.conductorWeight = conductorWeight;
        this.conductorMoney = conductorMoney;
    }
}
