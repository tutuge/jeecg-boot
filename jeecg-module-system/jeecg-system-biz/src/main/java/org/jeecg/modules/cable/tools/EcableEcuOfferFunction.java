package org.jeecg.modules.cable.tools;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.userEcable.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.jeecg.modules.cable.tools.ComputeFunction.insulationDataCompute;
import static org.jeecg.modules.cable.tools.EcableFunction.getExternalDiameter;
import static org.jeecg.modules.cable.tools.EcableFunction.getSilkPercent;

@Service
@Slf4j
public class EcableEcuOfferFunction {
    @Resource
    EcbuConductorModel ecbuConductorModel;// 导体
    @Resource
    EcbuMicatapeModel ecbuMicatapeModel;// 云母带
    @Resource
    EcbuInsulationModel ecbuInsulationModel;// 绝缘
    @Resource
    EcbuInfillingModel ecbuInfillingModel;// 填充物
    @Resource
    EcbuBagModel ecbuBagModel;// 包带
    @Resource
    EcbuSteelbandModel ecbuSteelbandModel;// 钢带
    @Resource
    EcbuSheathModel ecbuSheathModel;// 护套

    // getConductorData 获取导体数据
    public ConductorComputeExtendBo getConductorData(EcuOffer ecuOffer) {
        String[] areaArr = (ecuOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcbucId(ecuOffer.getEcbucId());
        // log.info("conductor + " + ecbuConductor);
        BigDecimal conductorDensity = ecbuConductor.getDensity();
        BigDecimal conductorUnitPrice = ecbuConductor.getUnitPrice();
        BigDecimal fireSilkNumber = ecuOffer.getFireSilkNumber();// 粗芯丝号
        BigDecimal zeroSilkNumber = ecuOffer.getZeroSilkNumber();// 细芯丝号
        Integer fireMembrance = ecuOffer.getFireMembrance(); //粗芯过膜
        Integer fireRootNumber = ecuOffer.getFireRootNumber(); //粗芯根数
        BigDecimal fireStrand = ecuOffer.getFireStrand(); //粗芯绞合系数
        Integer zeroMembrance = ecuOffer.getZeroMembrance(); //细芯过膜

        BigDecimal fireRadius = BigDecimal.ZERO;// 火线直径
        BigDecimal zeroRadius = BigDecimal.ZERO;// 零线直径
        BigDecimal fireWeight = BigDecimal.ZERO;// 粗芯重量
        BigDecimal zeroWeight = BigDecimal.ZERO;// 细芯重量
        BigDecimal fireMoney = BigDecimal.ZERO;// 粗芯金额
        BigDecimal zeroMoney = BigDecimal.ZERO;// 细芯金额
        BigDecimal fireDiameter = BigDecimal.ZERO;// 粗芯外径
        BigDecimal zeroDiameter = BigDecimal.ZERO;// 细芯外径
        BigDecimal externalDiameter;// 导体外径
        BigDecimal conductorMoney;
        BigDecimal conductorWeight;// 导体重量
        // log.info(CommonFunction.getGson().toJson(fireArr));
        if (fireArr.length == 2) {// 有一个*号时
            // 单根火线数据
            fireRadius = fireSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                    .add(new BigDecimal(fireMembrance));
            fireWeight = fireRadius
                    .multiply(fireRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(fireRootNumber))
                    .multiply(fireStrand)
                    .multiply(new BigDecimal(fireArr[0]))// 核心数
                    .multiply(conductorDensity);
            fireMoney = fireWeight.multiply(conductorUnitPrice);
            // 单段火线外径
            fireDiameter = fireSilkNumber.multiply(getSilkPercent(fireRootNumber));
        }
        // 零线
        if (areaArr.length == 2) {
            zeroArr = areaArr[1].split("\\*");
            // 单根零线数据
            zeroRadius = zeroSilkNumber
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(new BigDecimal(zeroMembrance));
            zeroWeight = zeroRadius
                    .multiply(zeroRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(ecuOffer.getZeroRootNumber()))
                    .multiply(ecuOffer.getZeroStrand())
                    .multiply(new BigDecimal(zeroArr[0]))// 核心数
                    .multiply(conductorDensity);
            zeroMoney = zeroWeight.multiply(conductorUnitPrice);
            // 单段零线外径
            zeroDiameter = (ecuOffer.getZeroSilkNumber()).multiply(getSilkPercent(ecuOffer.getZeroRootNumber()));
        }
        // 计算导体外径
        externalDiameter = getExternalDiameter(areaArr, fireDiameter, zeroDiameter);
        conductorWeight = fireWeight.add(zeroWeight);
        conductorMoney = fireMoney.add(zeroMoney);
        return new ConductorComputeExtendBo(fireRadius.stripTrailingZeros(), zeroRadius.stripTrailingZeros(),
                fireDiameter.stripTrailingZeros(), zeroDiameter.stripTrailingZeros(),
                externalDiameter.stripTrailingZeros(),
                fireWeight.stripTrailingZeros(), zeroWeight.stripTrailingZeros(), fireMoney.stripTrailingZeros(),
                zeroMoney.stripTrailingZeros(), conductorWeight.stripTrailingZeros(), conductorMoney.stripTrailingZeros());
    }

    // getMicatapeData
    public MicatapeComputeBo getMicatapeData(EcuOffer ecuOffer, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        String[] areaArr = (ecuOffer.getAreaStr()).split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        String[] zeroArr;
        BigDecimal micatapeWeight;// 云母带重量
        BigDecimal micatapeMoney;// 云母带金额
        BigDecimal micatapeThickness = ecuOffer.getMicatapeThickness();// 云母带厚度
        BigDecimal fireMicatapeRadius = BigDecimal.ZERO;// 粗芯云母带半径
        BigDecimal fireMicatapeWeight = BigDecimal.ZERO;// 粗芯云母带重量
        BigDecimal fireMicatapeMoney = BigDecimal.ZERO;// 粗芯云母带金额
        BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;// 细芯云母带半径
        BigDecimal zeroMicatapeWeight = BigDecimal.ZERO;// 细芯云母带重量
        BigDecimal zeroMicatapeMoney = BigDecimal.ZERO;// 细芯云母带金额
        if (ecuOffer.getEcbumId() != 0) {
            EcbuMicatape ecbuMicatape = ecbuMicatapeModel.getObjectPassEcbumId(ecuOffer.getEcbumId());
            // 火线云母带
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
            // 零线云母带
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

    public InsulationComputeBo getInsulationData(EcuOffer ecuOffer,
                                                 BigDecimal fireDiameter,
                                                 BigDecimal zeroDiameter,
                                                 BigDecimal fireMicatapeRadius,
                                                 BigDecimal zeroMicatapeRadius) {

        if (ecuOffer.getEcbuiId() != 0) {
            EcbuInsulation ecbuInsulation = ecbuInsulationModel.getObjectPassEcbuiId(ecuOffer.getEcbuiId());
            BigDecimal density = ecbuInsulation.getDensity();
            BigDecimal unitPrice = ecbuInsulation.getUnitPrice();
            String areaStr = ecuOffer.getAreaStr();
            BigDecimal insulationFireThickness = ecuOffer.getInsulationFireThickness();// 粗芯绝缘厚度
            BigDecimal insulationZeroThickness = ecuOffer.getInsulationZeroThickness();// 细芯绝缘厚度

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

    //  获取填充物数据
    public InfillingComputeBo getInfillingData(EcuOffer ecuOffer, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        BigDecimal externalDiameter;//导体外径
        BigDecimal wideDiameter;// 粗芯直径
        BigDecimal fineDiameter;// 细芯直径
        BigDecimal infillingWeight = BigDecimal.ZERO;// 填充物重量
        BigDecimal infillingMoney = BigDecimal.ZERO;// 填充物金额
        String[] areaArr = (ecuOffer.getAreaStr()).split("\\+");
        BigDecimal micatapeThickness = ecuOffer.getMicatapeThickness();
        BigDecimal insulationFireThickness = ecuOffer.getInsulationFireThickness();
        BigDecimal insulationZeroThickness = ecuOffer.getInsulationZeroThickness();
        // log.info("fireDiameter + " + fireDiameter);
        wideDiameter = fireDiameter// 粗芯直径
                //.add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationFireThickness.multiply(new BigDecimal("2")));
        // log.info("wideDiameter + " + wideDiameter);
        fineDiameter = zeroDiameter// 细芯直径
                //.add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationZeroThickness.multiply(new BigDecimal("2")));
        externalDiameter = getExternalDiameter(areaArr, wideDiameter, fineDiameter);// 外径
        if (ecuOffer.getEcbuinId() != 0) {
            EcbuInfilling ecbuInfilling = ecbuInfillingModel.getObjectAndEcbuinId(ecuOffer.getEcbuinId());
            BigDecimal totalInfillingVolume = externalDiameter
                    .divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .multiply(externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP))
                    .multiply(BigDecimal.valueOf(Math.PI));
            BigDecimal fireInfillingRadius = fireDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
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
            BigDecimal remainInfillingVolume = totalInfillingVolume.subtract(fireInfillingVolume).subtract(zeroInfillingVolume);
            //("remainInfillingVolume + " + remainInfillingVolume);
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

    // getBagData 获取包带数据
    public BagComputeBo getBagData(EcuOffer ecuOffer, BigDecimal externalDiameter) {
//        Map<String, Object> map = new HashMap<>();
        BigDecimal bagRadius = BigDecimal.ZERO;// 包带半径
        BigDecimal bagWeight = BigDecimal.ZERO;// 包带重量
        BigDecimal bagMoney = BigDecimal.ZERO;// 包带金额
        if (ecuOffer.getEcbubId() != 0) {
            EcbuBag ecbuBag = ecbuBagModel.getObjectPassEcbubId(ecuOffer.getEcbubId());
            bagRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(ecuOffer.getBagThickness());
            bagWeight = ((bagRadius.multiply(bagRadius))
                    .subtract(externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
                            .multiply(externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP))))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(ecbuBag.getDensity());
            bagMoney = bagWeight.multiply(ecbuBag.getUnitPrice());
        }
        return new BagComputeBo(bagRadius.stripTrailingZeros(),
                bagWeight.stripTrailingZeros(),
                bagMoney.stripTrailingZeros());
    }

    // getSteelbandData
    public SteelBandComputeBo getSteelbandData(EcuOffer ecuOffer, BigDecimal externalDiameter) {
//        Map<String, Object> map = new HashMap<>();
        BigDecimal totalSteelbandRadius;// 钢带总半径
        BigDecimal totalSteelbandVolume = BigDecimal.ZERO;// 钢带总体积
        BigDecimal innerSteelbandRadius = BigDecimal.ZERO;// 钢带内半径
        BigDecimal innerSteelbandVolume = BigDecimal.ZERO;// 钢带内部体积
        BigDecimal remainSteelbandVolume = BigDecimal.ZERO;// 钢带体积
        BigDecimal steelbandWeight = BigDecimal.ZERO;// 钢带重量
        BigDecimal steelbandMoney = BigDecimal.ZERO;// 钢带金额
        if (ecuOffer.getEcbusbId() != 0) {
            EcbuSteelband ecbuSteelband = ecbuSteelbandModel.getObjectPassEcbusbId(ecuOffer.getEcbusbId());
            totalSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)// 外径
                    .add(ecuOffer.getBagThickness())// 包带
                    .add(ecuOffer.getShieldThickness())// 屏蔽
                    .add(ecuOffer.getSteelbandThickness())// 钢带
            ;
            totalSteelbandVolume = totalSteelbandRadius
                    .multiply(totalSteelbandRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            innerSteelbandRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(ecuOffer.getBagThickness())
                    .add(ecuOffer.getShieldThickness());
            innerSteelbandVolume = innerSteelbandRadius.multiply(innerSteelbandRadius)
                    .multiply(BigDecimal.valueOf(Math.PI));
            remainSteelbandVolume = (totalSteelbandVolume
                    .subtract(innerSteelbandVolume))
                    .multiply(new BigDecimal(ecuOffer.getSteelbandStorey()));
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

    // getSheathData 获取护套数据
    public SheathComputeBo getSheathData(EcuOffer ecuOffer, BigDecimal externalDiameter) {
        BigDecimal totalSheathRadius = BigDecimal.ZERO;// 护套总半径
        BigDecimal totalSheathVolume = BigDecimal.ZERO;// 护套总体积
        BigDecimal innerSheathRadius = BigDecimal.ZERO;// 护套内半径
        BigDecimal innerSheathVolume = BigDecimal.ZERO;// 护套内体积
        BigDecimal remainSheathVolume = BigDecimal.ZERO;// 护套体积
        BigDecimal sheathWeight = BigDecimal.ZERO;// 护套重量
        BigDecimal sheathMoney = BigDecimal.ZERO;// 护套金额
        if (ecuOffer.getEcbuSheathId() != 0 && ecuOffer.getSheathThickness().compareTo(BigDecimal.ZERO) != 0) {
            EcbuSheath ecbuSheath = ecbuSheathModel.getObjectPassEcbusid(ecuOffer.getEcbuSheathId());
            totalSheathRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)// 外径
                    .add(ecuOffer.getBagThickness())// 包带厚度
                    .add(ecuOffer.getShieldThickness())// 屏蔽厚度
                    .add(ecuOffer.getSteelbandThickness()
                            .multiply(new BigDecimal(ecuOffer.getSteelbandStorey())))// 钢带厚度 = 钢带厚度*层数
                    .add(ecuOffer.getSheathThickness());// 护套厚度

            totalSheathVolume = totalSheathRadius.multiply(totalSheathRadius).multiply(BigDecimal.valueOf(Math.PI));
            innerSheathRadius = externalDiameter.divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP)
                    .add(ecuOffer.getBagThickness())
                    .add(ecuOffer.getShieldThickness())
                    .add(ecuOffer.getSteelbandThickness());
            innerSheathVolume = innerSheathRadius.multiply(innerSheathRadius).multiply(BigDecimal.valueOf(Math.PI));
            remainSheathVolume = (totalSheathVolume.subtract(innerSheathVolume));
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
