package org.jeecg.modules.cable.tools;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.entity.userEcable.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EcableFunction {
    //getConductorData 获取导体数据
    public static Map<String, Object> getConductorData(EcuqInput ecuqInput,
                                                       EcuqDesc ecuqDesc,
                                                       EcquParameter ecquParameter,
                                                       EcbuConductor ecbuConductor) {
        Map<String, Object> map = new HashMap<>();
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal conductorDensity = ecbuConductor.getDensity();
        BigDecimal conductorUnitPrice = ecuqDesc.getCunitPrice();
        BigDecimal fireSilkNumber = ecuqDesc.getFireSilkNumber();//粗芯丝号
        BigDecimal zeroSilkNumber = ecuqDesc.getZeroSilkNumber();//细芯丝号
        BigDecimal fireRadius = new BigDecimal("0");//火线直径
        BigDecimal zeroRadius = new BigDecimal("0");//零线直径
        BigDecimal fireWeight = new BigDecimal("0");//粗芯重量
        BigDecimal zeroWeight = new BigDecimal("0");//细芯重量
        BigDecimal fireMoney = new BigDecimal("0");//粗芯金额
        BigDecimal zeroMoney = new BigDecimal("0");//细芯金额
        BigDecimal fireDiameter = new BigDecimal("0");//粗芯外径
        BigDecimal zeroDiameter = new BigDecimal("0");//细芯外径
        BigDecimal externalDiameter;//导体外径
        BigDecimal conductorMoney;
        BigDecimal conductorWeight;//导体重量
        if (fireArr.length == 2) {//有一个*号时
            //单根火线数据
            fireRadius = fireSilkNumber
                    .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(new BigDecimal(ecuqDesc.getFireMembrance()));
            fireWeight = fireRadius
                    .multiply(fireRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecquParameter.getLength())
                    .multiply(new BigDecimal(ecuqDesc.getFireRootNumber()))
                    .multiply(ecuqDesc.getFireStrand())
                    .multiply(new BigDecimal(fireArr[0]))//核心数
                    .multiply(conductorDensity);
            fireMoney = fireWeight.multiply(conductorUnitPrice);
            //单段火线外径
            fireDiameter = (ecuqDesc.getFireSilkNumber())
                    .multiply(getSilkPercent(ecuqDesc.getFireRootNumber()));
        }
        //零线
        if (areaArr.length == 2) {
            zeroArr = areaArr[1].split("\\*");
            //单根零线数据
            zeroRadius = zeroSilkNumber
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(new BigDecimal(ecuqDesc.getZeroMembrance()));
            zeroWeight = zeroRadius
                    .multiply(zeroRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecquParameter.getLength())
                    .multiply(new BigDecimal(ecuqDesc.getZeroRootNumber()))
                    .multiply(ecuqDesc.getZeroStrand())
                    .multiply(new BigDecimal(zeroArr[0]))//核心数
                    .multiply(conductorDensity);
            zeroMoney = zeroWeight.multiply(conductorUnitPrice);
            //单段零线外径
            zeroDiameter = (ecuqDesc.getZeroSilkNumber())
                    .multiply(getSilkPercent(ecuqDesc.getZeroRootNumber()));
        }
        //计算导体外径
        externalDiameter = getExternalDiameter(areaArr, fireDiameter, zeroDiameter);
        conductorWeight = fireWeight.add(zeroWeight);
        conductorMoney = fireMoney.add(zeroMoney);
        /*log.info("fireSilkNumber + " + fireSilkNumber);
        log.info("fireRootNumber + " + ecuqDesc.getFireRootNumber());
        log.info("fireRadius + " + fireRadius);
        log.info("fireWeight + " + fireWeight);
        log.info("fireMoney + " + fireMoney);
        log.info("fireDiameter + " + fireDiameter);
        log.info("zeroSilkNumber + " + zeroSilkNumber);
        log.info("zeroRadius + " + zeroRadius);
        log.info("zeroWeight + " + zeroWeight);
        log.info("zeroMoney + " + zeroMoney);
        log.info("zeroDiameter + " + zeroDiameter);
        log.info("externalDiameter + " + externalDiameter);
        log.info("conductorWeight + " + conductorWeight);
        log.info("conductorMoney + " + conductorMoney);*/
        //更新导体重量
        map.put("fireRadius", fireRadius);//粗芯半径
        map.put("zeroRadius", zeroRadius);//细芯半径
        map.put("fireWeight", fireWeight);//粗芯重量
        map.put("zeroWeight", zeroWeight);//细芯重量
        map.put("fireMoney", fireMoney);//粗芯金额
        map.put("zeroMoney", zeroMoney);//细芯金额
        map.put("fireDiameter", fireDiameter);//粗芯直径
        map.put("zeroDiameter", zeroDiameter);//细芯直径
        map.put("externalDiameter", externalDiameter);//导体直径
        map.put("conductorWeight", conductorWeight);//导体重量
        map.put("conductorMoney", conductorMoney);//导体金额
        return map;
    }

    //getMicatapeData
    public static Map<String, Object> getMicatapeData(EcuqInput ecuqInput,
                                                      EcuqDesc ecuqDesc,
                                                      EcbuMicatape ecbuMicatape,
                                                      BigDecimal fireDiameter,
                                                      BigDecimal zeroDiameter,
                                                      EcquParameter ecquParameter) {
        Map<String, Object> map = new HashMap<>();
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal micatapeWeight;//云母带重量
        BigDecimal micatapeMoney;//云母带金额
        BigDecimal micatapeThickness = ecuqDesc.getMicatapeThickness();//云母带厚度
        BigDecimal fireMicatapeRadius = new BigDecimal("0");//粗芯云母带半径
        BigDecimal fireMicatapeWeight = new BigDecimal("0");//粗芯云母带重量
        BigDecimal fireMicatapeMoney = new BigDecimal("0");//粗芯云母带金额
        BigDecimal zeroMicatapeRadius = new BigDecimal("0");//细芯云母带半径
        BigDecimal zeroMicatapeWeight = new BigDecimal("0");//细芯云母带重量
        BigDecimal zeroMicatapeMoney = new BigDecimal("0");//细芯云母带金额
        if (ecuqDesc.getEcbumId() != 0) {
            //火线云母带
            fireMicatapeRadius = fireDiameter
                    .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(micatapeThickness);
            fireMicatapeWeight = fireMicatapeRadius.multiply(fireMicatapeRadius)
                    .subtract(
                            fireDiameter
                                    .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                                    .multiply(fireDiameter
                                            .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecquParameter.getLength())
                    .multiply(ecbuMicatape.getDensity())
                    .multiply(new BigDecimal(fireArr[0]));
            fireMicatapeMoney = fireMicatapeWeight.multiply(ecbuMicatape.getUnitPrice());
            //零线云母带
            if (areaArr.length == 2) {
                zeroMicatapeRadius = zeroDiameter
                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                        .add(micatapeThickness);
                zeroArr = areaArr[1].split("\\*");
                zeroMicatapeWeight = zeroMicatapeRadius.multiply(zeroMicatapeRadius)
                        .subtract(
                                zeroDiameter
                                        .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                                        .multiply(zeroDiameter
                                                .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength())
                        .multiply(ecbuMicatape.getDensity())
                        .multiply(new BigDecimal(zeroArr[0]));
                zeroMicatapeMoney = zeroMicatapeWeight.multiply(ecbuMicatape.getUnitPrice());
            }
        }
        //System.out.println("fireMicatapeRadius + " + fireMicatapeRadius);
        //System.out.println("fireMicatapeWeight + " + fireMicatapeWeight);
        //System.out.println("fireMicatapeMoney + " + fireMicatapeMoney);
        //System.out.println("zeroMicatapeRadius + " + fireMicatapeRadius);
        //System.out.println("zeroMicatapeWeight + " + zeroMicatapeWeight);
        //System.out.println("zeroMicatapeMoney + " + zeroMicatapeMoney);
        //System.out.println("micatapeThickness + " + micatapeThickness);
        //System.out.println("micatapeWeight + " + micatapeWeight);
        //System.out.println("micatapeMoney + " + micatapeMoney);
        micatapeWeight = fireMicatapeWeight.add(zeroMicatapeWeight);
        micatapeMoney = fireMicatapeMoney.add(zeroMicatapeMoney);
        map.put("fireMicatapeRadius", fireMicatapeRadius);
        map.put("fireMicatapeWeight", fireMicatapeWeight);
        map.put("fireMicatapeMoney", fireMicatapeMoney);
        map.put("zeroMicatapeRadius", zeroMicatapeRadius);
        map.put("zeroMicatapeWeight", zeroMicatapeWeight);
        map.put("zeroMicatapeMoney", zeroMicatapeMoney);
        map.put("micatapeThickness", micatapeThickness);
        map.put("micatapeWeight", micatapeWeight);
        map.put("micatapeMoney", micatapeMoney);
        return map;
    }

    //getInsulationData
    public static Map<String, Object> getInsulationData(EcuqInput ecuqInput,
                                                        EcuqDesc ecuqDesc,
                                                        EcbuInsulation ecbuInsulation,
                                                        BigDecimal fireDiameter,
                                                        BigDecimal zeroDiameter,
                                                        BigDecimal fireMicatapeRadius,
                                                        BigDecimal zeroMicatapeRadius,
                                                        EcquParameter ecquParameter) {
        Map<String, Object> map = new HashMap<>();
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();//粗芯绝缘厚度
        BigDecimal fireInsulationRadius = new BigDecimal("0");//粗芯绝缘总半径
        BigDecimal fireInsulationWeight = new BigDecimal("0");//粗芯绝缘重量
        BigDecimal fireInsulationMoney = new BigDecimal("0");//粗芯绝缘金额
        BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();//细芯绝缘厚度
        BigDecimal zeroInsulationRadius = new BigDecimal("0");//细芯绝缘总半径
        BigDecimal zeroInsulationWeight = new BigDecimal("0");//细芯绝缘重量
        BigDecimal zeroInsulationMoney = new BigDecimal("0");//细芯绝缘金额
        BigDecimal insulationWeight;//绝缘重量
        BigDecimal insulationMoney;//绝缘金额
        if (ecuqDesc.getEcbuiId() != 0) {
            if (fireMicatapeRadius.compareTo(new BigDecimal("0")) == 0) {//没有云母带
                //粗芯绝缘
                fireInsulationRadius = fireDiameter
                        .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                        .add(insulationFireThickness);
                fireInsulationWeight = fireInsulationRadius.multiply(fireInsulationRadius)
                        .subtract(
                                fireDiameter
                                        .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                                        .multiply(fireDiameter
                                                .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength())
                        .multiply(ecbuInsulation.getDensity())
                        .multiply(new BigDecimal(fireArr[0]));
                fireInsulationMoney = fireInsulationWeight.multiply(ecbuInsulation.getUnitPrice());
                //细芯绝缘
                if (areaArr.length == 2) {
                    zeroArr = areaArr[1].split("\\*");
                    zeroInsulationRadius = zeroDiameter
                            .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                            .add(insulationZeroThickness);
                    zeroInsulationWeight = zeroInsulationRadius.multiply(zeroInsulationRadius)
                            .subtract(
                                    fireDiameter
                                            .divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                                            .multiply(
                                                    fireDiameter
                                                            .divide(new BigDecimal("2"), 16,
                                                                    RoundingMode.HALF_UP)
                                            ))
                            .multiply(BigDecimal.valueOf(Math.PI))
                            .multiply(ecquParameter.getLength())
                            .multiply(ecbuInsulation.getDensity())
                            .multiply(new BigDecimal(zeroArr[0]));
                    zeroInsulationMoney = zeroInsulationWeight.multiply(ecbuInsulation.getUnitPrice());
                }
            } else {//有云母带
                //粗芯绝缘
                fireInsulationRadius = fireMicatapeRadius.add(insulationFireThickness);
                fireInsulationWeight = fireInsulationRadius.multiply(fireInsulationRadius)
                        .subtract(fireMicatapeRadius.multiply(fireInsulationRadius))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength())
                        .multiply(ecbuInsulation.getDensity())
                        .multiply(new BigDecimal(fireArr[0]));
                fireInsulationMoney = fireInsulationWeight.multiply(ecbuInsulation.getUnitPrice());
                //细芯绝缘
                if (areaArr.length == 2) {
                    zeroArr = areaArr[1].split("\\*");
                    zeroInsulationRadius = zeroMicatapeRadius.add(insulationZeroThickness);
                    zeroInsulationWeight = zeroInsulationRadius.multiply(zeroInsulationRadius)
                            .subtract(zeroMicatapeRadius.multiply(zeroInsulationRadius))
                            .multiply(BigDecimal.valueOf(Math.PI))
                            .multiply(ecquParameter.getLength())
                            .multiply(ecbuInsulation.getDensity())
                            .multiply(new BigDecimal(zeroArr[0]));
                    zeroInsulationMoney = zeroInsulationWeight.multiply(ecbuInsulation.getUnitPrice());
                }
            }
        }
        insulationWeight = fireInsulationWeight.add(zeroInsulationWeight);
        insulationMoney = fireInsulationMoney.add(zeroInsulationMoney);
        /*log.info("fireInsulationRadius + " + fireInsulationRadius);
        log.info("fireInsulationDiameter + " + fireInsulationRadius.multiply(new BigDecimal("2")));
        log.info("insulationFireThickness + " + insulationFireThickness);
        log.info("fireInsulationWeight + " + fireInsulationWeight);
        log.info("fireInsulationMoney + " + fireInsulationMoney);
        log.info("zeroInsulationDiameter + " + fireInsulationRadius);
        log.info("zeroInsulationWeight + " + zeroInsulationWeight);
        log.info("zeroInsulationMoney + " + zeroInsulationMoney);
        log.info("insulationWeight + " + insulationWeight);
        log.info("insulationMoney + " + insulationMoney);*/
        map.put("fireInsulationRadius", fireInsulationRadius);
        map.put("fireInsulationWeight", fireInsulationWeight);
        map.put("fireInsulationMoney", fireInsulationMoney);
        map.put("zeroInsulationRadius", zeroInsulationRadius);
        map.put("zeroInsulationWeight", zeroInsulationWeight);
        map.put("zeroInsulationMoney", zeroInsulationMoney);
        map.put("insulationFireThickness", insulationFireThickness);
        map.put("insulationWeight", insulationWeight);
        map.put("insulationMoney", insulationMoney);
        return map;
    }

    //getInfillingData 获取填充物数据
    public static Map<String, Object> getInfillingData(EcuqInput ecuqInput,
                                                       EcuqDesc ecuqDesc,
                                                       EcquParameter ecquParameter,
                                                       EcbuInfilling ecbuInfilling,
                                                       BigDecimal fireDiameter,
                                                       BigDecimal zeroDiameter,
                                                       BigDecimal micatapeThickness,
                                                       BigDecimal insulationFireThickness,
                                                       BigDecimal insulationZeroThickness) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal externalDiameter;
        BigDecimal wideDiameter;//粗芯直径
        BigDecimal fineDiameter;//细芯直径
        BigDecimal infillingWeight = new BigDecimal("0");//填充物重量
        BigDecimal infillingMoney = new BigDecimal("0");//填充物金额
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        //log.info("fireDiameter + " + fireDiameter);
        wideDiameter = fireDiameter//粗芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationFireThickness.multiply(new BigDecimal("2")));
        //log.info("wideDiameter + " + wideDiameter);
        fineDiameter = zeroDiameter//细芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationZeroThickness.multiply(new BigDecimal("2")));
        externalDiameter = getExternalDiameter(areaArr, wideDiameter, fineDiameter);//外径
        if (ecuqDesc.getEcbuinId() != 0) {
            BigDecimal totalInfillingVolume = externalDiameter
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .multiply(externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecquParameter.getLength());
            BigDecimal fireInfillingRadius = fireDiameter.divide(new BigDecimal("2"), 16,
                            RoundingMode.HALF_UP)
                    .add(micatapeThickness)
                    .add(insulationFireThickness);
            BigDecimal fireInfillingVolume = fireInfillingRadius.multiply(fireInfillingRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecquParameter.getLength());
            BigDecimal zeroInfillingVolume = new BigDecimal("0");
            if (areaArr.length == 2) {
                BigDecimal zeroInfillingRadius = zeroDiameter.divide(new BigDecimal("2"), 16,
                                RoundingMode.HALF_UP)
                        .add(micatapeThickness)
                        .add(insulationZeroThickness);
                zeroInfillingVolume = zeroInfillingRadius.multiply(zeroInfillingRadius)
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength());
            }
            BigDecimal remainInfillingVolume = totalInfillingVolume.subtract(fireInfillingVolume)
                    .subtract(zeroInfillingVolume);
            log.info("remainInfillingVolume + " + remainInfillingVolume);
            infillingWeight = remainInfillingVolume
                    .multiply(ecbuInfilling.getDensity());
            infillingMoney = infillingWeight
                    .multiply(ecbuInfilling.getUnitPrice());

        }
        /*log.info("wideDiameter + " + wideDiameter);
        log.info("fineDiameter + " + fineDiameter);
        log.info("infillingWeight + " + infillingWeight);
        log.info("infillingMoney + " + infillingMoney);*/
        map.put("externalDiameter", externalDiameter);
        map.put("wideDiameter", wideDiameter);//粗芯直径
        map.put("fineDiameter", fineDiameter);
        map.put("infillingWeight", infillingWeight);
        map.put("infillingMoney", infillingMoney);
        return map;
    }

    //getBagData 获取包带数据
    public static Map<String, Object> getBagData(EcuqInput ecuqInput,
                                                 EcuqDesc ecuqDesc,
                                                 EcquParameter ecquParameter,
                                                 EcbuBag ecbuBag,
                                                 BigDecimal externalDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal bagRadius = new BigDecimal("0");//包带半径
        BigDecimal bagWeight = new BigDecimal("0");//包带重量
        BigDecimal bagMoney = new BigDecimal("0");//包带金额
        BigDecimal multiply = externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                .multiply(externalDiameter
                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP));
        BigDecimal bagDiameter = new BigDecimal("0");
        if (ecuqInput.getSilkName().contains("22") || ecuqInput.getSilkName().contains("23")) {//凯装
            if (ecuqDesc.getEcbub22Id() != 0) {
                bagRadius = externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                        .add(ecuqDesc.getBag22Thickness());
                bagDiameter = bagRadius.multiply(new BigDecimal("2"));
                bagWeight = ((bagRadius
                        .multiply(bagRadius))
                        .subtract(multiply))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecbuBag.getDensity())
                        .multiply(ecquParameter.getLength());
                bagMoney = bagWeight.multiply(ecbuBag.getUnitPrice());
            }
        } else {
            if (ecuqDesc.getEcbubId() != 0) {
                //Ecbu_bag ecbBag = ecbuBagService.getObjectPassId(ecuqDesc.getEcbubId());
                bagRadius = externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                        .add(ecuqDesc.getBagThickness());
                bagDiameter = bagRadius.multiply(new BigDecimal("2"));
                bagWeight = ((bagRadius
                        .multiply(bagRadius))
                        .subtract(multiply))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecbuBag.getDensity())
                        .multiply(ecquParameter.getLength());
                bagMoney = bagWeight.multiply(ecbuBag.getUnitPrice());
            }
        }
		/*System.out.println("bagRadius + " + bagRadius);
		System.out.println("bagWeight + " + bagWeight);
		System.out.println("bagMoney + " + bagMoney);*/
        map.put("externalDiameter", externalDiameter);
        map.put("bagDiameter", bagDiameter);
        map.put("bagRadius", bagRadius);
        map.put("bagWeight", bagWeight);
        map.put("bagMoney", bagMoney);
        return map;
    }

    //getSteelbandData
    public static Map<String, Object> getSteelbandData(EcuqDesc ecuqDesc,
                                                       EcquParameter ecquParameter,
                                                       EcbuSteelband ecbuSteelband,
                                                       BigDecimal externalDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal totalSteelbandRadius = new BigDecimal("0");//钢带总半径
        BigDecimal totalSteelbandVolume = new BigDecimal("0");//钢带总体积
        BigDecimal innerSteelbandRadius = new BigDecimal("0");//钢带内半径
        BigDecimal innerSteelbandVolume = new BigDecimal("0");//钢带内部体积
        BigDecimal remainSteelbandVolume = new BigDecimal("0");//钢带体积
        BigDecimal steelbandWeight = new BigDecimal("0");//钢带重量
        BigDecimal steelbandMoney = new BigDecimal("0");//钢带金额
        if (ecuqDesc.getEcbusbId() != 0 && ecuqDesc.getSteelbandThickness()
                .compareTo(new BigDecimal("0")) != 0) {
            totalSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 16,
                            RoundingMode.HALF_UP)//外径
                    .add(ecuqDesc.getBagThickness())//包带
                    .add(ecuqDesc.getShieldThickness())//屏蔽
                    .add(ecuqDesc.getSteelbandThickness())//钢带
            ;
            totalSteelbandVolume = totalSteelbandRadius
                    .multiply(totalSteelbandRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecquParameter.getLength());
            innerSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 16,
                            RoundingMode.HALF_UP)
                    .add(ecuqDesc.getBagThickness())
                    .add(ecuqDesc.getShieldThickness());
            innerSteelbandVolume = innerSteelbandRadius.multiply(innerSteelbandRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecquParameter.getLength());
            remainSteelbandVolume = (totalSteelbandVolume
                    .subtract(innerSteelbandVolume))
                    .multiply(new BigDecimal(ecuqDesc.getSteelbandStorey()));
            steelbandWeight = remainSteelbandVolume.multiply(ecbuSteelband.getDensity());
            steelbandMoney = steelbandWeight.multiply(ecbuSteelband.getUnitPrice());
        }
		/*System.out.println("totalSteelbandRadius + " + totalSteelbandRadius);
		System.out.println("totalSteelbandVolume + " + totalSteelbandVolume);
		System.out.println("innerSteelbandRadius + " + totalSteelbandRadius);
		System.out.println("innerSteelbandVolume + " + innerSteelbandVolume);
		System.out.println("remainSteelbandVolume + " + remainSteelbandVolume);
		System.out.println("steelbandWeight + " + steelbandWeight);
		System.out.println("steelbandMoney + " + steelbandMoney);*/
        map.put("totalSteelbandVolume", totalSteelbandVolume);
        map.put("innerSteelbandRadius", innerSteelbandRadius);
        map.put("steelbandDiameter", totalSteelbandRadius.multiply(new BigDecimal("2")));
        map.put("innerSteelbandVolume", innerSteelbandVolume);
        map.put("remainSteelbandVolume", remainSteelbandVolume);
        map.put("steelbandWeight", steelbandWeight);
        map.put("steelbandMoney", steelbandMoney);
        return map;
    }

    //getSheathData 获取护套数据
    public static Map<String, Object> getSheathData(EcuqInput ecuqInput,
                                                    EcuqDesc ecuqDesc,
                                                    EcquParameter ecquParameter,
                                                    EcbuSheath ecbuSheath,
                                                    BigDecimal externalDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal totalSheathRadius = new BigDecimal("0");//护套总半径
        BigDecimal totalSheathVolume = new BigDecimal("0");//护套总体积
        BigDecimal innerSheathRadius = new BigDecimal("0");//护套内半径
        BigDecimal innerSheathVolume = new BigDecimal("0");//护套内体积
        BigDecimal remainSheathVolume = new BigDecimal("0");//护套体积
        BigDecimal sheathWeight = new BigDecimal("0");//护套重量
        BigDecimal sheathMoney = new BigDecimal("0");//护套金额
        if (ecuqInput.getSilkName().contains("22") || ecuqInput.getSilkName().contains("23")) {//凯装
            if (ecuqDesc.getEcbusid() != 0 && ecuqDesc.getSheathThickness()
                    .compareTo(new BigDecimal("0")) != 0) {
                totalSheathRadius = externalDiameter
                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)//外径
                        .add(ecuqDesc.getBag22Thickness())//包带
                        .add(ecuqDesc.getShieldThickness())//屏蔽
                        .add(ecuqDesc.getSteelbandThickness()
                                .multiply(new BigDecimal(ecuqDesc.getSteelbandStorey())))//钢带
                        .add(ecuqDesc.getSheath22Thickness())//护套
                ;
                log.info("sheath23Thickness + " + ecuqDesc.getSheath22Thickness());
                totalSheathVolume = totalSheathRadius
                        .multiply(totalSheathRadius)
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength());
                innerSheathRadius = externalDiameter
                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                        .add(ecuqDesc.getBag22Thickness())
                        .add(ecuqDesc.getShieldThickness())
                        .add(ecuqDesc.getSteelbandThickness());
                innerSheathVolume = innerSheathRadius
                        .multiply(innerSheathRadius)
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength());
                remainSheathVolume = (totalSheathVolume
                        .subtract(innerSheathVolume));
                sheathWeight = remainSheathVolume.multiply(ecbuSheath.getDensity());
                sheathMoney = sheathWeight.multiply(ecbuSheath.getUnitPrice());
            }
        } else {
            if (ecuqDesc.getEcbusid() != 0 && ecuqDesc.getSheathThickness()
                    .compareTo(new BigDecimal("0")) != 0) {
                totalSheathRadius = externalDiameter.divide(new BigDecimal("2"), 16,
                                RoundingMode.HALF_UP)//外径
                        .add(ecuqDesc.getBagThickness())//包带
                        .add(ecuqDesc.getShieldThickness())//屏蔽
                        .add(ecuqDesc.getSteelbandThickness()
                                .multiply(new BigDecimal(ecuqDesc.getSteelbandStorey())))//钢带
                        .add(ecuqDesc.getSheathThickness())//护套
                ;
                totalSheathVolume = totalSheathRadius
                        .multiply(totalSheathRadius)
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength());
                innerSheathRadius = externalDiameter.divide(new BigDecimal("2"), 16,
                                RoundingMode.HALF_UP)
                        .add(ecuqDesc.getBagThickness())
                        .add(ecuqDesc.getShieldThickness())
                        .add(ecuqDesc.getSteelbandThickness());
                innerSheathVolume = innerSheathRadius
                        .multiply(innerSheathRadius)
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecquParameter.getLength());
                remainSheathVolume = (totalSheathVolume
                        .subtract(innerSheathVolume));
                sheathWeight = remainSheathVolume.multiply(ecbuSheath.getDensity());
                sheathMoney = sheathWeight.multiply(ecbuSheath.getUnitPrice());
            }
        }
		/*System.out.println("totalSheathRadius + " + totalSheathRadius);
		System.out.println("totalSheathVolume + " + totalSheathVolume);
		System.out.println("innerSheathRadius + " + innerSheathRadius);
		System.out.println("innerSheathVolume + " + innerSheathVolume);
		System.out.println("remainSheathVolume + " + remainSheathVolume);
		System.out.println("sheathWeight + " + sheathWeight.setScale(6,RoundingMode.HALF_UP));
		System.out.println("sheathMoney + " + sheathMoney.setScale(6,RoundingMode.HALF_UP));*/
        map.put("totalSheathRadius", totalSheathRadius);
        map.put("sheathDiameter", totalSheathRadius.multiply(new BigDecimal("2")));
        map.put("totalSheathVolume", totalSheathVolume);
        map.put("innerSheathRadius", innerSheathRadius);
        map.put("innerSheathVolume", innerSheathVolume);
        map.put("remainSheathVolume", remainSheathVolume);
        map.put("sheathWeight", sheathWeight);
        map.put("sheathMoney", sheathMoney);
        return map;
    }

    //getBillPercentData 获取开发票计算结果
    public static Map<String, Object> getBillPercentData(EcuqInput ecuqInput,
                                                         EcduCompany company,
                                                         BigDecimal unitMoney,
                                                         EcbulUnit ecbulUnit) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal billSingleMoney = new BigDecimal("0");//开票单价
        BigDecimal billComputeMoney = new BigDecimal("0");//开票小计
        if (company.getBillPercentType() == 1) {//算法1
            billSingleMoney = unitMoney
                    .divide(
                            (new BigDecimal("1")
                                    .subtract(ecuqInput.getBillPercent())), 6, RoundingMode.HALF_UP
                    );//开票单价
            billComputeMoney = unitMoney.divide(new BigDecimal("1")
                            .subtract(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()));//开票小计
            if (ecbulUnit != null) {//判断单位是否为米
                billComputeMoney = unitMoney.divide(new BigDecimal("1")
                                .subtract(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                        .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));//开票小计
            }
        } else if (company.getBillPercentType() == 2) {//算法2
            billSingleMoney = unitMoney.multiply(new BigDecimal("1").add(ecuqInput.getBillPercent()));//开票单价
            billComputeMoney = unitMoney.multiply(new BigDecimal("1").add(ecuqInput.getBillPercent()))
                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()));//开票小计
            if (ecbulUnit != null) {//判断单位是否为米
                billComputeMoney = unitMoney.multiply(new BigDecimal("1").add(ecuqInput.getBillPercent()))
                        .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                        .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));//开票小计
            }
        }
        map.put("billSingleMoney", billSingleMoney);
        map.put("billComputeMoney", billComputeMoney);
        return map;
    }

    //getDeliveryData 获取快递数据
    public static Map<String, Object> getDeliveryData(EcuQuoted ecuQuoted,
                                                      List<DeliveryObj> listDeliveryPrice,
                                                      EcbudDelivery dDelivery, int ecbudId) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal price = new BigDecimal("0");//快递总价
        DeliveryObj objectDelivery = new DeliveryObj();
        if (dDelivery == null) {
            objectDelivery = listDeliveryPrice.get(0);//默认选最便宜的快递
        } else {
            if (ecbudId != -1) {
                if (ecuQuoted.getEcbudId() == 0) {
                    log.info(CommonFunction.getGson().toJson(listDeliveryPrice));
                    listDeliveryPrice.get((dDelivery.getSortId() - 1)).setDSelect(true);
                    objectDelivery = listDeliveryPrice.get((dDelivery.getSortId() - 1));
                    //ecbudId = objectDelivery.getEcbudId();
                } else {
                    for (DeliveryObj deliveryObj : listDeliveryPrice) {
                        if (ecuQuoted.getEcbudId() == deliveryObj.getEcbudId()) {
                            objectDelivery = deliveryObj;
                            //ecbudId = deliveryObj.getEcbudId();
                        }
                    }
                }
            }
        }
        map.put("price", price);
        map.put("objectDelivery", CommonFunction.getGson().toJson(objectDelivery));
        return map;
    }

    //getSilkPercent 对应丝号倍数
    public static BigDecimal getSilkPercent(int rootNumber) {
        new BigDecimal("0");
        BigDecimal silkPercent;
        silkPercent = switch (rootNumber) {
            case 1 -> new BigDecimal("1");
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
            default -> new BigDecimal("0");
        };
        return silkPercent;
    }

    //getAverageDiameter 外径的平均数
    public static BigDecimal getAverageDiameter(int fireSegment,
                                                BigDecimal fireDiameter,
                                                int zeroSegment, BigDecimal zeroDiameter) {
        BigDecimal averageDiameter;
        averageDiameter = new BigDecimal(fireSegment).multiply(fireDiameter)
                .add(new BigDecimal(zeroSegment).multiply(zeroDiameter))
                .divide(new BigDecimal(fireSegment).add(new BigDecimal(zeroSegment)), 16, RoundingMode.HALF_UP);
        return averageDiameter;
    }

    //getExternalDiameter 获取外径
    public static BigDecimal getExternalDiameter(String[] areaArr,
                                                 BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        BigDecimal externalDiameter = new BigDecimal("0");//外径
        BigDecimal averageDiameter;
        int fireNumber = Integer.parseInt(areaArr[0].split("\\*")[0]);//粗芯段数
        if (areaArr.length == 1) {//零线数为0时视为等圆
            externalDiameter = getSilkPercent(fireNumber).multiply(fireDiameter);
        } else {//既有火线又有零线
            int zeroNumber = Integer.parseInt(areaArr[1].split("\\*")[0]);//细芯段数
            if (fireNumber == 2 && zeroNumber == 1) {
                if (zeroDiameter.compareTo(fireDiameter.multiply(new BigDecimal("2"))
                        .divide(new BigDecimal("3"), 6, RoundingMode.HALF_UP)) < 1) {
                    externalDiameter = fireDiameter.multiply(new BigDecimal("2"));
                } else {
                    externalDiameter = new BigDecimal("2.16")
                            .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
                }
            } else if (fireNumber == 3 && zeroNumber == 1) {//有一根小截面的四芯电缆
                externalDiameter = new BigDecimal("2.42")
                        .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
            } else if (fireNumber == 4 && zeroNumber == 1) {//有一根小截面的五芯电缆
                externalDiameter = new BigDecimal("2.70")
                        .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
            } else if (fireNumber == 3 && zeroNumber == 2) {//有两根小截面的五芯电缆 计算地线
                averageDiameter = getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter);
                externalDiameter = new BigDecimal("2.70").multiply(averageDiameter);
            }
        }
        return externalDiameter;
    }
}
