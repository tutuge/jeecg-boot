package org.jeecg.modules.cable.tools;

import cn.hutool.core.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.entity.userEcable.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jeecg.modules.cable.tools.ComputeFunction.*;

/**
 * 报价计算
 */
@Slf4j
public class EcableFunction {

    public static ConductorComputeBo getConductorData(EcOffer ecOffer) {
        Map<String, Object> map = new HashMap<>();
        // 使用+号将两个值切分
        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        // 粗芯丝号
        BigDecimal zeroSilkNumber = ecOffer.getZeroSilkNumber();// 细芯丝号
        BigDecimal fireRadius = BigDecimal.ZERO;// 火线直径
        BigDecimal zeroRadius = BigDecimal.ZERO;// 零线直径
        BigDecimal fireDiameter = BigDecimal.ZERO;// 粗芯外径
        BigDecimal zeroDiameter = BigDecimal.ZERO;// 细芯外径
        BigDecimal externalDiameter;// 导体外径
        if (fireArr.length == 2) {// 有一个*号时
            // 单段火线外径
            fireDiameter = (ecOffer.getFireSilkNumber()).multiply(getSilkPercent(ecOffer.getFireRootNumber()));
        }
        // 零线
        if (areaArr.length == 2) {
            // 单根零线数据
            zeroRadius = zeroSilkNumber.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(new BigDecimal(ecOffer.getZeroMembrance()));
            // 单段零线外径
            zeroDiameter = (ecOffer.getZeroSilkNumber()).multiply(getSilkPercent(ecOffer.getZeroRootNumber()));
        }
        // 计算导体外径
        externalDiameter = getExternalDiameter(areaArr, fireDiameter, zeroDiameter);
        // 更新导体重量
        return new ConductorComputeBo(fireRadius, zeroRadius, fireDiameter, zeroDiameter, externalDiameter);
    }

    // getConductorData 获取导体数据
    public static ConductorComputeExtendBo getConductorData(EcuqInput ecuqInput,
                                                            EcuqDesc ecuqDesc,
                                                            EcquParameter ecquParameter,
                                                            EcbuConductor ecbuConductor) {
        BigDecimal length = ecquParameter.getLength();
        String areaStr = ecuqInput.getAreaStr();

        BigDecimal conductorDensity = ecbuConductor.getDensity();

        Integer zeroMembrance = ecuqDesc.getZeroMembrance();
        Integer zeroRootNumber = ecuqDesc.getZeroRootNumber();
        BigDecimal zeroStrand = ecuqDesc.getZeroStrand();
        BigDecimal zeroSilkNumber = ecuqDesc.getZeroSilkNumber();// 细芯丝号
        BigDecimal conductorUnitPrice = ecuqDesc.getCunitPrice();
        BigDecimal fireSilkNumber = ecuqDesc.getFireSilkNumber();// 粗芯丝号
        Integer fireMembrance = ecuqDesc.getFireMembrance();
        Integer fireRootNumber = ecuqDesc.getFireRootNumber();
        BigDecimal fireStrand = ecuqDesc.getFireStrand();

        ConductorComputeExtendBo conductorComputeExtendBo = conductorDataCompute(conductorDensity,
                conductorUnitPrice,
                fireRootNumber,
                zeroRootNumber,
                fireSilkNumber,
                zeroSilkNumber,
                fireMembrance,
                fireStrand,
                zeroMembrance,
                zeroStrand,
                areaStr);
        BigDecimal fireWeight = conductorComputeExtendBo.getFireWeight();
        fireWeight = fireWeight.multiply(length);
        conductorComputeExtendBo.setFireWeight(fireWeight);
        BigDecimal fireMoney = fireWeight.multiply(conductorUnitPrice);
        conductorComputeExtendBo.setFireMoney(fireMoney);

        BigDecimal zeroWeight = conductorComputeExtendBo.getZeroWeight();
        zeroWeight = zeroWeight.multiply(length);
        conductorComputeExtendBo.setZeroWeight(zeroWeight);
        BigDecimal zeroMoney = zeroWeight.multiply(conductorUnitPrice);
        conductorComputeExtendBo.setZeroMoney(zeroMoney);

        conductorComputeExtendBo.setConductorWeight(fireWeight.add(zeroWeight));
        conductorComputeExtendBo.setConductorMoney(fireMoney.add(zeroMoney));
        return conductorComputeExtendBo;
    }

