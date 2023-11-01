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
    public BigDecimal fireRadius;
    /**
     * 细芯半径
     */
    public BigDecimal zeroRadius;
    /**
     * 粗芯直径
     */
    public BigDecimal fireDiameter;
    /**
     * 细芯直径
     */
    public BigDecimal zeroDiameter;
    /**
     * 导体直径
     */
    public BigDecimal externalDiameter;
}
