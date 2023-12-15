package org.jeecg.modules.cable.tools;

import org.jeecg.modules.cable.domain.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.jeecg.modules.cable.tools.EcableFunction.getExternalDiameter;
import static org.jeecg.modules.cable.tools.EcableFunction.getSilkPercent;

public class ComputeFunction {

    /**
     * @param conductorDensity   导体密度
     * @param conductorUnitPrice
     * @param fireRootNumber     粗芯根数
     * @param zeroRootNumber     细芯根数
     * @param fireSilkNumber     粗芯丝号 也就是火线直径
     * @param zeroSilkNumber     细芯丝号 也就是零线直径
     * @param fireMembrance
     * @param fireStrand         粗芯绞合系数
     * @param zeroMembrance
     * @param zeroStrand         零线绞合系数
     * @param areaStr            规格
     * @param conductorReduction 报价单上的导体截面折扣
     * @return
     */
    public static ConductorComputeExtendBo conductorDataCompute(BigDecimal conductorDensity,
                                                                BigDecimal conductorUnitPrice,
                                                                Integer fireRootNumber,
                                                                Integer zeroRootNumber,
                                                                BigDecimal fireSilkNumber,
                                                                BigDecimal zeroSilkNumber,
                                                                Integer fireMembrance,
                                                                BigDecimal fireStrand,
                                                                Integer zeroMembrance,
                                                                BigDecimal zeroStrand,
                                                                String areaStr,
                                                                BigDecimal conductorReduction) {
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal fireRadius = BigDecimal.ZERO;//火线半径
        BigDecimal zeroRadius = BigDecimal.ZERO;//零线半径
        BigDecimal fireWeight = BigDecimal.ZERO;//粗芯重量
        BigDecimal zeroWeight = BigDecimal.ZERO;//细芯重量
        BigDecimal fireMoney = BigDecimal.ZERO;//粗芯金额
        BigDecimal zeroMoney = BigDecimal.ZERO;//细芯金额
        BigDecimal fireDiameter = BigDecimal.ZERO;//粗芯外径
        BigDecimal zeroDiameter = BigDecimal.ZERO;//细芯外径
        BigDecimal externalDiameter;//导体外径
        BigDecimal conductorMoney;
        BigDecimal conductorWeight;//导体重量
        if (fireArr.length == 2) {
            //有一个*号时
            //单根火线数据
            fireRadius = fireSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(new BigDecimal(fireMembrance));
            //火线面积
            BigDecimal area = fireRadius.multiply(fireRadius).multiply(BigDecimal.valueOf(Math.PI));
            //折扣之后再次计算出的火线导体半径
            fireRadius = reduce(area, conductorReduction);

            fireWeight = fireRadius.multiply(fireRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(fireRootNumber))
                    .multiply(fireStrand)
                    .multiply(new BigDecimal(fireArr[0]))//有几根火线
                    .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP) //转换为平方厘米
                    .multiply(conductorDensity);
            fireMoney = fireWeight.multiply(conductorUnitPrice);
            //单段火线外径 = 半径*2
            fireDiameter = fireRadius.multiply(BigDecimal.valueOf(2D)).multiply(getSilkPercent(fireRootNumber));
        }
        //零线
        if (areaArr.length == 2) {
            zeroArr = areaArr[1].split("\\*");
            //单根零线数据
            zeroRadius = zeroSilkNumber
                    .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(new BigDecimal(zeroMembrance));
            //截面面积
            BigDecimal area = zeroRadius.multiply(zeroRadius).multiply(BigDecimal.valueOf(Math.PI));
            //折扣之后再次计算出的零线半径
            zeroRadius = reduce(area, conductorReduction);
            zeroWeight = zeroRadius.multiply(zeroRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(zeroRootNumber))
                    .multiply(zeroStrand)
                    .multiply(new BigDecimal(zeroArr[0]))//核心数
                    .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP) //转换为平方厘米
                    .multiply(conductorDensity);
            zeroMoney = zeroWeight.multiply(conductorUnitPrice);
            //单段零线外径
            zeroDiameter = zeroRadius.multiply(BigDecimal.valueOf(2D)).multiply(getSilkPercent(zeroRootNumber));
        }
        //计算导体加权后的外径
        externalDiameter = getExternalDiameter(areaArr, fireDiameter, zeroDiameter);
        conductorWeight = fireWeight.add(zeroWeight);
        conductorMoney = fireMoney.add(zeroMoney);
        return new ConductorComputeExtendBo(fireRadius.stripTrailingZeros(),
                zeroRadius.stripTrailingZeros(),
                fireDiameter.stripTrailingZeros(),
                zeroDiameter.stripTrailingZeros(),
                externalDiameter.stripTrailingZeros(),
                fireWeight.stripTrailingZeros(),
                zeroWeight.stripTrailingZeros(),
                fireMoney.stripTrailingZeros(),
                zeroMoney.stripTrailingZeros(),
                conductorWeight.stripTrailingZeros(),
                conductorMoney.stripTrailingZeros());
    }

    /**
     * 截面积打折
     *
     * @param area
     * @param reduction
     * @return
     */
    public static BigDecimal reduce(BigDecimal area, BigDecimal reduction) {
        //给截面积打折
        BigDecimal divide = area.multiply(reduction).divide(BigDecimal.valueOf(Math.PI), 16, RoundingMode.HALF_UP);
        //半径
        return sqrt(divide, 16);
    }


    public static BigDecimal sqrt(BigDecimal value, int scale) {
        BigDecimal num2 = BigDecimal.valueOf(2);
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }
        deviation = deviation.setScale(scale, RoundingMode.HALF_UP);
        return deviation;
    }

    /**
     * 计算云母带
     *
     * @param areaStr
     * @param density
     * @param unitPrice
     * @param micaTapeThickness // 云母带厚度
     * @param fireDiameter
     * @param zeroDiameter
     * @return
     */
    public static MicaTapeComputeBo micaTapeDataCompute(String areaStr,
                                                        BigDecimal density,
                                                        BigDecimal unitPrice,
                                                        BigDecimal micaTapeThickness,
                                                        BigDecimal fireDiameter,
                                                        BigDecimal zeroDiameter) {
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal micaTapeWeight;//云母带重量
        BigDecimal micaTapeMoney;//云母带金额
        BigDecimal fireMicatapeRadius = BigDecimal.ZERO;//粗芯云母带半径
        BigDecimal fireMicatapeWeight = BigDecimal.ZERO;//粗芯云母带重量
        BigDecimal fireMicatapeMoney = BigDecimal.ZERO;//粗芯云母带金额
        BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;//细芯云母带半径
        BigDecimal zeroMicatapeWeight = BigDecimal.ZERO;//细芯云母带重量
        BigDecimal zeroMicatapeMoney = BigDecimal.ZERO;//细芯云母带金额
        //火线云母带半径 = 火线半径 + 云母带厚度
        fireMicatapeRadius = fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                .add(micaTapeThickness);
        //重量(KG)
        fireMicatapeWeight = fireMicatapeRadius.multiply(fireMicatapeRadius)
                .subtract(fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                        .multiply(fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)))
                .multiply(BigDecimal.valueOf(Math.PI))
                .multiply(density)
                .divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP)
                .multiply(new BigDecimal(fireArr[0]));
        fireMicatapeMoney = fireMicatapeWeight.multiply(unitPrice);
        //零线云母带
        if (areaArr.length == 2) {
            zeroMicatapeRadius = zeroDiameter
                    .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(micaTapeThickness);
            zeroArr = areaArr[1].split("\\*");
            //重量(KG)
            zeroMicatapeWeight = zeroMicatapeRadius.multiply(zeroMicatapeRadius)
                    .subtract(zeroDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                            .multiply(zeroDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(density)
                    .divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(zeroArr[0]));
            zeroMicatapeMoney = zeroMicatapeWeight.multiply(unitPrice);
        }
        micaTapeWeight = fireMicatapeWeight.add(zeroMicatapeWeight);
        micaTapeMoney = fireMicatapeMoney.add(zeroMicatapeMoney);
        return new MicaTapeComputeBo(fireMicatapeRadius.stripTrailingZeros(),
                fireMicatapeWeight.stripTrailingZeros(),
                fireMicatapeMoney.stripTrailingZeros(),
                zeroMicatapeRadius.stripTrailingZeros(),
                zeroMicatapeWeight.stripTrailingZeros(),
                zeroMicatapeMoney.stripTrailingZeros(),
                micaTapeThickness.stripTrailingZeros(),
                micaTapeWeight.stripTrailingZeros(),
                micaTapeMoney.stripTrailingZeros());
    }


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
    public static InsulationComputeBo insulationDataCompute(BigDecimal density,
                                                            BigDecimal unitPrice,
                                                            String areaStr,
                                                            BigDecimal insulationFireThickness,
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
        // 没有云母带
        if (fireMicatapeRadius.compareTo(BigDecimal.ZERO) == 0) {
            // 粗芯绝缘
            fireInsulationRadius = fireDiameter
                    .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(insulationFireThickness);
            fireInsulationWeight = fireInsulationRadius.multiply(fireInsulationRadius)
                    .subtract(fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                            .multiply(fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(density)
                    .divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(fireArr[0]));
            fireInsulationMoney = fireInsulationWeight.multiply(unitPrice);
            // 细芯绝缘
            if (areaArr.length == 2) {
                zeroArr = areaArr[1].split("\\*");
                zeroInsulationRadius = zeroDiameter
                        .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                        .add(insulationZeroThickness);
                //重量(KG)
                zeroInsulationWeight = zeroInsulationRadius.multiply(zeroInsulationRadius)
                        .subtract(fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                                .multiply(fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                                ))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(density)
                        .divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(zeroArr[0]));
                zeroInsulationMoney = zeroInsulationWeight.multiply(unitPrice);
            }
        } else {// 有云母带
            // 粗芯绝缘
            fireInsulationRadius = fireMicatapeRadius.add(insulationFireThickness);
            //重量(KG)
            fireInsulationWeight = fireInsulationRadius.multiply(fireInsulationRadius)
                    .subtract(fireMicatapeRadius.multiply(fireInsulationRadius))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(density)
                    .divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP)
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
                        .divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP)
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

    /**
     * @param conductorDiameter       导体外径
     * @param density
     * @param unitPrice
     * @param areaStr
     * @param micaTapeThickness
     * @param insulationFireThickness
     * @param insulationZeroThickness
     * @param fireDiameter
     * @param zeroDiameter
     * @return
     */
    public static InfillingComputeBo infillingDataCompute(BigDecimal density,
                                                          BigDecimal unitPrice,
                                                          String areaStr,
                                                          BigDecimal micaTapeThickness,
                                                          BigDecimal insulationFireThickness,
                                                          BigDecimal insulationZeroThickness,
                                                          BigDecimal fireDiameter,
                                                          BigDecimal zeroDiameter) {
        //导体->云母带->绝缘->填充物->包袋->屏蔽->钢带->外护套
        String[] areaArr = areaStr.split("\\+");
        //云母带双层厚度
        BigDecimal micaTapes = micaTapeThickness.multiply(new BigDecimal("2"));
        //绝缘双层厚度
        BigDecimal insulations = insulationFireThickness.multiply(new BigDecimal("2"));
        // 粗芯
        BigDecimal wideDiameter = fireDiameter.add(micaTapes).add(insulations);
        // 细芯直径
        BigDecimal fineDiameter = zeroDiameter.add(micaTapes).add(insulations);
        //重新计算增加了云母与绝缘后的总外径
        BigDecimal conductorDiameter = getExternalDiameter(areaArr, wideDiameter, fineDiameter);//导体外径
        //导体总的加权后的半径
        BigDecimal conductorRadius = conductorDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        //导体加权后总的面积
        BigDecimal totalInfillingVolume = conductorRadius.multiply(conductorRadius).multiply(BigDecimal.valueOf(Math.PI));
        //火线+云母+绝缘
        BigDecimal fireInfillingRadius = fireDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                .add(micaTapeThickness)
                .add(insulationFireThickness);
        //火线对应面积
        BigDecimal fireInfillingVolume = fireInfillingRadius.multiply(fireInfillingRadius).multiply(BigDecimal.valueOf(Math.PI));
        //零线对应的面积
        BigDecimal zeroInfillingVolume = BigDecimal.ZERO;
        if (areaArr.length == 2) {
            BigDecimal zeroInfillingRadius = zeroDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(micaTapeThickness)
                    .add(insulationZeroThickness);
            zeroInfillingVolume = zeroInfillingRadius.multiply(zeroInfillingRadius).multiply(BigDecimal.valueOf(Math.PI));
        }
        // 填充物面积 = 导体加权总面积 - 火线总面积- 零线总面积
        BigDecimal remainInfillingVolume = totalInfillingVolume.subtract(fireInfillingVolume).subtract(zeroInfillingVolume);
        BigDecimal infillingWeight = remainInfillingVolume.multiply(density).divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP); //填充物重量(kg)
        BigDecimal infillingMoney = infillingWeight.multiply(unitPrice); //填充物金额
        return new InfillingComputeBo(conductorDiameter.stripTrailingZeros(),
                wideDiameter.stripTrailingZeros(),
                fineDiameter.stripTrailingZeros(),
                infillingWeight.stripTrailingZeros(),
                infillingMoney.stripTrailingZeros());
    }

    public static BagComputeBo bagDataCompute(BigDecimal bagThickness, BigDecimal density, BigDecimal unitPrice, BigDecimal externalDiameter) {
        //导体->云母带->绝缘->填充物->包带->屏蔽->钢带->外护套
        BigDecimal radius = externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        BigDecimal bagRadius = radius.add(bagThickness); // 包带半径
        BigDecimal bagWeight = ((bagRadius.multiply(bagRadius)).subtract(radius.multiply(radius)))
                .multiply(BigDecimal.valueOf(Math.PI)).multiply(density).divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP); //包带重量(kg);
        BigDecimal bagMoney = bagWeight.multiply(unitPrice);// 包带金额
        return new BagComputeBo(bagRadius.stripTrailingZeros(),
                bagWeight.stripTrailingZeros(),
                bagMoney.stripTrailingZeros());
    }

    /**
     * @param unitPrice
     * @param density
     * @param bagThickness
     * @param shieldThickness
     * @param steelBandThickness
     * @param steelBandStorey    钢带层数
     * @param externalDiameter   内径直径
     * @return
     */
    public static SteelBandComputeBo steelBandDataCompute(BigDecimal unitPrice,
                                                          BigDecimal density,
                                                          BigDecimal bagThickness,
                                                          BigDecimal shieldThickness,
                                                          BigDecimal steelBandThickness,
                                                          Integer steelBandStorey,
                                                          BigDecimal externalDiameter) {
        // 钢带内半径
        BigDecimal innerSteelBandRadius = externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                .add(bagThickness)// 包带
                .add(shieldThickness);// 屏蔽
        //钢带总厚度 = 钢带厚度 * 钢带层数
        BigDecimal steelBandTotal = steelBandThickness.multiply(new BigDecimal(steelBandStorey));
        // 钢带总半径 = 钢带内径 + 钢带厚度
        BigDecimal totalSteelBandRadius = innerSteelBandRadius.add(steelBandTotal);
        // 钢带总体积
        BigDecimal totalSteelBandVolume = totalSteelBandRadius.multiply(totalSteelBandRadius).multiply(BigDecimal.valueOf(Math.PI));
        // 钢带内部体积
        BigDecimal innerSteelBandVolume = innerSteelBandRadius.multiply(innerSteelBandRadius).multiply(BigDecimal.valueOf(Math.PI));
        // 钢带体积
        BigDecimal remainSteelBandVolume = (totalSteelBandVolume.subtract(innerSteelBandVolume));
        // 钢带重量(KG)
        BigDecimal steelBandWeight = remainSteelBandVolume.multiply(density).divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP);
        // 钢带金额
        BigDecimal steelBandMoney = steelBandWeight.multiply(unitPrice);
        return new SteelBandComputeBo(totalSteelBandVolume.stripTrailingZeros(),
                innerSteelBandRadius.stripTrailingZeros(),
                innerSteelBandVolume.stripTrailingZeros(),
                remainSteelBandVolume.stripTrailingZeros(),
                steelBandWeight.stripTrailingZeros(),
                steelBandMoney.stripTrailingZeros(),
                totalSteelBandRadius.stripTrailingZeros());
    }

    public static SheathComputeBo sheathDataCompute(BigDecimal density,
                                                    BigDecimal unitPrice,
                                                    BigDecimal bagThickness,
                                                    BigDecimal shieldThickness,
                                                    BigDecimal steelBandThickness,
                                                    Integer steelBandStorey,
                                                    BigDecimal sheathThickness,
                                                    BigDecimal externalDiameter) {
        //外径半径
        BigDecimal divide = externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        //钢带总厚度
        BigDecimal multiply = steelBandThickness.multiply(new BigDecimal(steelBandStorey));
        // 护套内半径 = 外半径 + 包带 + 屏蔽 + 钢带
        BigDecimal innerSheathRadius = divide.add(bagThickness).add(shieldThickness).add(multiply);
        // 护套总半径
        BigDecimal totalSheathRadius = innerSheathRadius.add(sheathThickness);// 护套厚度
        // 护套总体积
        BigDecimal totalSheathVolume = totalSheathRadius.multiply(totalSheathRadius).multiply(BigDecimal.valueOf(Math.PI));
        // 护套内体积
        BigDecimal innerSheathVolume = innerSheathRadius.multiply(innerSheathRadius).multiply(BigDecimal.valueOf(Math.PI));
        // 护套体积
        BigDecimal remainSheathVolume = totalSheathVolume.subtract(innerSheathVolume);
        // 护套重量(KG)
        BigDecimal sheathWeight = remainSheathVolume.multiply(density).divide(BigDecimal.valueOf(1000D),16,RoundingMode.HALF_UP);
        //护套金额
        BigDecimal sheathMoney = sheathWeight.multiply(unitPrice);
        return new SheathComputeBo(totalSheathRadius.stripTrailingZeros(),
                totalSheathVolume.stripTrailingZeros(),
                innerSheathRadius.stripTrailingZeros(),
                innerSheathVolume.stripTrailingZeros(),
                remainSheathVolume.stripTrailingZeros(),
                sheathWeight.stripTrailingZeros(),
                sheathMoney.stripTrailingZeros());
    }

}
