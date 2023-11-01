package org.jeecg.modules.cable.tools;

import org.jeecg.modules.cable.domain.InsulationComputeBo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ComputeFunction {

    /**
     * 计算绝缘数据
     *
     * @param density                 绝缘密度
     * @param unitPrice               绝缘单价
     * @param areaStr                 截面
     * @param insulationFireThickness 粗芯绝缘厚度
     * @param insulationZeroThickness //细芯绝缘厚度
     * @param fireDiameter            //粗芯直径
     * @param zeroDiameter            //细芯直径
     * @param fireMicatapeRadius      //粗芯云母带半径
     * @param zeroMicatapeRadius      //细芯云母带半径
     * @return
     */
    public static InsulationComputeBo insulationDataCompute(BigDecimal density, BigDecimal unitPrice,
                                                            String areaStr, BigDecimal insulationFireThickness,
                                                            BigDecimal insulationZeroThickness,
                                                            BigDecimal fireDiameter,
                                                            BigDecimal zeroDiameter,
                                                            BigDecimal fireMicatapeRadius,
                                                            BigDecimal zeroMicatapeRadius) {
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal fireInsulationRadius = BigDecimal.ZERO;// 粗芯绝缘总半径
        BigDecimal fireInsulationWeight = BigDecimal.ZERO;// 粗芯绝缘重量
        BigDecimal fireInsulationMoney = BigDecimal.ZERO;// 粗芯绝缘金额
        BigDecimal zeroInsulationRadius = BigDecimal.ZERO;// 细芯绝缘总半径
        BigDecimal zeroInsulationWeight = BigDecimal.ZERO;// 细芯绝缘重量
        BigDecimal zeroInsulationMoney = BigDecimal.ZERO;// 细芯绝缘金额
        BigDecimal insulationWeight;// 绝缘重量
        BigDecimal insulationMoney;// 绝缘金额
        if (fireMicatapeRadius.compareTo(BigDecimal.ZERO) == 0) {      // 没有云母带
            // 粗芯绝缘
            fireInsulationRadius = fireDiameter
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(insulationFireThickness);
            fireInsulationWeight = fireInsulationRadius.multiply(fireInsulationRadius)
                    .subtract(fireDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                            .multiply(fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(density)
                    .multiply(new BigDecimal(fireArr[0]));
            fireInsulationMoney = fireInsulationWeight.multiply(unitPrice);
            // 细芯绝缘
            if (areaArr.length == 2) {
                zeroArr = areaArr[1].split("\\*");
                zeroInsulationRadius = zeroDiameter
                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                        .add(insulationZeroThickness);
                zeroInsulationWeight = zeroInsulationRadius.multiply(zeroInsulationRadius)
                        .subtract(fireDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                                .multiply(fireDiameter.divide(new BigDecimal("2"), 7, RoundingMode.HALF_UP)
                                ))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(density)
                        .multiply(new BigDecimal(zeroArr[0]));
                zeroInsulationMoney = zeroInsulationWeight.multiply(unitPrice);
            }
        } else {// 有云母带
            // 粗芯绝缘
            fireInsulationRadius = fireMicatapeRadius.add(insulationFireThickness);
            fireInsulationWeight = fireInsulationRadius.multiply(fireInsulationRadius)
                    .subtract(fireMicatapeRadius.multiply(fireInsulationRadius))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(density)
                    .multiply(new BigDecimal(fireArr[0]));
            fireInsulationMoney = fireInsulationWeight.multiply(unitPrice);
            // 细芯绝缘
            if (areaArr.length == 2) {
                zeroArr = areaArr[1].split("\\*");
                zeroInsulationRadius = zeroMicatapeRadius.add(insulationZeroThickness);
                zeroInsulationWeight = zeroInsulationRadius.multiply(zeroInsulationRadius)
                        .subtract(zeroMicatapeRadius.multiply(zeroInsulationRadius))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(density)
                        .multiply(new BigDecimal(zeroArr[0]));
                zeroInsulationMoney = zeroInsulationWeight.multiply(unitPrice);
            }
        }
        insulationWeight = fireInsulationWeight.add(zeroInsulationWeight);
        insulationMoney = fireInsulationMoney.add(zeroInsulationMoney);
        return new InsulationComputeBo(fireInsulationRadius.stripTrailingZeros(),
                fireInsulationWeight.stripTrailingZeros(),
                fireInsulationMoney.stripTrailingZeros(),
                zeroInsulationRadius.stripTrailingZeros(),
                zeroInsulationWeight.stripTrailingZeros(),
                zeroInsulationMoney.stripTrailingZeros(),
                insulationFireThickness.stripTrailingZeros(),
                insulationZeroThickness.stripTrailingZeros(),
                insulationWeight.stripTrailingZeros(),
                insulationMoney.stripTrailingZeros());
    }

}
