package org.jeecg.modules.cable.tools;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.model.systemEcable.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.jeecg.modules.cable.tools.EcableFunction.getExternalDiameter;
import static org.jeecg.modules.cable.tools.EcableFunction.getSilkPercent;


@Service
@Slf4j
public class EcableEcOfferFunction {
    @Resource
    EcbConductorModel ecbConductorModel;//导体
    @Resource
    EcbMicatapeModel ecbMicatapeModel;//云母带
    @Resource
    EcbInsulationModel ecbInsulationModel;//绝缘
    @Resource
    EcbInfillingModel ecbInfillingModel;//填充物
    @Resource
    EcbBagModel ecbBagModel;//包带
    @Resource
    EcbSteelbandModel ecbSteelbandModel;//钢带
    @Resource
    EcbSheathModel ecbSheathModel;//护套

    //getConductorData 获取导体数据
    public Map<String, Object> getConductorData(EcOffer ecOffer) {
        Map<String, Object> map = new HashMap<>();
        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        int ecbcId = 0;
        if (ecOffer.getEcsId() == 2) {//YJV
            ecbcId = 3;
        } else if (ecOffer.getEcsId() == 10) {//YJLV
            ecbcId = 4;
        }
        EcbConductor ecbuConductor = ecbConductorModel.getObjectPassEcbcId(ecbcId);
        BigDecimal conductorDensity = ecbuConductor.getDensity();
        BigDecimal conductorUnitPrice = ecbuConductor.getUnitPrice();
        BigDecimal fireSilkNumber = ecOffer.getFireSilkNumber();//粗芯丝号
        BigDecimal zeroSilkNumber = ecOffer.getZeroSilkNumber();//细芯丝号
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
        //log.info(CommonFunction.getGson().toJson(fireArr));
        if (fireArr.length == 2) {//有一个*号时
            //单根火线数据
            fireRadius = fireSilkNumber
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(new BigDecimal(ecOffer.getFireMembrance()));
            fireWeight = fireRadius
                    .multiply(fireRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(ecOffer.getFireRootNumber()))
                    .multiply(ecOffer.getFireStrand())
                    .multiply(new BigDecimal(fireArr[0]))//核心数
                    .multiply(conductorDensity);
            fireMoney = fireWeight.multiply(conductorUnitPrice);
            //单段火线外径
            fireDiameter = (ecOffer.getFireSilkNumber())
                    .multiply(getSilkPercent(ecOffer.getFireRootNumber()));
        }
        //零线
        if (areaArr.length == 2) {
            zeroArr = areaArr[1].split("\\*");
            //单根零线数据
            zeroRadius = zeroSilkNumber
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(new BigDecimal(ecOffer.getZeroMembrance()));
            zeroWeight = zeroRadius
                    .multiply(zeroRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(ecOffer.getZeroRootNumber()))
                    .multiply(ecOffer.getZeroStrand())
                    .multiply(new BigDecimal(zeroArr[0]))//核心数
                    .multiply(conductorDensity);
            zeroMoney = zeroWeight.multiply(conductorUnitPrice);
            //单段零线外径
            zeroDiameter = (ecOffer.getZeroSilkNumber())
                    .multiply(getSilkPercent(ecOffer.getZeroRootNumber()));
        }
        //计算导体外径
        externalDiameter = getExternalDiameter(areaArr, fireDiameter, zeroDiameter);
        conductorWeight = fireWeight.add(zeroWeight);
        conductorMoney = fireMoney.add(zeroMoney);
        /*log.info("fireSilkNumber + " + fireSilkNumber);
        log.info("fireRootNumber + " + ecuOffer.getFireRootNumber());
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
    public Map<String, Object> getMicatapeData(EcOffer ecOffer,
                                               BigDecimal fireDiameter,
                                               BigDecimal zeroDiameter) {
        Map<String, Object> map = new HashMap<>();
        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal micatapeWeight;//云母带重量
        BigDecimal micatapeMoney;//云母带金额
        BigDecimal micatapeThickness = ecOffer.getMicatapeThickness();//云母带厚度
        BigDecimal fireMicatapeRadius = new BigDecimal("0");//粗芯云母带半径
        BigDecimal fireMicatapeWeight = new BigDecimal("0");//粗芯云母带重量
        BigDecimal fireMicatapeMoney = new BigDecimal("0");//粗芯云母带金额
        BigDecimal zeroMicatapeRadius = new BigDecimal("0");//细芯云母带半径
        BigDecimal zeroMicatapeWeight = new BigDecimal("0");//细芯云母带重量
        BigDecimal zeroMicatapeMoney = new BigDecimal("0");//细芯云母带金额
        if (ecOffer.getEcbmId() != 0) {
            EcbMicatape ecbuMicatape = ecbMicatapeModel.getObjectPassEcbmId(ecOffer.getEcbmId());
            //火线云母带
            fireMicatapeRadius = fireDiameter
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(micatapeThickness);
            fireMicatapeWeight = fireMicatapeRadius.multiply(fireMicatapeRadius)
                    .subtract(
                            fireDiameter
                                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                                    .multiply(fireDiameter.divide(new BigDecimal("2"), 6,
                                            RoundingMode.HALF_UP)))
                    .multiply(BigDecimal.valueOf(Math.PI))
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
                                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                                        .multiply(zeroDiameter.divide(new BigDecimal("2"), 6,
                                                RoundingMode.HALF_UP)))
                        .multiply(BigDecimal.valueOf(Math.PI))
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
    public Map<String, Object> getInsulationData(EcOffer ecOffer,
                                                 BigDecimal fireDiameter,
                                                 BigDecimal zeroDiameter,
                                                 BigDecimal fireMicatapeRadius,
                                                 BigDecimal zeroMicatapeRadius) {
        Map<String, Object> map = new HashMap<>();
        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal insulationFireThickness = ecOffer.getInsulationFireThickness();//粗芯绝缘厚度
        BigDecimal fireInsulationRadius = new BigDecimal("0");//粗芯绝缘总半径
        BigDecimal fireInsulationWeight = new BigDecimal("0");//粗芯绝缘重量
        BigDecimal fireInsulationMoney = new BigDecimal("0");//粗芯绝缘金额
        BigDecimal insulationZeroThickness = ecOffer.getInsulationZeroThickness();//细芯绝缘厚度
        BigDecimal zeroInsulationRadius = new BigDecimal("0");//细芯绝缘总半径
        BigDecimal zeroInsulationWeight = new BigDecimal("0");//细芯绝缘重量
        BigDecimal zeroInsulationMoney = new BigDecimal("0");//细芯绝缘金额
        BigDecimal insulationWeight;//绝缘重量
        BigDecimal insulationMoney;//绝缘金额
        if (ecOffer.getEcbiId() != 0) {
            EcbInsulation ecbuInsulation = ecbInsulationModel.getObjectPassEcbiId(ecOffer.getEcbiId());
            if (fireMicatapeRadius.compareTo(new BigDecimal("0")) == 0) {//没有云母带
                //粗芯绝缘
                fireInsulationRadius = fireDiameter
                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                        .add(insulationFireThickness);
                fireInsulationWeight = fireInsulationRadius.multiply(fireInsulationRadius)
                        .subtract(
                                fireDiameter
                                        .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                                        .multiply(fireDiameter.divide(new BigDecimal("2"), 6,
                                                RoundingMode.HALF_UP)))
                        .multiply(BigDecimal.valueOf(Math.PI))
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
                                            .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                                            .multiply(
                                                    fireDiameter
                                                            .divide(new BigDecimal("2"), 7,
                                                                    RoundingMode.HALF_UP)
                                            ))
                            .multiply(BigDecimal.valueOf(Math.PI))
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
                            .multiply(ecbuInsulation.getDensity())
                            .multiply(new BigDecimal(zeroArr[0]));
                    zeroInsulationMoney = zeroInsulationWeight.multiply(ecbuInsulation.getUnitPrice());
                }
            }
        }
        insulationWeight = fireInsulationWeight.add(zeroInsulationWeight);
        insulationMoney = fireInsulationMoney.add(zeroInsulationMoney);
		/*System.out.println("fireInsulationDiameter + " + fireInsulationRadius.multiply(new BigDecimal("2")));
		System.out.println("insulationFireThickness + " + insulationFireThickness);
		System.out.println("fireInsulationWeight + " + fireInsulationWeight);
		System.out.println("fireInsulationMoney + " + fireInsulationMoney);
		System.out.println("zeroInsulationDiameter + " + fireInsulationRadius);
		System.out.println("zeroInsulationWeight + " + zeroInsulationWeight);
		System.out.println("zeroInsulationMoney + " + zeroInsulationMoney);
		System.out.println("insulationWeight + " + insulationWeight);
		System.out.println("insulationMoney + " + insulationMoney);*/
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
    public Map<String, Object> getInfillingData(EcOffer ecOffer,
                                                BigDecimal fireDiameter,
                                                BigDecimal zeroDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal externalDiameter;
        BigDecimal wideDiameter;//粗芯直径
        BigDecimal fineDiameter;//细芯直径
        BigDecimal infillingWeight = new BigDecimal("0");//填充物重量
        BigDecimal infillingMoney = new BigDecimal("0");//填充物金额
        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
        BigDecimal micatapeThickness = ecOffer.getMicatapeThickness();
        BigDecimal insulationFireThickness = ecOffer.getInsulationFireThickness();
        BigDecimal insulationZeroThickness = ecOffer.getInsulationZeroThickness();
        wideDiameter = fireDiameter//粗芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationFireThickness.multiply(new BigDecimal("2")));
        fineDiameter = zeroDiameter//细芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationZeroThickness.multiply(new BigDecimal("2")));
        externalDiameter = getExternalDiameter(areaArr, wideDiameter, fineDiameter);//外径
        if (ecOffer.getEcbinId() != 0) {
            EcbInfilling ecbuInfilling = ecbInfillingModel.getObjectPassEcbinId(ecOffer.getEcbinId());
            BigDecimal totalInfillingVolume = externalDiameter
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .multiply(externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP))
                    .multiply(BigDecimal.valueOf(Math.PI));
            BigDecimal fireInfillingRadius = fireDiameter.divide(new BigDecimal("2"), 6,
                            RoundingMode.HALF_UP)
                    .add(micatapeThickness)
                    .add(insulationFireThickness);
            BigDecimal fireInfillingVolume = fireInfillingRadius.multiply(fireInfillingRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            BigDecimal zeroInfillingVolume = new BigDecimal("0");
            if (areaArr.length == 2) {
                BigDecimal zeroInfillingRadius = zeroDiameter.divide(new BigDecimal("2"), 6,
                                RoundingMode.HALF_UP)
                        .add(micatapeThickness)
                        .add(insulationZeroThickness);
                zeroInfillingVolume = zeroInfillingRadius.multiply(zeroInfillingRadius)
                        .multiply(BigDecimal.valueOf(Math.PI));
            }
            BigDecimal remainInfillingVolume = totalInfillingVolume.subtract(fireInfillingVolume)
                    .subtract(zeroInfillingVolume);
            infillingWeight = remainInfillingVolume
                    .multiply(ecbuInfilling.getDensity());
            infillingMoney = infillingWeight
                    .multiply(ecbuInfilling.getUnitPrice());

        }
        //System.out.println("wideDiameter + " + wideDiameter);
        // System.out.println("fineDiameter + " + fineDiameter);
        // System.out.println("infillingWeight + " + infillingWeight);
        // System.out.println("infillingMoney + " + infillingMoney);
        map.put("externalDiameter", externalDiameter);
        map.put("wideDiameter", wideDiameter);//粗芯直径
        map.put("fineDiameter", fineDiameter);
        map.put("infillingWeight", infillingWeight);
        map.put("infillingMoney", infillingMoney);
        return map;
    }

    //getBagData 获取包带数据
    public Map<String, Object> getBagData(EcOffer ecOffer, BigDecimal externalDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal bagRadius = new BigDecimal("0");//包带半径
        BigDecimal bagWeight = new BigDecimal("0");//包带重量
        BigDecimal bagMoney = new BigDecimal("0");//包带金额
        if (ecOffer.getEcbbId() != 0) {
            EcbBag ecbBag = ecbBagModel.getObjectPassEcbbId(ecOffer.getEcbbId());
            bagRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(ecOffer.getBagThickness());
            bagWeight = ((bagRadius
                    .multiply(bagRadius))
                    .subtract(externalDiameter.divide(new BigDecimal("2"), 16,
                                    RoundingMode.HALF_UP)
                            .multiply(externalDiameter.divide(new BigDecimal("2"), 6,
                                    RoundingMode.HALF_UP))))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecbBag.getDensity());
            bagMoney = bagWeight.multiply(ecbBag.getUnitPrice());
        }
		/*System.out.println("bagRadius + " + bagRadius);
		System.out.println("bagWeight + " + bagWeight);
		System.out.println("bagMoney + " + bagMoney);*/
        map.put("bagRadius", bagRadius);
        map.put("bagWeight", bagWeight);
        map.put("bagMoney", bagMoney);
        return map;
    }

