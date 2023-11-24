package org.jeecg.modules.cable.model.load;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.load.bo.CompanyRegisterBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPcompanyVo;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.jeecg.modules.cable.model.quality.EcquLevelModel;
import org.jeecg.modules.cable.model.systemCommon.EcbPcompanyModel;
import org.jeecg.modules.cable.model.systemCommon.EcblUnitModel;
import org.jeecg.modules.cable.model.systemCommon.EcdCompanyModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbDeliveryModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdModelModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdMoneyModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdPriceModel;
import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.model.systemOffer.EcOfferModel;
import org.jeecg.modules.cable.model.userCommon.EcbuPcompanyModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.model.userCommon.EcbulUnitModel;
import org.jeecg.modules.cable.model.userCommon.EcduCompanyModel;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudModelModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudMoneyModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudPriceModel;
import org.jeecg.modules.cable.model.userEcable.*;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class LoadRegister {
    @Resource
    EcbuConductorModel ecbuConductorModel;

    @Resource
    EcbuInsulationModel ecbuInsulationModel;
    @Resource
    EcbuShieldModel ecbuShieldModel;
    @Resource
    EcbuMicaTapeModel ecbuMicaTapeModel;
    @Resource
    EcbuInfillingModel ecbuInfillingModel;
    @Resource
    EcbuBagModel ecbuBagModel;
    @Resource
    EcbuSteelbandModel ecbuSteelbandModel;
    @Resource
    EcbuSheathModel ecbuSheathModel;
    @Resource
    EcSilkModel ecSilkModel;
    @Resource
    EcOfferModel ecOfferModel;
    @Resource
    EcquLevelModel ecquLevelModel;
    @Resource
    EcuOfferModel ecuOfferModel;
    @Resource
    EcblUnitModel ecblUnitModel;// 长度单位
    @Resource
    EcbulUnitModel ecbulUnitModel;
    @Resource
    EcdCompanyModel ecdCompanyModel;// 公司数据
    @Resource
    EcduCompanyModel ecduCompanyModel;
    @Resource
    EcbPcompanyModel ecbPcompanyModel;// 平台公司
    @Resource
    EcbuPcompanyModel ecbuPcompanyModel;
    @Resource
    EcbDeliveryModel ecbDeliveryModel;// 快递
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel;
    @Resource
    EcbuStoreModel ecbuStoreModel;
    @Resource
    EcbdModelModel ecbdModelModel;// 物流模型
    @Resource
    EcbudModelModel ecbudModelModel;// 物流模型
    @Resource
    EcbdMoneyModel ecbdMoneyModel;// 物流
    @Resource
    EcbudMoneyModel ecbudMoneyModel;
    @Resource
    EcbudPriceModel ecbudPriceModel;
    @Resource
    EcbdPriceModel ecbdPriceModel;
    @Resource
    EcduPccModel ecduPccModel;

    /**
     * 初始化公司基础数据
     *
     * @param registerBo
     */
    @Transactional(rollbackFor = Exception.class)
    public void load(CompanyRegisterBo registerBo) {
        //导体->云母带->绝缘->填充物->包袋->屏蔽->钢带->外护套
        Integer ecCompanyId = registerBo.getEcCompanyId();
        // 加载导体
        List<EcbConductor> listConductor = ecbuConductorModel.getListStart();
        for (EcbConductor ecbConductor : listConductor) {
            EcbuConductor recordConductor = new EcbuConductor();
            recordConductor.setEcbcId(ecbConductor.getEcbcId());
            recordConductor.setEcCompanyId(ecCompanyId);
            recordConductor.setStartType(true);
            recordConductor.setName("");
            recordConductor.setUnitPrice(ecbConductor.getUnitPrice());
            recordConductor.setDensity(ecbConductor.getDensity());
            recordConductor.setResistivity(ecbConductor.getResistivity());
            recordConductor.setDescription("");
            ecbuConductorModel.deal(recordConductor);
        }
        ecbuConductorModel.loadData();// 加截txt
        // 加载云母带
        List<EcbMicaTape> listMicaTape = ecbuMicaTapeModel.getListStart();
        for (EcbMicaTape ecbMicatape : listMicaTape) {
            EcbuMicaTape recordMicatape = new EcbuMicaTape();
            recordMicatape.setEcbmId(ecbMicatape.getEcbmId());
            recordMicatape.setEcCompanyId(ecCompanyId);
            recordMicatape.setStartType(true);
            recordMicatape.setName("");
            recordMicatape.setUnitPrice(ecbMicatape.getUnitPrice());
            recordMicatape.setDensity(ecbMicatape.getDensity());
            recordMicatape.setDescription("");
            ecbuMicaTapeModel.deal(recordMicatape);
        }
        ecbuMicaTapeModel.loadData();// 加截txt
        // 加载绝缘
        List<EcbInsulation> listInsulation = ecbuInsulationModel.getListStart();
        for (EcbInsulation ecbInsulation : listInsulation) {
            EcbuInsulation recordInsulation = new EcbuInsulation();
            recordInsulation.setEcbiId(ecbInsulation.getEcbiId());
            recordInsulation.setEcCompanyId(ecCompanyId);
            recordInsulation.setStartType(true);
            recordInsulation.setName("");
            recordInsulation.setUnitPrice(ecbInsulation.getUnitPrice());
            recordInsulation.setDensity(ecbInsulation.getDensity());
            recordInsulation.setDescription("");
            ecbuInsulationModel.deal(recordInsulation);
        }
        ecbuInsulationModel.loadData();// 加截txt
        // 加载填充物
        List<EcbInfilling> listInfilling = ecbuInfillingModel.getListStart();
        // log.info("listInfilling + " + CommonFunction.getGson().toJson(listInfilling));
        for (EcbInfilling ecbInfilling : listInfilling) {
            EcbuInfilling recordInfilling = new EcbuInfilling();
            recordInfilling.setEcbinId(ecbInfilling.getEcbinId());
            recordInfilling.setEcCompanyId(ecCompanyId);
            recordInfilling.setStartType(true);
            recordInfilling.setName("");
            recordInfilling.setUnitPrice(ecbInfilling.getUnitPrice());
            recordInfilling.setDensity(ecbInfilling.getDensity());
            recordInfilling.setDescription("");
            ecbuInfillingModel.deal(recordInfilling);
        }
        ecbuInfillingModel.loadData();// txt文档
        // 加载包带
        List<EcbBag> listBag = ecbuBagModel.getListStart();
        for (EcbBag ecbBag : listBag) {
            EcbuBag recordBag = new EcbuBag();
            recordBag.setEcbbId(ecbBag.getEcbbId());
            recordBag.setEcCompanyId(ecCompanyId);
            recordBag.setStartType(true);
            recordBag.setName("");
            recordBag.setUnitPrice(ecbBag.getUnitPrice());
            recordBag.setDensity(ecbBag.getDensity());
            recordBag.setDescription("");
            ecbuBagModel.deal(recordBag);
        }
        ecbuBagModel.loadData();// txt文档
        // 加载屏蔽
        List<EcbShield> listShield = ecbuShieldModel.getListStart();
        for (EcbShield ecbShield : listShield) {
            EcbuShield recordShield = new EcbuShield();
            recordShield.setEcbsId(ecbShield.getEcbsId());
            recordShield.setEcCompanyId(ecCompanyId);
            recordShield.setStartType(true);
            recordShield.setName("");
            recordShield.setUnitPrice(ecbShield.getUnitPrice());
            recordShield.setDensity(ecbShield.getDensity());
            recordShield.setDescription("");
            ecbuShieldModel.deal(recordShield);
        }
        ecbuShieldModel.loadData();// txt文档
        // 加载钢带
        List<EcbSteelBand> listSteelband = ecbuSteelbandModel.getListStart();
        for (EcbSteelBand ecbSteelband : listSteelband) {
            EcbuSteelband recordSteelband = new EcbuSteelband();
            recordSteelband.setEcbsbId(ecbSteelband.getEcbsbId());
            recordSteelband.setEcCompanyId(ecCompanyId);
            recordSteelband.setStartType(true);
            recordSteelband.setName("");
            recordSteelband.setUnitPrice(ecbSteelband.getUnitPrice());
            recordSteelband.setDensity(ecbSteelband.getDensity());
            recordSteelband.setDescription("");
            ecbuSteelbandModel.deal(recordSteelband);
        }
        ecbuSteelbandModel.loadData();// txt文档
        // 加载护套
        List<EcbSheath> listSheath = ecbuSheathModel.getListStart();
        for (EcbSheath ecbSheath : listSheath) {
            EcbuSheath recordSheath = new EcbuSheath();
            recordSheath.setEcbsId(ecbSheath.getEcbsId());
            recordSheath.setEcCompanyId(ecCompanyId);
            recordSheath.setStartType(true);
            recordSheath.setName("");
            recordSheath.setUnitPrice(ecbSheath.getUnitPrice());
            recordSheath.setDensity(ecbSheath.getDensity());
            recordSheath.setDescription("");
            ecbuSheathModel.deal(recordSheath);
        }
        // 加载长度单位
        List<EcblUnit> listEcblUnit = ecblUnitModel.getListStart();
        for (EcblUnit ecblUnit : listEcblUnit) {
            EcbulUnit recordEcbulUnit = new EcbulUnit();
            recordEcbulUnit.setEcCompanyId(ecCompanyId);
            recordEcbulUnit.setStartType(true);
            recordEcbulUnit.setSortId(ecblUnit.getSortId());
            recordEcbulUnit.setLengthName(ecblUnit.getLengthName());
            recordEcbulUnit.setMeterNumber(ecblUnit.getMeterNumber());
            recordEcbulUnit.setDescription(ecblUnit.getDescription());
            ecbulUnitModel.deal(recordEcbulUnit);
        }
        ecbulUnitModel.loadData(ecCompanyId);
        // 加载公司数据
        List<EcdCompany> listEcdCompany = ecdCompanyModel.getListStart();
        for (EcdCompany ecdCompany : listEcdCompany) {
            EcduCompany recordEcduCompany = new EcduCompany();
            recordEcduCompany.setEcCompanyId(ecCompanyId);
            recordEcduCompany.setStartType(true);
            recordEcduCompany.setSortId(ecdCompany.getSortId());
            recordEcduCompany.setDefaultType(false);
            recordEcduCompany.setAbbreviation(ecdCompany.getAbbreviation());
            recordEcduCompany.setFullName(ecdCompany.getFullName());
            recordEcduCompany.setLogoImg(ecdCompany.getLogoImg());
            recordEcduCompany.setSealImg(ecdCompany.getSealImg());
            recordEcduCompany.setBillPercentType(ecdCompany.getBillPercentType());
            recordEcduCompany.setDescription(ecdCompany.getDescription());
            ecduCompanyModel.deal(recordEcduCompany);
        }
        // 平台公司(天猫\淘宝等)数据
        List<EcbPcompanyVo> listEcbPcompany = ecbPcompanyModel.getListStart();
        for (EcbPcompany ecbPcompany : listEcbPcompany) {
            EcbuPcompany recordEcbuPcompany = new EcbuPcompany();
            recordEcbuPcompany.setEcCompanyId(ecCompanyId);
            recordEcbuPcompany.setStartType(true);
            recordEcbuPcompany.setSortId(ecbPcompany.getSortId());
            recordEcbuPcompany.setPlatformId(ecbPcompany.getPlatformId());
            recordEcbuPcompany.setPcName(ecbPcompany.getPcName());
            recordEcbuPcompany.setPcPercent(ecbPcompany.getPcPercent());
            recordEcbuPcompany.setDescription(ecbPcompany.getDescription());
            // log.info("recordEcbuPcompany + " + CommonFunction.getGson().toJson(recordEcbuPcompany));
            ecbuPcompanyModel.saveOrUpdate(recordEcbuPcompany);
        }
        ecbuPcompanyModel.loadData();
        // 加载默认仓库
        EcbuStore recordEcbuStore = new EcbuStore();
        recordEcbuStore.setEcCompanyId(ecCompanyId);
        recordEcbuStore.setStoreName("默认仓库");
        recordEcbuStore.setSortId(1);
        recordEcbuStore.setStartType(true);
        recordEcbuStore.setDefaultType(true);
        recordEcbuStore.setPercentCopper(new BigDecimal("0.02"));
        recordEcbuStore.setPercentAluminium(new BigDecimal("0.05"));
        recordEcbuStore.setDunitMoney(new BigDecimal("0.3"));
        recordEcbuStore.setDescription("无");
        ecbuStoreModel.dealDefault(recordEcbuStore);
        recordEcbuStore = new EcbuStore();
        recordEcbuStore.setEcCompanyId(ecCompanyId);
        recordEcbuStore.setDefaultType(true);
        EcbuStore ecbuStore = ecbuStoreModel.getObjectDefaultPassEcCompanyId(recordEcbuStore);
        // 加载快递
        // 主表
        List<EcbDelivery> listEcbDelivery = ecbDeliveryModel.getListStart();
        for (EcbDelivery ecbDelivery : listEcbDelivery) {
            EcbuDelivery recordEcbuDelivery = new EcbuDelivery();
            recordEcbuDelivery.setEcCompanyId(ecCompanyId);
            recordEcbuDelivery.setEcbusId(ecbuStore.getEcbusId());
            recordEcbuDelivery.setDeliveryName(ecbDelivery.getDeliveryName());
            recordEcbuDelivery.setSortId(ecbDelivery.getSortId());
            recordEcbuDelivery.setStartType(ecbDelivery.getStartType());
            recordEcbuDelivery.setDeliveryType(ecbDelivery.getDeliveryType());
            recordEcbuDelivery.setDescription(ecbDelivery.getDescription());
            ecbuDeliveryModel.deal(recordEcbuDelivery);
        }
        List<EcbuDelivery> listEcbuDelivery = ecbuDeliveryModel.getListStart(ecCompanyId);
        for (EcbuDelivery ecbuDelivery : listEcbuDelivery) {
            Integer ecbudId = ecbuDelivery.getEcbudId();
            String deliveryName = ecbuDelivery.getDeliveryName();
            EcbDelivery ecbDelivery = ecbDeliveryModel.getObjectPassDeliveryName(deliveryName);
            // 物流
            if (ecbDelivery.getDeliveryType() == 1) {
                // 快递
                List<EcbdMoney> listEcbdMoney = ecbdMoneyModel.getListPassEcbdId(ecbDelivery.getEcbdId());
                EcbudMoney recordEcbudMoney = new EcbudMoney();
                for (EcbdMoney ecbdMoney : listEcbdMoney) {
                    recordEcbudMoney.setEcbudId(ecbudId);
                    recordEcbudMoney.setSortId(ecbdMoney.getSortId());
                    recordEcbudMoney.setStartType(ecbdMoney.getStartType());
                    recordEcbudMoney.setEcpId(ecbdMoney.getEcpId());
                    recordEcbudMoney.setProvinceName(ecbdMoney.getProvinceName());
                    recordEcbudMoney.setFirstMoney(ecbdMoney.getFirstMoney());
                    recordEcbudMoney.setFirstWeight(ecbdMoney.getFirstWeight());
                    recordEcbudMoney.setContinueMoney(ecbdMoney.getContinueMoney());
                    ecbudMoneyModel.deal(recordEcbudMoney);
                }
            } else if (ecbDelivery.getDeliveryType() == 2) {
                // 1.物流模型
                EcbdModel ecbdModel = ecbdModelModel.getObjectPassEcbdId(ecbDelivery.getEcbdId());
                EcbudModel recordEcbudModel = new EcbudModel();
                recordEcbudModel.setEcbudId(ecbudId);
                recordEcbudModel.setStartWeight1(ecbdModel.getStartWeight1());
                recordEcbudModel.setEndWeight1(ecbdModel.getEndWeight1());
                recordEcbudModel.setStartWeight2(ecbdModel.getStartWeight2());
                recordEcbudModel.setEndWeight2(ecbdModel.getEndWeight2());
                recordEcbudModel.setStartWeight3(ecbdModel.getStartWeight3());
                recordEcbudModel.setEndWeight3(ecbdModel.getEndWeight3());
                recordEcbudModel.setStartWeight4(ecbdModel.getStartWeight4());
                recordEcbudModel.setEndWeight4(ecbdModel.getEndWeight4());
                recordEcbudModel.setStartWeight5(ecbdModel.getStartWeight5());
                recordEcbudModel.setEndWeight5(ecbdModel.getEndWeight5());
                ecbudModelModel.deal(recordEcbudModel);
                // 2.物流
                List<EcbdPrice> listEcbdPrice = ecbdPriceModel.getListPassEcbdId(ecbDelivery.getEcbdId());
                EcbudPrice recordEcbudPrice = new EcbudPrice();
                for (EcbdPrice ecbdPrice : listEcbdPrice) {
                    recordEcbudPrice.setEcbudId(ecbudId);
                    recordEcbudPrice.setSortId(ecbdPrice.getSortId());
                    recordEcbudPrice.setStartType(ecbdPrice.getStartType());
                    recordEcbudPrice.setEcpId(ecbdPrice.getEcpId());
                    recordEcbudPrice.setProvinceName(ecbdPrice.getProvinceName());
                    recordEcbudPrice.setFirstPrice(ecbdPrice.getFirstPrice());
                    recordEcbudPrice.setPrice1(ecbdPrice.getPrice1());
                    recordEcbudPrice.setPrice2(ecbdPrice.getPrice2());
                    recordEcbudPrice.setPrice3(ecbdPrice.getPrice3());
                    recordEcbudPrice.setPrice4(ecbdPrice.getPrice4());
                    recordEcbudPrice.setPrice5(ecbdPrice.getPrice5());
                    ecbudPriceModel.deal(recordEcbudPrice);
                }
            }
        }
        ecduPccModel.load(1, ecCompanyId);// 加载txt
        // 加载国标库
        List<EcSilk> listSilk = ecSilkModel.getListStart();
        EcquLevel recordEcquLevel = new EcquLevel();
        EcquLevel ecquLevel;
        EcuOffer recordEcuOffer = new EcuOffer();
        for (EcSilk ecSilk : listSilk) {
            List<EcOffer> listEcOffer = ecOfferModel.getList(ecSilk.getEcsId());
            log.info("listEcOffer + " + CommonFunction.getGson().toJson(listEcOffer));
            if (!listEcOffer.isEmpty()) {
                // 获取导体
                EcbuConductor ecbuConductor;
                Integer ecbcId = 0;
                if (ecSilk.getEcsId() == 2) {// yjv
                    ecbcId = 3;
                } else if (ecSilk.getEcsId() == 10) {// YJLV
                    ecbcId = 2;
                } else if (ecSilk.getEcsId() == 6) {// BV
                    ecbcId = 3;
                }
                ecbuConductor = ecbuConductorModel.getObjectPassEcbcIdAndEcCompanyId(ecbcId, ecCompanyId);
                // 先创建相应的质量等级
                recordEcquLevel.setEcsId(ecSilk.getEcsId());
                recordEcquLevel.setEcbucId(ecbuConductor.getEcbucId());
                recordEcquLevel.setEcCompanyId(ecCompanyId);
                recordEcquLevel.setStartType(true);
                recordEcquLevel.setPowerId(1);
                recordEcquLevel.setName(ecSilk.getAbbreviation() + "国标");
                recordEcquLevel.setDescription("");
                ecquLevelModel.deal(recordEcquLevel);
                ecquLevel = ecquLevelModel.getObjectPassEcCompanyIdAndName(ecCompanyId, ecSilk.getAbbreviation() + "国标");
                // 创建国标库
                for (EcOffer ecOffer : listEcOffer) {
                    recordEcuOffer.setEcCompanyId(ecCompanyId);
                    recordEcuOffer.setEcqulId(ecquLevel.getEcqulId());
                    recordEcuOffer.setEcbucId(ecbuConductor.getEcbucId());
                    recordEcuOffer.setStartType(true);
                    recordEcuOffer.setAreaStr(ecOffer.getAreaStr());
                    recordEcuOffer.setAddPercent(new BigDecimal("0"));
                    recordEcuOffer.setFireSilkNumber(ecOffer.getFireSilkNumber());
                    recordEcuOffer.setFireRootNumber(ecOffer.getFireRootNumber());
                    recordEcuOffer.setFireMembrance(ecOffer.getFireMembrance());
                    recordEcuOffer.setFirePress(ecOffer.getFirePress());
                    recordEcuOffer.setFireStrand(ecOffer.getFireStrand());
                    recordEcuOffer.setZeroSilkNumber(ecOffer.getZeroSilkNumber());
                    recordEcuOffer.setZeroRootNumber(ecOffer.getZeroRootNumber());
                    recordEcuOffer.setZeroMembrance(ecOffer.getZeroMembrance());
                    recordEcuOffer.setZeroPress(ecOffer.getZeroPress());
                    recordEcuOffer.setZeroStrand(ecOffer.getZeroStrand());
                    // 绝缘
                    EcbuInsulation ecbuInsulation = ecbuInsulationModel
                            .getObjectPassEcCompanyIdAndEcbiId(ecCompanyId, ecOffer.getEcbiId());
                    Integer ecbuiId = 0;
                    if (ecbuInsulation != null) {
                        ecbuiId = ecbuInsulation.getEcbuiId();
                    }
                    recordEcuOffer.setEcbuiId(ecbuiId);
                    recordEcuOffer.setInsulationFireThickness(ecOffer.getInsulationFireThickness());
                    recordEcuOffer.setInsulationZeroThickness(ecOffer.getInsulationZeroThickness());
                    // 包带
                    EcbuBag ecbuBag = ecbuBagModel
                            .getObjectPassEcCompanyIdAndEcbbId(ecCompanyId, ecOffer.getEcbbId());
                    Integer ecbubId = 0;
                    if (ecbuBag != null) {
                        ecbubId = ecbuBag.getEcbubId();
                    }
                    recordEcuOffer.setEcbubId(ecbubId);
                    recordEcuOffer.setBagThickness(ecOffer.getBagThickness());
                    // 铠装包带
                    ecbuBag = ecbuBagModel
                            .getObjectPassEcCompanyIdAndEcbbId(ecCompanyId, ecOffer.getEcbb22Id());
                    if (ecbuBag != null) {
                        ecbubId = ecbuBag.getEcbubId();
                    }
                    recordEcuOffer.setEcbub22Id(ecbubId);
                    recordEcuOffer.setBag22Thickness(ecOffer.getBagThickness());
                    // 屏蔽
                    EcbuShield ecbuShield = ecbuShieldModel
                            .getObjectPassEcCompanyIdAndEcbsId(ecCompanyId, ecOffer.getEcbShieldId());
                    Integer ecbusId = 0;
                    if (ecbuShield != null) {
                        ecbusId = ecbuShield.getEcbusId();
                    }
                    recordEcuOffer.setEcbuShieldId(ecbusId);
                    recordEcuOffer.setShieldThickness(ecOffer.getShieldThickness());
                    recordEcuOffer.setShieldPercent(ecOffer.getShieldPercent());
                    // 钢带
                    EcbuSteelband ecbuSteelband = ecbuSteelbandModel
                            .getObjectPassEcCompanyIdAndEcbsbId(ecCompanyId, ecOffer.getEcbsbId());
                    Integer ecbusbId = 0;
                    if (ecbuSteelband != null) {
                        ecbusbId = ecbuSteelband.getEcbusId();
                    }
                    recordEcuOffer.setEcbusbId(ecbusbId);
                    recordEcuOffer.setSteelbandThickness(ecOffer.getSteelbandThickness());
                    recordEcuOffer.setSteelbandStorey(ecOffer.getSteelbandStorey());
                    // 护套
                    EcbuSheath ecbuSheath = ecbuSheathModel.getObjectPassEcCompanyIdAndEcbsId(ecCompanyId, ecOffer.getEcbuSheathId());
                    Integer ecbusid = 0;
                    if (ecbuSheath != null) {
                        ecbusid = ecbuSheath.getEcbusId();
                    }
                    recordEcuOffer.setEcbuSheathId(ecbusid);
                    recordEcuOffer.setSheathThickness(ecOffer.getSheathThickness());
                    recordEcuOffer.setSheath22Thickness(ecOffer.getSheath22Thickness());
                    // 云母带
                    EcbuMicaTape ecbuMicatape = ecbuMicaTapeModel
                            .getObjectPassEcCompanyIdAndEcbmId(ecCompanyId, ecOffer.getEcbmId());
                    Integer ecbumId = 0;
                    if (ecbuMicatape != null) {
                        ecbumId = ecbuMicatape.getEcbumId();
                    }
                    recordEcuOffer.setEcbumId(ecbumId);
                    recordEcuOffer.setMicatapeThickness(ecOffer.getMicatapeThickness());
                    // 填充物
                    EcbuInfilling ecbuInfilling = ecbuInfillingModel
                            .getObjectPassEcCompanyIdAndEcbinId(ecCompanyId, ecOffer.getEcbinId());
                    Integer ecbuinId = 0;
                    if (ecbuInfilling != null) {
                        ecbuinId = ecbuInfilling.getEcbuiId();
                    }
                    recordEcuOffer.setEcbuinId(ecbuinId);
                    // 钢丝
                    recordEcuOffer.setEcbuswId(0);
                    recordEcuOffer.setSteelwireMembrance(ecOffer.getSteelwireMembrance());
                    recordEcuOffer.setSteelwirePress(ecOffer.getSteelwirePress());
                    // 成缆系数
                    recordEcuOffer.setCableStrand(ecOffer.getCableStrand());
                    recordEcuOffer.setDefaultWeight(ecOffer.getDefaultWeight());
                    recordEcuOffer.setDefaultMoney(ecOffer.getDefaultMoney());
                    ecuOfferModel.saveOrUpdate(recordEcuOffer);
                }
            }
        }
    }

    /**
     * 清空某公司下的数据
     *
     * @param ecCompanyId
     */
    @Transactional(rollbackFor = Exception.class)
    public void clean(Integer ecCompanyId) {
        ecbuConductorModel.deletePassEcCompanyId(ecCompanyId);// 清除导体
        ecbuInsulationModel.deletePassEcCompanyId(ecCompanyId);// 清除绝缘
        ecbuShieldModel.deletePassEcCompanyId(ecCompanyId);// 清除屏蔽
        ecbuMicaTapeModel.deletePassEcCompanyId(ecCompanyId);// 清除云母带
        ecbuInfillingModel.deletePassEcCompanyId(ecCompanyId);// 清除填充物
        ecbuBagModel.deletePassEcCompanyId(ecCompanyId);// 清除包带
        ecbuSteelbandModel.deletePassEcCompanyId(ecCompanyId);// 清除钢带
        ecbuSheathModel.deletePassEcCompanyId(ecCompanyId);// 清除护套
        ecbulUnitModel.deletePassEcCompanyId(ecCompanyId);// 清除长度单位
        ecduCompanyModel.deletePassEcCompanyId(ecCompanyId);// 清除公司数据
        ecbuPcompanyModel.deletePassEcCompanyId(ecCompanyId);// 清除平台公司
        ecbuStoreModel.deletePassEcCompanyId(ecCompanyId);// 清除默认仓库
        // 清除快递数据
        List<EcbuDelivery> listEcbuDelivery = ecbuDeliveryModel.getListStart(ecCompanyId);
        for (EcbuDelivery ecbuDelivery : listEcbuDelivery) {
            Integer ecbudId = ecbuDelivery.getEcbudId();
            ecbudModelModel.deletePassEcbudId(ecbudId);// 清除物流模型
            ecbudPriceModel.deletePassEcbudId(ecbudId);// 清除物流
            ecbudMoneyModel.deletePassEcbudId(ecbudId);// 清除快递
        }
        ecuOfferModel.deletePassEcCompanyId(ecCompanyId); // 清除国标库
    }

    // loadZeyang
    public void loadZeyang() {
        Integer ecCompanyId = 6;
        // 加载国标库
        List<EcSilk> listSilk = ecSilkModel.getListStart();
        // log.info(CommonFunction.getGson().toJson(CommonFunction.getGson().toJson(listSilk)));
        EcquLevel recordEcquLevel = new EcquLevel();
        EcquLevel ecquLevel;
        EcuOffer recordEcuOffer = new EcuOffer();
        for (EcSilk ecSilk : listSilk) {
            List<EcOffer> listEcOffer = ecOfferModel.getList(ecSilk.getEcsId());
            log.info("listEcOffer + " + listEcOffer);
            if (!listEcOffer.isEmpty()) {
                // 获取导体
                EcbuConductor ecbuConductor;
                int ecbcId = 0;
                if (ecSilk.getEcsId() == 2) {// yjv
                    ecbcId = 3;
                } else if (ecSilk.getEcsId() == 10) {// YJLV
                    ecbcId = 2;
                } else if (ecSilk.getEcsId() == 6) {// BV
                    ecbcId = 3;
                }
                ecbuConductor = ecbuConductorModel.getObjectPassEcbcIdAndEcCompanyId(ecbcId, ecCompanyId);
                // 先创建相应的质量等级
                recordEcquLevel.setEcsId(ecSilk.getEcsId());
                recordEcquLevel.setEcbucId(ecbuConductor.getEcbucId());
                recordEcquLevel.setEcCompanyId(ecCompanyId);
                recordEcquLevel.setStartType(true);
                recordEcquLevel.setPowerId(1);
                recordEcquLevel.setName(ecSilk.getAbbreviation() + "国标");
                recordEcquLevel.setDescription("");
                ecquLevelModel.deal(recordEcquLevel);
                ecquLevel = ecquLevelModel.getObjectPassEcCompanyIdAndName(ecCompanyId, ecSilk.getAbbreviation() + "国标");
                // 创建国标库
                for (EcOffer ecOffer : listEcOffer) {
                    recordEcuOffer.setEcCompanyId(ecCompanyId);
                    recordEcuOffer.setEcqulId(ecquLevel.getEcqulId());
                    recordEcuOffer.setEcbucId(ecbuConductor.getEcbucId());
                    recordEcuOffer.setStartType(true);
                    recordEcuOffer.setAreaStr(ecOffer.getAreaStr());
                    recordEcuOffer.setAddPercent(new BigDecimal("0"));
                    recordEcuOffer.setFireSilkNumber(ecOffer.getFireSilkNumber());
                    recordEcuOffer.setFireRootNumber(ecOffer.getFireRootNumber());
                    recordEcuOffer.setFireMembrance(ecOffer.getFireMembrance());
                    recordEcuOffer.setFirePress(ecOffer.getFirePress());
                    recordEcuOffer.setFireStrand(ecOffer.getFireStrand());
                    recordEcuOffer.setZeroSilkNumber(ecOffer.getZeroSilkNumber());
                    recordEcuOffer.setZeroRootNumber(ecOffer.getZeroRootNumber());
                    recordEcuOffer.setZeroMembrance(ecOffer.getZeroMembrance());
                    recordEcuOffer.setZeroPress(ecOffer.getZeroPress());
                    recordEcuOffer.setZeroStrand(ecOffer.getZeroStrand());
                    // 绝缘
                    EcbuInsulation ecbuInsulation = ecbuInsulationModel
                            .getObjectPassEcCompanyIdAndEcbiId(ecCompanyId, ecOffer.getEcbiId());
                    Integer ecbuiId = 0;
                    if (ecbuInsulation != null) {
                        ecbuiId = ecbuInsulation.getEcbuiId();
                    }
                    recordEcuOffer.setEcbuiId(ecbuiId);
                    recordEcuOffer.setInsulationFireThickness(ecOffer.getInsulationFireThickness());
                    recordEcuOffer.setInsulationZeroThickness(ecOffer.getInsulationZeroThickness());
                    // 包带
                    EcbuBag ecbuBag = ecbuBagModel
                            .getObjectPassEcCompanyIdAndEcbbId(ecCompanyId, ecOffer.getEcbbId());
                    Integer ecbubId = 0;
                    if (ecbuBag != null) {
                        ecbubId = ecbuBag.getEcbubId();
                    }
                    recordEcuOffer.setEcbubId(ecbubId);
                    recordEcuOffer.setBagThickness(ecOffer.getBagThickness());
                    // 铠装包带
                    ecbuBag = ecbuBagModel.getObjectPassEcCompanyIdAndEcbbId(ecCompanyId, ecOffer.getEcbb22Id());
                    if (ecbuBag != null) {
                        ecbubId = ecbuBag.getEcbubId();
                    }
                    recordEcuOffer.setEcbub22Id(ecbubId);
                    recordEcuOffer.setBag22Thickness(ecOffer.getBagThickness());
                    // 屏蔽
                    EcbuShield ecbuShield = ecbuShieldModel.getObjectPassEcCompanyIdAndEcbsId(ecCompanyId, ecOffer.getEcbShieldId());
                    Integer ecbusId = 0;
                    if (ecbuShield != null) {
                        ecbusId = ecbuShield.getEcbusId();
                    }
                    recordEcuOffer.setEcbuShieldId(ecbusId);
                    recordEcuOffer.setShieldThickness(ecOffer.getShieldThickness());
                    recordEcuOffer.setShieldPercent(ecOffer.getShieldPercent());
                    // 钢带
                    EcbuSteelband ecbuSteelband = ecbuSteelbandModel.getObjectPassEcCompanyIdAndEcbsbId(ecCompanyId, ecOffer.getEcbsbId());
                    Integer ecbusbId = 0;
                    if (ecbuSteelband != null) {
                        ecbusbId = ecbuSteelband.getEcbusId();
                    }
                    recordEcuOffer.setEcbusbId(ecbusbId);
                    recordEcuOffer.setSteelbandThickness(ecOffer.getSteelbandThickness());
                    recordEcuOffer.setSteelbandStorey(ecOffer.getSteelbandStorey());
                    // 护套
                    EcbuSheath ecbuSheath = ecbuSheathModel
                            .getObjectPassEcCompanyIdAndEcbsId(ecCompanyId, ecOffer.getEcbuSheathId());
                    Integer ecbusid = 0;
                    if (ecbuSheath != null) {
                        ecbusid = ecbuSheath.getEcbusId();
                    }
                    recordEcuOffer.setEcbuSheathId(ecbusid);
                    recordEcuOffer.setSheathThickness(ecOffer.getSheathThickness());
                    recordEcuOffer.setSheath22Thickness(ecOffer.getSheath22Thickness());
                    // 云母带
                    EcbuMicaTape ecbuMicatape = ecbuMicaTapeModel.getObjectPassEcCompanyIdAndEcbmId(ecCompanyId, ecOffer.getEcbmId());
                    Integer ecbumId = 0;
                    if (ecbuMicatape != null) {
                        ecbumId = ecbuMicatape.getEcbumId();
                    }
                    recordEcuOffer.setEcbumId(ecbumId);
                    recordEcuOffer.setMicatapeThickness(ecOffer.getMicatapeThickness());
                    // 填充物
                    EcbuInfilling ecbuInfilling = ecbuInfillingModel.getObjectPassEcCompanyIdAndEcbinId(ecCompanyId, ecOffer.getEcbinId());
                    Integer ecbuinId = 0;
                    if (ecbuInfilling != null) {
                        ecbuinId = ecbuInfilling.getEcbuiId();
                    }
                    recordEcuOffer.setEcbuinId(ecbuinId);
                    // 钢丝
                    recordEcuOffer.setEcbuswId(0);
                    recordEcuOffer.setSteelwireMembrance(ecOffer.getSteelwireMembrance());
                    recordEcuOffer.setSteelwirePress(ecOffer.getSteelwirePress());
                    ecuOfferModel.saveOrUpdate(recordEcuOffer);
                }
                Integer ecqulId = ecquLevel.getEcqulId();
                ecquLevelModel.deal(ecCompanyId);// 加载load为集成数据
                ecuOfferModel.loadArea(ecCompanyId, ecqulId);// 加载质量等级对应的截面库ecuArea
            }
        }
    }
}
