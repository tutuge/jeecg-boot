package org.jeecg.modules.cable.tools;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.model.systemEcable.*;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.jeecg.modules.cable.tools.ComputeFunction.*;


@Service
@Slf4j
public class EcableEcOfferFunction {
    @Resource
    EcbConductorModel ecbConductorModel;//导体
    @Resource
    EcbMicaTapeModel ecbMicatapeModel;//云母带
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
    @Resource
    private EcqLevelService ecqLevelService;

    //getConductorData 获取导体数据
    public ConductorComputeExtendBo getConductorData(EcOffer ecOffer) {
        EcqLevel level = new EcqLevel();
        level.setEcqlId(ecOffer.getEcqlId());
        EcqLevel ecqLevel = ecqLevelService.getObject(level);
        EcbConductor ecbuConductor = ecbConductorModel.getObjectPassEcbcId(ecqLevel.getEcbcId());
        BigDecimal conductorDensity = ecbuConductor.getDensity();
        BigDecimal conductorUnitPrice = ecbuConductor.getUnitPrice();
        Integer zeroRootNumber = ecOffer.getZeroRootNumber();
        BigDecimal fireSilkNumber = ecOffer.getFireSilkNumber();//粗芯丝号
        BigDecimal zeroSilkNumber = ecOffer.getZeroSilkNumber();
        Integer fireMembrance = ecOffer.getFireMembrance();
        Integer fireRootNumber = ecOffer.getFireRootNumber();
        BigDecimal fireStrand = ecOffer.getFireStrand();
        Integer zeroMembrance = ecOffer.getZeroMembrance();
        BigDecimal zeroStrand = ecOffer.getZeroStrand();
        String areaStr = ecOffer.getAreaStr();
        return conductorDataCompute(conductorDensity,
                conductorUnitPrice,
                fireRootNumber,
                zeroRootNumber,
                fireSilkNumber,
                zeroSilkNumber,
                fireMembrance,
                fireStrand,
                zeroMembrance,
                zeroStrand,
                areaStr,
                BigDecimal.ONE);
    }

    //getMicatapeData
    public InternalComputeBo getMicaTapeData(EcOffer ecOffer, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        if (ecOffer.getEcbmId() != 0) {
            EcbMicaTape ecbuMicatape = ecbMicatapeModel.getObjectPassEcbmId(ecOffer.getEcbmId());
            String areaStr = ecOffer.getAreaStr();
            BigDecimal density = ecbuMicatape.getDensity();
            BigDecimal unitPrice = ecbuMicatape.getUnitPrice();
            BigDecimal micaTapeThickness = ecOffer.getMicatapeThickness();//云母带厚度

            return micaTapeDataCompute(areaStr,
                    density,
                    unitPrice,
                    micaTapeThickness,
                    fireDiameter,
                    zeroDiameter);
        }
        return new InternalComputeBo();
    }

    //getInsulationData
    public InternalComputeBo getInsulationData(EcOffer ecOffer,
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
        return new InternalComputeBo();
    }

    //getInfillingData 获取填充物数据
    public InfillingComputeBo getInfillingData(EcOffer ecOffer, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        if (ecOffer.getEcbinId() != 0) {
            EcbInfilling ecbuInfilling = ecbInfillingModel.getObjectPassEcbinId(ecOffer.getEcbinId());
            BigDecimal density = ecbuInfilling.getDensity();
            BigDecimal unitPrice = ecbuInfilling.getUnitPrice();
            String areaStr = ecOffer.getAreaStr();

            BigDecimal micaTapeThickness = ecOffer.getMicatapeThickness();
            BigDecimal insulationFireThickness = ecOffer.getInsulationFireThickness();
            BigDecimal insulationZeroThickness = ecOffer.getInsulationZeroThickness();

            return infillingDataCompute(density,
                    unitPrice,
                    areaStr,
                    micaTapeThickness,
                    insulationFireThickness,
                    insulationZeroThickness,
                    fireDiameter,
                    zeroDiameter);
        }
        return new InfillingComputeBo();
    }

    //getBagData 获取包带数据
    public ExternalComputeBo getBagData(EcOffer ecOffer, BigDecimal externalDiameter) {
        if (ecOffer.getEcbbId() != 0) {
            EcbBag ecbBag = ecbBagModel.getObjectPassEcbbId(ecOffer.getEcbbId());
            BigDecimal bagThickness = ecOffer.getBagThickness();
            BigDecimal unitPrice = ecbBag.getUnitPrice();
            BigDecimal density = ecbBag.getDensity();
            return bagDataCompute(bagThickness, density, unitPrice, externalDiameter);
        }
        return new ExternalComputeBo();
    }

    //getSteelbandData
    public ExternalComputeBo getSteelBandData(EcOffer ecOffer, BigDecimal externalDiameter) {
        if (ecOffer.getEcbsbId() != 0) {
            EcbSteelBand ecbuSteelband = ecbSteelbandModel.getObjectPassEcbsbId(ecOffer.getEcbsbId());
            BigDecimal density = ecbuSteelband.getDensity();
            BigDecimal unitPrice = ecbuSteelband.getUnitPrice();

            BigDecimal bagThickness = ecOffer.getBagThickness();
            BigDecimal shieldThickness = ecOffer.getShieldThickness();
            BigDecimal steelbandThickness = ecOffer.getSteelbandThickness();
            Integer steelbandStorey = ecOffer.getSteelbandStorey();

            return steelBandDataCompute(unitPrice,
                    density,
                    bagThickness,
                    shieldThickness,
                    steelbandThickness,
                    steelbandStorey,
                    externalDiameter);
        }
        return new ExternalComputeBo();
    }

    //getSheathData 获取护套数据
    public ExternalComputeBo getSheathData(EcOffer ecOffer, BigDecimal externalDiameter) {

        BigDecimal sheathThickness = ecOffer.getSheathThickness();
        if (ecOffer.getEcbSheathId() != 0 && sheathThickness.compareTo(new BigDecimal("0")) != 0) {
            EcbSheath ecbuSheath = ecbSheathModel.getObjectPassEcbsId(ecOffer.getEcbSheathId());
            BigDecimal density = ecbuSheath.getDensity();
            BigDecimal unitPrice = ecbuSheath.getUnitPrice();

            BigDecimal bagThickness = ecOffer.getBagThickness();
            BigDecimal shieldThickness = ecOffer.getShieldThickness();
            BigDecimal steelbandThickness = ecOffer.getSteelbandThickness();
            Integer steelbandStorey = ecOffer.getSteelbandStorey();

            return sheathDataCompute(density,
                    unitPrice,
                    bagThickness,
                    shieldThickness,
                    steelbandThickness,
                    steelbandStorey,
                    sheathThickness,
                    externalDiameter);
        }
        return new ExternalComputeBo();

    }
}
