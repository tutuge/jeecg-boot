package org.jeecg.modules.cable.tools;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.userEcable.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.jeecg.modules.cable.tools.ComputeFunction.*;

@Service
@Slf4j
public class EcableEcuOfferFunction {
    @Resource
    EcbuConductorModel ecbuConductorModel;// 导体
    @Resource
    EcbuMicaTapeModel ecbuMicatapeModel;// 云母带
    @Resource
    EcbuInsulationModel ecbuInsulationModel;// 绝缘
    @Resource
    EcbuInfillingModel ecbuInfillingModel;// 填充物
    @Resource
    EcbuBagModel ecbuBagModel;// 包带
    @Resource
    EcbuSteelBandModel ecbuSteelbandModel;// 钢带
    @Resource
    EcbuSheathModel ecbuSheathModel;// 护套

    // getConductorData 获取导体数据
    public ConductorComputeExtendBo getConductorData(EcuOffer ecuOffer) {
        EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcbucId(ecuOffer.getEcbucId());
        BigDecimal conductorDensity = ecbuConductor.getDensity();
        BigDecimal conductorUnitPrice = ecbuConductor.getUnitPrice();
        BigDecimal fireSilkNumber = ecuOffer.getFireSilkNumber();// 粗芯丝号
        BigDecimal zeroSilkNumber = ecuOffer.getZeroSilkNumber();// 细芯丝号
        Integer fireMembrance = ecuOffer.getFireMembrance(); //粗芯过膜
        Integer fireRootNumber = ecuOffer.getFireRootNumber(); //粗芯根数
        BigDecimal fireStrand = ecuOffer.getFireStrand(); //粗芯绞合系数
        Integer zeroMembrance = ecuOffer.getZeroMembrance(); //细芯过膜
        Integer zeroRootNumber = ecuOffer.getZeroRootNumber(); //细芯根数
        BigDecimal zeroStrand = ecuOffer.getZeroStrand();
        String areaStr = ecuOffer.getAreaStr();

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

    // getMicatapeData
    public MicaTapeComputeBo getMicaTapeData(EcuOffer ecuOffer, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        if (ecuOffer.getEcbumId() != 0) {
            String areaStr = ecuOffer.getAreaStr();
            EcbuMicaTape ecbuMicatape = ecbuMicatapeModel.getObjectPassEcbumId(ecuOffer.getEcbumId());
            BigDecimal density = ecbuMicatape.getDensity();
            BigDecimal unitPrice = ecbuMicatape.getUnitPrice();
            BigDecimal micaTapeThickness = ecuOffer.getMicatapeThickness();// 云母带厚度
            return micaTapeDataCompute(areaStr,
                    density,
                    unitPrice,
                    micaTapeThickness,
                    fireDiameter,
                    zeroDiameter);
        }
        return new MicaTapeComputeBo();
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

        if (ecuOffer.getEcbuinId() != 0) {
            EcbuInfilling ecbuInfilling = ecbuInfillingModel.getObjectAndEcbuinId(ecuOffer.getEcbuinId());
            BigDecimal density = ecbuInfilling.getDensity();
            BigDecimal unitPrice = ecbuInfilling.getUnitPrice();
            String areaStr = ecuOffer.getAreaStr();
            BigDecimal micaTapeThickness = ecuOffer.getMicatapeThickness();
            BigDecimal insulationFireThickness = ecuOffer.getInsulationFireThickness();
            BigDecimal insulationZeroThickness = ecuOffer.getInsulationZeroThickness();

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

    // getBagData 获取包带数据
    public BagComputeBo getBagData(EcuOffer ecuOffer, BigDecimal externalDiameter) {
        if (ecuOffer.getEcbubId() != 0) {
            EcbuBag ecbuBag = ecbuBagModel.getObjectPassEcbubId(ecuOffer.getEcbubId());
            BigDecimal bagThickness = ecuOffer.getBagThickness();
            BigDecimal density = ecbuBag.getDensity();
            BigDecimal unitPrice = ecbuBag.getUnitPrice();
            return bagDataCompute(bagThickness, density, unitPrice, externalDiameter);
        }
        return new BagComputeBo();
    }

    // getSteelbandData
    public SteelBandComputeBo getSteelBandData(EcuOffer ecuOffer, BigDecimal externalDiameter) {

        if (ecuOffer.getEcbusbId() != 0) {
            EcbuSteelband ecbuSteelband = ecbuSteelbandModel.getObjectPassEcbusbId(ecuOffer.getEcbusbId());
            BigDecimal unitPrice = ecbuSteelband.getUnitPrice();
            BigDecimal density = ecbuSteelband.getDensity();

            BigDecimal bagThickness = ecuOffer.getBagThickness();
            BigDecimal shieldThickness = ecuOffer.getShieldThickness();
            BigDecimal steelbandThickness = ecuOffer.getSteelbandThickness();
            Integer steelbandStorey = ecuOffer.getSteelbandStorey();
            return steelBandDataCompute(unitPrice,
                    density,
                    bagThickness,
                    shieldThickness,
                    steelbandThickness,
                    steelbandStorey,
                    externalDiameter);

        }
        return new SteelBandComputeBo();
    }

    // getSheathData 获取护套数据
    public SheathComputeBo getSheathData(EcuOffer ecuOffer, BigDecimal externalDiameter) {
        BigDecimal sheathThickness = ecuOffer.getSheathThickness();
        if (ecuOffer.getEcbuSheathId() != 0 && sheathThickness.compareTo(BigDecimal.ZERO) != 0) {
            EcbuSheath ecbuSheath = ecbuSheathModel.getObjectPassEcbusid(ecuOffer.getEcbuSheathId());
            BigDecimal density = ecbuSheath.getDensity();
            BigDecimal unitPrice = ecbuSheath.getUnitPrice();

            BigDecimal bagThickness = ecuOffer.getBagThickness();
            BigDecimal shieldThickness = ecuOffer.getShieldThickness();
            BigDecimal steelbandThickness = ecuOffer.getSteelbandThickness();
            Integer steelbandStorey = ecuOffer.getSteelbandStorey();

            return sheathDataCompute(density,
                    unitPrice,
                    bagThickness,
                    shieldThickness,
                    steelbandThickness,
                    steelbandStorey,
                    sheathThickness,
                    externalDiameter);
        }
        return new SheathComputeBo();
    }
}