    //getSteelbandData
    public Map<String, Object> getSteelbandData(EcOffer ecOffer,
                                                BigDecimal externalDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal totalSteelbandRadius;//钢带总半径
        BigDecimal totalSteelbandVolume = new BigDecimal("0");//钢带总体积
        BigDecimal innerSteelbandRadius = new BigDecimal("0");//钢带内半径
        BigDecimal innerSteelbandVolume = new BigDecimal("0");//钢带内部体积
        BigDecimal remainSteelbandVolume = new BigDecimal("0");//钢带体积
        BigDecimal steelbandWeight = new BigDecimal("0");//钢带重量
        BigDecimal steelbandMoney = new BigDecimal("0");//钢带金额
        if (ecOffer.getEcbsbId() != 0) {
            EcbSteelBand ecbuSteelband = ecbSteelbandModel.getObjectPassEcbsbId(ecOffer.getEcbsbId());
            totalSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 6,
                            RoundingMode.HALF_UP)//外径
                    .add(ecOffer.getBagThickness())//包带
                    .add(ecOffer.getShieldThickness())//屏蔽
                    .add(ecOffer.getSteelbandThickness())//钢带
            ;
            totalSteelbandVolume = totalSteelbandRadius
                    .multiply(totalSteelbandRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            innerSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 6,
                            RoundingMode.HALF_UP)
                    .add(ecOffer.getBagThickness())
                    .add(ecOffer.getShieldThickness());
            innerSteelbandVolume = innerSteelbandRadius.multiply(innerSteelbandRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            remainSteelbandVolume = (totalSteelbandVolume
                    .subtract(innerSteelbandVolume))
                    .multiply(new BigDecimal(ecOffer.getSteelbandStorey()));
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
        map.put("innerSteelbandVolume", innerSteelbandVolume);
        map.put("remainSteelbandVolume", remainSteelbandVolume);
        map.put("steelbandWeight", steelbandWeight);
        map.put("steelbandMoney", steelbandMoney);
        return map;
    }

    //getSheathData 获取护套数据
    public Map<String, Object> getSheathData(EcOffer ecOffer,
                                             BigDecimal externalDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal totalSheathRadius = new BigDecimal("0");//护套总半径
        BigDecimal totalSheathVolume = new BigDecimal("0");//护套总体积
        BigDecimal innerSheathRadius = new BigDecimal("0");//护套内半径
        BigDecimal innerSheathVolume = new BigDecimal("0");//护套内体积
        BigDecimal remainSheathVolume = new BigDecimal("0");//护套体积
        BigDecimal sheathWeight = new BigDecimal("0");//护套重量
        BigDecimal sheathMoney = new BigDecimal("0");//护套金额
        if (ecOffer.getEcbsid() != 0 && ecOffer.getSheathThickness()
                .compareTo(new BigDecimal("0")) != 0) {
            EcbSheath ecbuSheath = ecbSheathModel.getObjectPassEcbsId(ecOffer.getEcbsid());
            totalSheathRadius = externalDiameter.divide(new BigDecimal("2"), 6,
                            RoundingMode.HALF_UP)//外径
                    .add(ecOffer.getBagThickness())//包带
                    .add(ecOffer.getShieldThickness())//屏蔽
                    .add(ecOffer.getSteelbandThickness()
                            .multiply(new BigDecimal(ecOffer.getSteelbandStorey())))//钢带
                    .add(ecOffer.getSheathThickness())//护套
            ;
            totalSheathVolume = totalSheathRadius
                    .multiply(totalSheathRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            innerSheathRadius = externalDiameter.divide(new BigDecimal("2"), 6,
                            RoundingMode.HALF_UP)
                    .add(ecOffer.getBagThickness())
                    .add(ecOffer.getShieldThickness())
                    .add(ecOffer.getSteelbandThickness());
            innerSheathVolume = innerSheathRadius
                    .multiply(innerSheathRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            remainSheathVolume = (totalSheathVolume
                    .subtract(innerSheathVolume));
            sheathWeight = remainSheathVolume.multiply(ecbuSheath.getDensity());
            sheathMoney = sheathWeight.multiply(ecbuSheath.getUnitPrice());
        }
		/*System.out.println("totalSheathRadius + " + totalSheathRadius);
		System.out.println("totalSheathVolume + " + totalSheathVolume);
		System.out.println("innerSheathRadius + " + innerSheathRadius);
		System.out.println("innerSheathVolume + " + innerSheathVolume);
		System.out.println("remainSheathVolume + " + remainSheathVolume);
		System.out.println("sheathWeight + " + sheathWeight.setScale(6,RoundingMode.HALF_UP));
		System.out.println("sheathMoney + " + sheathMoney.setScale(6,RoundingMode.HALF_UP));*/
        map.put("totalSheathRadius", totalSheathRadius);
        map.put("totalSheathVolume", totalSheathVolume);
        map.put("innerSheathRadius", innerSheathRadius);
        map.put("innerSheathVolume", innerSheathVolume);
        map.put("remainSheathVolume", remainSheathVolume);
        map.put("sheathWeight", sheathWeight);
        map.put("sheathMoney", sheathMoney);
        return map;
    }
}
