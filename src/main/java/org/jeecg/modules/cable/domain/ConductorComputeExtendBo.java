package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 导体计算
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConductorComputeExtendBo extends ConductorComputeBo {

    /**
     * 粗芯重量
     */
    private BigDecimal fireWeight = BigDecimal.ZERO;
    /**
     * 细芯重量
     */
    private BigDecimal zeroWeight = BigDecimal.ZERO;
    /**
     * 粗芯金额
     */
    private BigDecimal fireMoney = BigDecimal.ZERO;
    /**
     * 细芯金额
     */
    private BigDecimal zeroMoney = BigDecimal.ZERO;
    /**
     * 导体总重量
     */
    private BigDecimal conductorWeight = BigDecimal.ZERO;

    /**
     * 导体总金额
     */
    private BigDecimal conductorMoney = BigDecimal.ZERO;

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
