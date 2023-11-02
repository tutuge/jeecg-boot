package org.jeecg.modules.cable.tools;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.model.systemEcable.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.jeecg.modules.cable.tools.ComputeFunction.insulationDataCompute;
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
    public ConductorComputeExtendBo getConductorData(EcOffer ecOffer) {

        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        Integer ecbcId = 0;
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
        BigDecimal fireRadius = BigDecimal.ZERO;//火线直径
        BigDecimal zeroRadius = BigDecimal.ZERO;//零线直径
        BigDecimal fireWeight = BigDecimal.ZERO;//粗芯重量
        BigDecimal zeroWeight = BigDecimal.ZERO;//细芯重量
        BigDecimal fireMoney = BigDecimal.ZERO;//粗芯金额
        BigDecimal zeroMoney = BigDecimal.ZERO;//细芯金额
        BigDecimal fireDiameter = BigDecimal.ZERO;//粗芯外径
        BigDecimal zeroDiameter = BigDecimal.ZERO;//细芯外径
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
            fireDiameter = (ecOffer.getFireSilkNumber()).multiply(getSilkPercent(ecOffer.getFireRootNumber()));
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

    //getMicatapeData
    public MicatapeComputeBo getMicatapeData(EcOffer ecOffer, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal micatapeWeight;//云母带重量
        BigDecimal micatapeMoney;//云母带金额
        BigDecimal micatapeThickness = ecOffer.getMicatapeThickness();//云母带厚度
        BigDecimal fireMicatapeRadius = BigDecimal.ZERO;//粗芯云母带半径
        BigDecimal fireMicatapeWeight = BigDecimal.ZERO;//粗芯云母带重量
        BigDecimal fireMicatapeMoney = BigDecimal.ZERO;//粗芯云母带金额
        BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;//细芯云母带半径
        BigDecimal zeroMicatapeWeight = BigDecimal.ZERO;//细芯云母带重量
        BigDecimal zeroMicatapeMoney = BigDecimal.ZERO;//细芯云母带金额
        if (ecOffer.getEcbmId() != 0) {
            EcbMicatape ecbuMicatape = ecbMicatapeModel.getObjectPassEcbmId(ecOffer.getEcbmId());
            //火线云母带
            fireMicatapeRadius = fireDiameter
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(micatapeThickness);
            fireMicatapeWeight = fireMicatapeRadius.multiply(fireMicatapeRadius)
                    .subtract(fireDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                            .multiply(fireDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)))
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
                        .subtract(zeroDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                                .multiply(zeroDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .multiply(ecbuMicatape.getDensity())
                        .multiply(new BigDecimal(zeroArr[0]));
                zeroMicatapeMoney = zeroMicatapeWeight.multiply(ecbuMicatape.getUnitPrice());
            }
        }
        micatapeWeight = fireMicatapeWeight.add(zeroMicatapeWeight);
        micatapeMoney = fireMicatapeMoney.add(zeroMicatapeMoney);
        return new MicatapeComputeBo(fireMicatapeRadius.stripTrailingZeros(),
                fireMicatapeWeight.stripTrailingZeros(),
                fireMicatapeMoney.stripTrailingZeros(),
                zeroMicatapeRadius.stripTrailingZeros(),
                zeroMicatapeWeight.stripTrailingZeros(),
                zeroMicatapeMoney.stripTrailingZeros(),
                micatapeThickness.stripTrailingZeros(),
                micatapeWeight.stripTrailingZeros(),
                micatapeMoney.stripTrailingZeros());
    }

    //getInsulationData
    public InsulationComputeBo getInsulationData(EcOffer ecOffer,
                                                 BigDecimal fireDiameter,
                                                 BigDecimal zeroDiameter,
                                                 BigDecimal fireMicatapeRadius,
                                                 BigDecimal zeroMicatapeRadius) {

        if (ecOffer.getEcbiId() != 0) {
            EcbInsulation ecbuInsulation = ecbInsulationModel.getObjectPassEcbiId(ecOffer.getEcbiId());
            BigDecimal density = ecbuInsulation.getDensity();
            BigDecimal unitPrice = ecbuInsulation.getUnitPrice();
            String areaStr = ecOffer.getAreaStr();
            BigDecimal insulationFireThickness = ecOffer.getInsulationFireThickness();//粗芯绝缘厚度
            BigDecimal insulationZeroThickness = ecOffer.getInsulationZeroThickness();//细芯绝缘厚度

            return insulationDataCompute(density, unitPrice,
                    areaStr, insulationFireThickness,
                    insulationZeroThickness,
                    fireDiameter,
                    zeroDiameter,
                    fireMicatapeRadius,
                    zeroMicatapeRadius);
        }
        return new InsulationComputeBo();
    }

    //getInfillingData 获取填充物数据
    public InfillingComputeBo getInfillingData(EcOffer ecOffer, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal externalDiameter;
        BigDecimal wideDiameter;//粗芯直径
        BigDecimal fineDiameter;//细芯直径
        BigDecimal infillingWeight = BigDecimal.ZERO;//填充物重量
        BigDecimal infillingMoney = BigDecimal.ZERO;//填充物金额
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
            BigDecimal zeroInfillingVolume = BigDecimal.ZERO;
            if (areaArr.length == 2) {
                BigDecimal zeroInfillingRadius = zeroDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
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
        return new InfillingComputeBo(externalDiameter.stripTrailingZeros(),
                wideDiameter.stripTrailingZeros(),
                fineDiameter.stripTrailingZeros(),
                infillingWeight.stripTrailingZeros(),
                infillingMoney.stripTrailingZeros());
    }

    //getBagData 获取包带数据
    public BagComputeBo getBagData(EcOffer ecOffer, BigDecimal externalDiameter) {
        BigDecimal bagRadius = BigDecimal.ZERO;//包带半径
        BigDecimal bagWeight = BigDecimal.ZERO;//包带重量
        BigDecimal bagMoney = BigDecimal.ZERO;//包带金额
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
        return new BagComputeBo(bagRadius.stripTrailingZeros(),
                bagWeight.stripTrailingZeros(),
                bagMoney.stripTrailingZeros());
    }

    //getSteelbandData
    public SteelBandComputeBo getSteelbandData(EcOffer ecOffer, BigDecimal externalDiameter) {
        BigDecimal totalSteelbandRadius;//钢带总半径
        BigDecimal totalSteelbandVolume = BigDecimal.ZERO;//钢带总体积
        BigDecimal innerSteelbandRadius = BigDecimal.ZERO;//钢带内半径
        BigDecimal innerSteelbandVolume = BigDecimal.ZERO;//钢带内部体积
        BigDecimal remainSteelbandVolume = BigDecimal.ZERO;//钢带体积
        BigDecimal steelbandWeight = BigDecimal.ZERO;//钢带重量
        BigDecimal steelbandMoney = BigDecimal.ZERO;//钢带金额
        if (ecOffer.getEcbsbId() != 0) {
            EcbSteelBand ecbuSteelband = ecbSteelbandModel.getObjectPassEcbsbId(ecOffer.getEcbsbId());
            totalSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)//外径
                    .add(ecOffer.getBagThickness())//包带
                    .add(ecOffer.getShieldThickness())//屏蔽
                    .add(ecOffer.getSteelbandThickness());//钢带
            totalSteelbandVolume = totalSteelbandRadius.multiply(totalSteelbandRadius).multiply(BigDecimal.valueOf(Math.PI));
            innerSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
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
        return new SteelBandComputeBo(totalSteelbandVolume.stripTrailingZeros(),
                innerSteelbandRadius.stripTrailingZeros(),
                innerSteelbandVolume.stripTrailingZeros(),
                remainSteelbandVolume.stripTrailingZeros(),
                steelbandWeight.stripTrailingZeros(),
                steelbandMoney.stripTrailingZeros());
    }

    //getSheathData 获取护套数据
    public SheathComputeBo getSheathData(EcOffer ecOffer, BigDecimal externalDiameter) {
        BigDecimal totalSheathRadius = BigDecimal.ZERO;//护套总半径
        BigDecimal totalSheathVolume = BigDecimal.ZERO;//护套总体积
        BigDecimal innerSheathRadius = BigDecimal.ZERO;//护套内半径
        BigDecimal innerSheathVolume = BigDecimal.ZERO;//护套内体积
        BigDecimal remainSheathVolume = BigDecimal.ZERO;//护套体积
        BigDecimal sheathWeight = BigDecimal.ZERO;//护套重量
        BigDecimal sheathMoney = BigDecimal.ZERO;//护套金额
        if (ecOffer.getEcbsid() != 0 && ecOffer.getSheathThickness()
                .compareTo(new BigDecimal("0")) != 0) {
            EcbSheath ecbuSheath = ecbSheathModel.getObjectPassEcbsId(ecOffer.getEcbsid());
            totalSheathRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)//外径
                    .add(ecOffer.getBagThickness())//包带
                    .add(ecOffer.getShieldThickness())//屏蔽
                    .add(ecOffer.getSteelbandThickness().multiply(new BigDecimal(ecOffer.getSteelbandStorey())))//钢带
                    .add(ecOffer.getSheathThickness());//护套
            totalSheathVolume = totalSheathRadius
                    .multiply(totalSheathRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            innerSheathRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
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
        return new SheathComputeBo(totalSheathRadius.stripTrailingZeros(),
                totalSheathVolume.stripTrailingZeros(),
                innerSheathRadius.stripTrailingZeros(),
                innerSheathVolume.stripTrailingZeros(),
                remainSheathVolume.stripTrailingZeros(),
                sheathWeight.stripTrailingZeros(),
                sheathMoney.stripTrailingZeros());
    }
}
