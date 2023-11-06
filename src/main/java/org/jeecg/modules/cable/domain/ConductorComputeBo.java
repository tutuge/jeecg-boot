package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConductorComputeBo {

    /**
     * 粗芯半径
     */
    public BigDecimal fireRadius = BigDecimal.ZERO;
    /**
     * 细芯半径
     */
    public BigDecimal zeroRadius = BigDecimal.ZERO;
    /**
     * 粗芯直径
     */
    public BigDecimal fireDiameter = BigDecimal.ZERO;
    /**
     * 细芯直径
     */
    public BigDecimal zeroDiameter = BigDecimal.ZERO;
    /**
     * 导体直径
     */
    public BigDecimal externalDiameter = BigDecimal.ZERO;
}