    // getMicatapeData
    public static MicaTapeComputeBo getMicatapeData(EcuqInput ecuqInput,
                                                    EcuqDesc ecuqDesc,
                                                    EcbuMicaTape ecbuMicatape,
                                                    BigDecimal fireDiameter,
                                                    BigDecimal zeroDiameter,
                                                    EcquParameter ecquParameter) {

        BigDecimal length = ecquParameter.getLength();
        if (ecuqDesc.getEcbumId() != 0) {
            String areaStr = ecuqInput.getAreaStr();

            BigDecimal density = ecbuMicatape.getDensity();
            BigDecimal unitPrice = ecbuMicatape.getUnitPrice();
            BigDecimal micatapeThickness = ecuqDesc.getMicatapeThickness();// 云母带厚度

            MicaTapeComputeBo bo = micaTapeDataCompute(areaStr,
                    density,
                    unitPrice,
                    micatapeThickness,
                    fireDiameter,
                    zeroDiameter);
            bo.setFireMicatapeWeight(bo.getFireMicatapeWeight().multiply(length));
            bo.setFireMicatapeMoney(bo.getFireMicatapeMoney().multiply(length));

            bo.setZeroMicatapeWeight(bo.getZeroMicatapeWeight().multiply(length));
            bo.setZeroMicatapeMoney(bo.getZeroMicatapeMoney().multiply(length));

            bo.setMicatapeWeight(bo.getFireMicatapeWeight().add(bo.getZeroMicatapeWeight()));
            bo.setMicatapeMoney(bo.getFireMicatapeMoney().add(bo.getZeroMicatapeMoney()));

            return bo;
        }
        return new MicaTapeComputeBo();
    }

    // getInsulationData
    public static InsulationComputeBo getInsulationData(EcuqInput ecuqInput,
                                                        EcuqDesc ecuqDesc,
                                                        EcbuInsulation ecbuInsulation,
                                                        BigDecimal fireDiameter,
                                                        BigDecimal zeroDiameter,
                                                        BigDecimal fireMicatapeRadius,
                                                        BigDecimal zeroMicatapeRadius,
                                                        EcquParameter ecquParameter) {

        BigDecimal length = ecquParameter.getLength();
        BigDecimal unitPrice = ecbuInsulation.getUnitPrice();
        BigDecimal density = ecbuInsulation.getDensity();
        BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();// 粗芯绝缘厚度
        BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();// 细芯绝缘厚度

        String areaStr = ecuqInput.getAreaStr();
        InsulationComputeBo computeBo = insulationDataCompute(density, unitPrice,
                areaStr, insulationFireThickness,
                insulationZeroThickness,
                fireDiameter,
                zeroDiameter,
                fireMicatapeRadius,
                zeroMicatapeRadius);
        computeBo.setFireInsulationWeight(computeBo.getFireInsulationWeight().multiply(length));
        computeBo.setFireInsulationMoney(computeBo.getFireInsulationMoney().multiply(length));

        computeBo.setZeroInsulationWeight(computeBo.getZeroInsulationWeight().multiply(length));
        computeBo.setZeroInsulationMoney(computeBo.getZeroInsulationMoney().multiply(length));

        computeBo.setInsulationWeight(computeBo.getFireInsulationWeight().add(computeBo.getZeroInsulationWeight()));
        computeBo.setInsulationMoney(computeBo.getFireInsulationMoney().add(computeBo.getZeroInsulationMoney()));

        return computeBo;

    }

    //  获取填充物数据
    public static InfillingComputeBo getInfillingData(EcuqInput ecuqInput,
                                                      EcquParameter ecquParameter,
                                                      EcbuInfilling ecbuInfilling,
                                                      BigDecimal fireDiameter,
                                                      BigDecimal zeroDiameter,
                                                      BigDecimal micaTapeThickness,
                                                      BigDecimal insulationFireThickness,
                                                      BigDecimal insulationZeroThickness) {
        BigDecimal length = ecquParameter.getLength();
        String areaStr = ecuqInput.getAreaStr();
        boolean infill = ObjUtil.isNull(ecbuInfilling);
        InfillingComputeBo infillingComputeBo = infillingDataCompute(
                infill ? BigDecimal.ZERO : ecbuInfilling.getDensity(),
                infill ? BigDecimal.ZERO : ecbuInfilling.getUnitPrice(),
                areaStr,
                micaTapeThickness,
                insulationFireThickness,
                insulationZeroThickness,
                fireDiameter,
                zeroDiameter);
        infillingComputeBo.setInfillingWeight(infillingComputeBo.getInfillingWeight().multiply(length));
        infillingComputeBo.setInfillingMoney(infillingComputeBo.getInfillingMoney().multiply(length));
        return infillingComputeBo;
    }

    // getBagData 获取包带数据
    public static BagComputeBo getBagData(EcuqInput ecuqInput,
                                          EcuqDesc ecuqDesc,
                                          EcquParameter ecquParameter,
                                          EcbuBag ecbuBag,
                                          BigDecimal externalDiameter) {
        BigDecimal length = ecquParameter.getLength();
        boolean bagNull = ObjUtil.isNull(ecbuBag);
        BigDecimal density = bagNull ? BigDecimal.ZERO : ecbuBag.getDensity();
        BigDecimal unitPrice = bagNull ? BigDecimal.ZERO : ecbuBag.getUnitPrice();
        BagComputeBo bagComputeBo = new BagComputeBo();
        if (ecuqInput.getSilkName().contains("22") || ecuqInput.getSilkName().contains("23")) {// 铠装
            if (ecuqDesc.getEcbub22Id() != 0) {
                bagComputeBo = bagDataCompute(ecuqDesc.getBag22Thickness(), density, unitPrice, externalDiameter);
            }
        } else {
            if (ecuqDesc.getEcbubId() != 0) {
                bagComputeBo = bagDataCompute(ecuqDesc.getBagThickness(), density, unitPrice, externalDiameter);
            }
        }

        bagComputeBo.setBagWeight(bagComputeBo.getBagWeight().multiply(length));
        bagComputeBo.setBagMoney(bagComputeBo.getBagMoney().multiply(length));

        return bagComputeBo;
    }


    public static SteelBandComputeBo getSteelBandData(EcuqDesc ecuqDesc,
                                                      EcquParameter ecquParameter,
                                                      EcbuSteelband ecbuSteelband,
                                                      BigDecimal externalDiameter) {
        BigDecimal length = ecquParameter.getLength();
        boolean aNull = ObjUtil.isNull(ecbuSteelband);
        BigDecimal unitPrice = aNull ? BigDecimal.ZERO : ecbuSteelband.getUnitPrice();
        BigDecimal density = aNull ? BigDecimal.ZERO : ecbuSteelband.getDensity();
        BigDecimal bagThickness = ecuqDesc.getBagThickness();
        BigDecimal steelbandThickness = ecuqDesc.getSteelbandThickness();
        BigDecimal shieldThickness = ecuqDesc.getShieldThickness();
        Integer steelbandStorey = ecuqDesc.getSteelbandStorey();

        SteelBandComputeBo steelBandComputeBo = steelBandDataCompute(unitPrice,
                density,
                bagThickness,
                shieldThickness,
                steelbandThickness,
                steelbandStorey,
                externalDiameter);
        steelBandComputeBo.setTotalSteelbandVolume(steelBandComputeBo.getTotalSteelbandVolume().multiply(length));
        steelBandComputeBo.setInnerSteelbandVolume(steelBandComputeBo.getInnerSteelbandVolume().multiply(length));
        steelBandComputeBo.setRemainSteelbandVolume(steelBandComputeBo.getRemainSteelbandVolume().multiply(length));
        steelBandComputeBo.setSteelbandWeight(steelBandComputeBo.getSteelbandWeight().multiply(length));
        steelBandComputeBo.setSteelbandMoney(steelBandComputeBo.getSteelbandMoney().multiply(length));
        return steelBandComputeBo;
    }

    // getSheathData 获取护套数据
    public static SheathComputeBo getSheathData(EcuqInput ecuqInput,
                                                EcuqDesc ecuqDesc,
                                                EcquParameter ecquParameter,
                                                EcbuSheath ecbuSheath,
                                                BigDecimal externalDiameter) {
        BigDecimal length = ecquParameter.getLength();
        boolean aNull = ObjUtil.isNull(ecbuSheath);
        BigDecimal density = aNull ? BigDecimal.ZERO : ecbuSheath.getDensity();
        BigDecimal unitPrice = aNull ? BigDecimal.ZERO : ecbuSheath.getUnitPrice();
        BigDecimal shieldThickness = ecuqDesc.getShieldThickness();
        BigDecimal steelbandThickness = ecuqDesc.getSteelbandThickness();
        Integer steelbandStorey = ecuqDesc.getSteelbandStorey();
        BigDecimal sheathThickness = ecuqDesc.getSheathThickness();

        SheathComputeBo sheathComputeBo = new SheathComputeBo();
        if (ecuqInput.getSilkName().contains("22") || ecuqInput.getSilkName().contains("23")) {// 铠装
            if (ecuqDesc.getEcbuSheathId() != 0 && sheathThickness.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal bag22Thickness = ecuqDesc.getBag22Thickness();
                sheathComputeBo = sheathDataCompute(density,
                        unitPrice,
                        bag22Thickness,
                        shieldThickness,
                        steelbandThickness,
                        steelbandStorey,
                        ecuqDesc.getSheath22Thickness(),
                        externalDiameter);
            }
        } else {
            if (ecuqDesc.getEcbuSheathId() != 0 && sheathThickness.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal bagThickness = ecuqDesc.getBagThickness();
                sheathComputeBo = sheathDataCompute(density,
                        unitPrice,
                        bagThickness,
                        shieldThickness,
                        steelbandThickness,
                        steelbandStorey,
                        sheathThickness,
                        externalDiameter);
            }
        }
        sheathComputeBo.setTotalSheathVolume(sheathComputeBo.getTotalSheathVolume().multiply(length));
        sheathComputeBo.setInnerSheathVolume(sheathComputeBo.getInnerSheathVolume().multiply(length));
        sheathComputeBo.setRemainSheathVolume(sheathComputeBo.getRemainSheathVolume().multiply(length));
        sheathComputeBo.setSheathWeight(sheathComputeBo.getSheathWeight().multiply(length));
        sheathComputeBo.setSheathMoney(sheathComputeBo.getSheathMoney().multiply(length));
        return sheathComputeBo;
    }

    // getBillPercentData 获取开发票计算结果
    public static BillBo getBillPercentData(EcuqInput ecuqInput,
                                            EcduCompany company,
                                            BigDecimal unitMoney,
                                            EcbulUnit ecbulUnit) {
        BigDecimal billSingleMoney = BigDecimal.ZERO;// 开票单价
        BigDecimal billComputeMoney = BigDecimal.ZERO;// 开票小计
        if (company.getBillPercentType() == 1) {// 算法1
            billSingleMoney = unitMoney
                    .divide((BigDecimal.ONE.subtract(ecuqInput.getBillPercent())), 6, RoundingMode.HALF_UP);// 开票单价
            billComputeMoney = unitMoney.divide(BigDecimal.ONE
                            .subtract(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()));// 开票小计
            if (ecbulUnit != null) {// 判断单位是否为米
                billComputeMoney = unitMoney.divide(BigDecimal.ONE
                                .subtract(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                        .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));// 开票小计
            }
        } else if (company.getBillPercentType() == 2) {// 算法2
            billSingleMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqInput.getBillPercent()));// 开票单价
            billComputeMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqInput.getBillPercent()))
                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()));// 开票小计
            if (ecbulUnit != null) {// 判断单位是否为米
                billComputeMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqInput.getBillPercent()))
                        .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                        .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));// 开票小计
            }
        }
        return new BillBo(billSingleMoney, billComputeMoney);
    }

    // getDeliveryData 获取快递数据
    public static DeliveryBo getDeliveryData(EcuQuoted ecuQuoted,
                                             List<DeliveryObj> listDeliveryPrice,
                                             EcbudDelivery dDelivery, Integer ecbudId) {
        BigDecimal price = BigDecimal.ZERO;// 快递总价
        DeliveryObj objectDelivery = new DeliveryObj();
        if (dDelivery == null) {
            objectDelivery = listDeliveryPrice.get(0);// 默认选最便宜的快递
        } else {
            if (ObjUtil.isNotNull(ecbudId)) {
                if (ecuQuoted.getEcbudId() == 0) {
                    log.info(CommonFunction.getGson().toJson(listDeliveryPrice));
                    listDeliveryPrice.get((dDelivery.getSortId() - 1)).setDSelect(true);
                    objectDelivery = listDeliveryPrice.get((dDelivery.getSortId() - 1));
                    // ecbudId = objectDelivery.getEcbudId();
                } else {
                    for (DeliveryObj deliveryObj : listDeliveryPrice) {
                        if (ecuQuoted.getEcbudId().equals(deliveryObj.getEcbudId())) {
                            objectDelivery = deliveryObj;
                            // ecbudId = deliveryObj.getEcbudId();
                        }
                    }
                }
            }
        }
        return new DeliveryBo(price, objectDelivery);
    }

    /**
     * 对应丝号倍数
     *
     * @param rootNumber
     * @return
     */
    public static BigDecimal getSilkPercent(Integer rootNumber) {
        BigDecimal silkPercent;
        silkPercent = switch (rootNumber) {
            case 1 -> BigDecimal.ONE;
            case 2 -> new BigDecimal("2");
            case 3 -> new BigDecimal("2.16");
            case 4 -> new BigDecimal("2.42");
            case 5 -> new BigDecimal("2.70");
            case 6 -> new BigDecimal("3");
            case 7 -> new BigDecimal("3");
            case 8 -> new BigDecimal("3.45");
            case 9 -> new BigDecimal("3.8");
            case 10 -> new BigDecimal("4.0");
            case 11 -> new BigDecimal("4.0");
            case 12 -> new BigDecimal("4.16");
            case 13 -> new BigDecimal("4.41");
            case 14 -> new BigDecimal("4.41");
            case 15 -> new BigDecimal("4.7");
            case 16 -> new BigDecimal("4.7");
            case 17 -> new BigDecimal("5.0");
            case 18 -> new BigDecimal("5.0");
            case 19 -> new BigDecimal("5.0");
            case 20 -> new BigDecimal("5.33");
            case 21 -> new BigDecimal("5.33");
            case 22 -> new BigDecimal("5.67");
            case 23 -> new BigDecimal("5.67");
            case 24 -> new BigDecimal("6.00");
            case 25 -> new BigDecimal("6.00");
            case 26 -> new BigDecimal("6.00");
            case 27 -> new BigDecimal("6.15");
            case 28 -> new BigDecimal("6.41");
            case 29 -> new BigDecimal("6.41");
            case 30 -> new BigDecimal("6.41");
            case 31 -> new BigDecimal("6.70");
            case 32 -> new BigDecimal("6.70");
            case 33 -> new BigDecimal("6.70");
            case 34 -> new BigDecimal("7.00");
            case 35 -> new BigDecimal("7.00");
            case 36 -> new BigDecimal("7.00");
            case 37 -> new BigDecimal("7.00");
            case 38 -> new BigDecimal("7.33");
            case 39 -> new BigDecimal("7.33");
            case 40 -> new BigDecimal("7.33");
            case 41 -> new BigDecimal("7.67");
            case 42 -> new BigDecimal("7.67");
            case 43 -> new BigDecimal("7.67");
            case 44 -> new BigDecimal("8.00");
            case 45 -> new BigDecimal("8.00");
            case 46 -> new BigDecimal("8.00");
            case 47 -> new BigDecimal("8.00");
            case 48 -> new BigDecimal("8.15");
            case 52 -> new BigDecimal("8.41");
            case 61 -> new BigDecimal("9.00");
            default -> BigDecimal.ZERO;
        };
        return silkPercent;
    }

    /**
     * 外径的平均数
     *
     * @param fireSegment  粗芯段数
     * @param fireDiameter
     * @param zeroSegment  细芯段数
     * @param zeroDiameter
     * @return
     */
    public static BigDecimal getAverageDiameter(Integer fireSegment, BigDecimal fireDiameter,
                                                Integer zeroSegment, BigDecimal zeroDiameter) {
        BigDecimal averageDiameter = new BigDecimal(fireSegment).multiply(fireDiameter)
                .add(new BigDecimal(zeroSegment).multiply(zeroDiameter))
                .divide(new BigDecimal(fireSegment).add(new BigDecimal(zeroSegment)), 16, RoundingMode.HALF_UP);
        return averageDiameter;
    }

    // getExternalDiameter 获取外径
    public static BigDecimal getExternalDiameter(String[] areaArr, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        BigDecimal externalDiameter = BigDecimal.ZERO;// 外径
        BigDecimal averageDiameter;
        Integer fireNumber = Integer.parseInt(areaArr[0].split("\\*")[0]);// 粗芯段数
        if (areaArr.length == 1) {// 零线数为0时视为等圆
            externalDiameter = getSilkPercent(fireNumber).multiply(fireDiameter);
        } else {// 既有火线又有零线
            Integer zeroNumber = Integer.parseInt(areaArr[1].split("\\*")[0]);// 细芯段数
            if (fireNumber == 2 && zeroNumber == 1) {
                if (zeroDiameter.compareTo(fireDiameter.multiply(new BigDecimal("2"))
                        .divide(new BigDecimal("3"), 6, RoundingMode.HALF_UP)) < 1) {
                    externalDiameter = fireDiameter.multiply(new BigDecimal("2"));
                } else {
                    externalDiameter = new BigDecimal("2.16")
                            .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
                }
            } else if (fireNumber == 3 && zeroNumber == 1) {// 有一根小截面的四芯电缆
                externalDiameter = new BigDecimal("2.42")
                        .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
            } else if (fireNumber == 4 && zeroNumber == 1) {// 有一根小截面的五芯电缆
                externalDiameter = new BigDecimal("2.70")
                        .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
            } else if (fireNumber == 3 && zeroNumber == 2) {// 有两根小截面的五芯电缆 计算地线
                averageDiameter = getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter);
                externalDiameter = new BigDecimal("2.70").multiply(averageDiameter);
            }
        }
        return externalDiameter;
    }
}
