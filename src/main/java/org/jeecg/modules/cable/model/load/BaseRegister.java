package org.jeecg.modules.cable.model.load;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.*;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdWeight;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.entity.userCommon.*;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialTypeMapper;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialsMapper;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMaterialTypeMapper;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMaterialsMapper;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.jeecg.modules.cable.model.systemCommon.EcbPlatformCompanyModel;
import org.jeecg.modules.cable.model.systemCommon.EcbStoreModel;
import org.jeecg.modules.cable.model.systemCommon.EcblUnitModel;
import org.jeecg.modules.cable.model.systemCommon.EcdCompanyModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbDeliveryModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdMoneyModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdPriceModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdWeightlModel;
import org.jeecg.modules.cable.model.systemEcable.EcSilkServiceModel;
import org.jeecg.modules.cable.model.systemOffer.EcOfferModel;
import org.jeecg.modules.cable.model.userCommon.EcbuPlatformCompanyModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.model.userCommon.EcbulUnitModel;
import org.jeecg.modules.cable.model.userCommon.EcduCompanyModel;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudMoneyModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudPriceModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudWeightModel;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import org.jeecg.modules.cable.model.userQuality.EcquLevelModel;
import org.jeecg.modules.cable.service.systemCommon.EcPlatformService;
import org.jeecg.modules.cable.service.systemCommon.EcbAxleService;
import org.jeecg.modules.cable.service.systemDelivery.EcSilkModelService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.jeecg.modules.cable.service.userCommon.EcbuAxleService;
import org.jeecg.modules.cable.service.userCommon.EcdTaxPointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxPointService;
import org.jeecg.modules.cable.service.userCommon.EcuPlatformService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class BaseRegister {

    @Resource
    EcSilkServiceModel ecSilkServiceModel;
    @Resource
    private EcSilkModelService ecSilkModelService;
    @Resource
    private EcuSilkService ecuSilkService;
    @Resource
    private EcuSilkModelService ecuSilkModelService;
    @Resource
    EcOfferModel ecOfferModel;
    @Resource
    EcquLevelModel ecquLevelModel; //用户质量等级
    @Resource
    EcuOfferModel ecuOfferModel;
    @Resource
    EcblUnitModel ecblUnitModel;// 系统长度单位
    @Resource
    EcbulUnitModel ecbulUnitModel; //用户长度单位
    @Resource
    EcdCompanyModel ecdCompanyModel;// 公司数据
    @Resource
    EcduCompanyModel ecduCompanyModel; //用户客户的公司
    @Resource
    EcbPlatformCompanyModel ecbPlatformcompanyModel;// 平台公司
    @Resource
    EcbuPlatformCompanyModel ecbuPlatformCompanyModel; //用户平台
    @Resource
    EcbDeliveryModel ecbDeliveryModel;// 系统物流
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel; //用户物流
    @Resource
    EcbStoreModel ecbStoreModel; //系统仓库
    @Resource
    private EcbAxleService ecbAxleService; //系统木轴
    @Resource
    private EcbuAxleService ecbuAxleService; //用户木轴
    @Resource
    private EcdTaxPointService ecdTaxPointService;//系统税点
    @Resource
    private EcduTaxPointService ecduTaxPointService;//用户税点

    @Resource
    EcbuStoreModel ecbuStoreModel; //用户仓库
    @Resource
    EcbdWeightlModel ecbdWeightlModel;// 物流模型
    @Resource
    EcbudWeightModel ecbudWeightModel;// 物流模型
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
    @Resource
    private EcqLevelService ecqLevelService;//系统质量等级
    @Resource
    private EcPlatformService ecPlatformService;
    @Resource
    private EcuPlatformService ecuPlatformService;
    @Resource
    private EcbMaterialsMapper ecbMaterialsMapper;
    @Resource
    private EcbuMaterialsMapper ecbuMaterialsMapper;
    @Resource
    private EcbMaterialTypeMapper ecbMaterialTypeMapper;
    @Resource
    private EcbuMaterialTypeMapper ecbuMaterialTypeMapper;
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW) // 开启一个新事务
    public void base(Integer ecCompanyId, AtomicBoolean ab) {
        //导体->云母带->绝缘->填充物->包袋->屏蔽->钢带->外护套

        // 加载长度单位
        CompletableFuture<Void> f9 = CompletableFuture.runAsync(() -> {
            try {
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
                log.info("保存长度单位完成！");
            } catch (Exception e) {
                log.error("保存长度单位异常！", e.getCause());
                ab.set(Boolean.TRUE);
            }
        }, executor);
        // 加载公司数据
        CompletableFuture<Void> f10 = CompletableFuture.runAsync(() -> {
            try {
                List<EcdCompany> listEcdCompany = ecdCompanyModel.getListStart();
                for (int i = 0; i < listEcdCompany.size(); i++) {
                    EcdCompany ecdCompany = listEcdCompany.get(i);
                    EcduCompany recordEcduCompany = new EcduCompany();
                    recordEcduCompany.setEcCompanyId(ecCompanyId);
                    recordEcduCompany.setStartType(true);
                    recordEcduCompany.setSortId(ecdCompany.getSortId());
                    recordEcduCompany.setDefaultType(i == 0);
                    recordEcduCompany.setAbbreviation(ecdCompany.getAbbreviation());
                    recordEcduCompany.setFullName(ecdCompany.getFullName());
                    recordEcduCompany.setLogoImg(ecdCompany.getLogoImg());
                    recordEcduCompany.setSealImg(ecdCompany.getSealImg());
                    recordEcduCompany.setBillPercentType(ecdCompany.getBillPercentType());
                    recordEcduCompany.setDescription(ecdCompany.getDescription());
                    ecduCompanyModel.deal(recordEcduCompany);
                }
                log.info("保存公司数据完成！");
            } catch (Exception e) {
                log.error("保存公司数据异常！", e.getCause());
                ab.set(Boolean.TRUE);
            }
        }, executor);
        // 平台公司(天猫\淘宝等)数据
        CompletableFuture<Void> f11 = CompletableFuture.runAsync(() -> {
            try {
                List<EcPlatform> platforms = ecPlatformService.getListStart();
                //系统的平台id与用户平台id的对照
                Map<Integer, Integer> platformMap = new HashMap<>();
                for (EcPlatform platform : platforms) {
                    EcuPlatform ecuPlatform = new EcuPlatform();
                    BeanUtils.copyProperties(platform, ecuPlatform);
                    ecuPlatform.setPlatformId(null);
                    ecuPlatform.setEcCompanyId(ecCompanyId);
                    ecuPlatformService.save(ecuPlatform);
                    platformMap.put(platform.getPlatformId(), ecuPlatform.getPlatformId());
                }
                List<EcbPlatformCompanyVo> listEcbPlatformCompany = ecbPlatformcompanyModel.getListStart();
                for (EcbPlatformCompany ecbPlatformCompany : listEcbPlatformCompany) {
                    EcbuPlatformCompany ecbuPlatformCompany = new EcbuPlatformCompany();
                    ecbuPlatformCompany.setEcCompanyId(ecCompanyId);
                    ecbuPlatformCompany.setStartType(true);
                    ecbuPlatformCompany.setSortId(ecbPlatformCompany.getSortId());
                    ecbuPlatformCompany.setPlatformId(platformMap.get(ecbPlatformCompany.getPlatformId()));
                    ecbuPlatformCompany.setPcName(ecbPlatformCompany.getPcName());
                    ecbuPlatformCompany.setPcPercent(ecbPlatformCompany.getPcPercent());
                    ecbuPlatformCompany.setDescription(ecbPlatformCompany.getDescription());
                    // log.info("recordEcbuPcompany + " + CommonFunction.getGson().toJson(recordEcbuPcompany));
                    ecbuPlatformCompanyModel.saveOrUpdate(ecbuPlatformCompany);
                }
                log.info("保存平台公司(天猫/淘宝等)数据完成！");
            } catch (Exception e) {
                log.error("保存平台公司(天猫/淘宝等)数据异常！", e.getCause());
                ab.set(Boolean.TRUE);
            }
        }, executor);
        // 加载默认仓库
        CompletableFuture<Void> f12 = CompletableFuture.runAsync(() -> {
            try {
                EcbStore store = new EcbStore();
                List<EcbStore> list = ecbStoreModel.getList(store);
                for (EcbStore ecbStore : list) {
                    EcbuStore ecbuStore = new EcbuStore();
                    ecbuStore.setEcCompanyId(ecCompanyId);
                    ecbuStore.setStoreName(ecbStore.getStoreName());
                    ecbuStore.setSortId(ecbStore.getSortId());
                    ecbuStore.setStartType(ecbStore.getStartType());
                    ecbuStore.setDefaultType(ecbStore.getDefaultType());
                    ecbuStore.setPercentCopper(ecbStore.getPercentCopper());
                    ecbuStore.setPercentAluminium(ecbStore.getPercentAluminium());
                    ecbuStore.setDunitMoney(ecbStore.getDunitMoney());
                    ecbuStore.setDescription(ecbStore.getDescription());
                    ecbuStoreModel.dealDefault(ecbuStore);
                }
                //再次查询刚刚创建的默认仓库
                EcbuStore recordEcbuStore = new EcbuStore();
                recordEcbuStore.setEcCompanyId(ecCompanyId);
                recordEcbuStore.setDefaultType(true);
                EcbuStore ecbuStore = ecbuStoreModel.getObjectDefaultPassEcCompanyId(recordEcbuStore);
                // 加载快递
                // 主表
                List<EcbDelivery> listEcbDelivery = ecbDeliveryModel.getListStart();
                for (EcbDelivery ecbDelivery : listEcbDelivery) {
                    EcbuDelivery ecbuDelivery = new EcbuDelivery();
                    ecbuDelivery.setEcCompanyId(ecCompanyId);
                    ecbuDelivery.setEcbusId(ecbuStore.getEcbusId());
                    ecbuDelivery.setDeliveryName(ecbDelivery.getDeliveryName());
                    ecbuDelivery.setSortId(ecbDelivery.getSortId());
                    ecbuDelivery.setStartType(ecbDelivery.getStartType());
                    ecbuDelivery.setDeliveryType(ecbDelivery.getDeliveryType());
                    ecbuDelivery.setDescription(ecbDelivery.getDescription());
                    ecbuDeliveryModel.deal(ecbuDelivery);
                }
                log.info("保存默认仓库数据完成！");
            } catch (Exception e) {
                ab.set(Boolean.TRUE);
                log.error("保存默认仓库数据异常！", e.getCause());
            }
        }, executor);
        //木轴
        CompletableFuture<Void> f13 = CompletableFuture.runAsync(() -> {
            try {
                EcbAxle ecbAxle = new EcbAxle();
                List<EcbAxle> list = ecbAxleService.getList(ecbAxle);
                for (EcbAxle axle : list) {
                    EcbuAxle ecbuAxle = new EcbuAxle();
                    BeanUtils.copyProperties(axle, ecbuAxle);
                    ecbuAxle.setEcCompanyId(ecCompanyId);
                    ecbuAxleService.insert(ecbuAxle);
                }
                log.info("保存木轴数据完成！");
            } catch (Exception e) {
                ab.set(Boolean.TRUE);
                log.error("保存木轴数据异常！", e.getCause());
            }
        }, executor);
        //税点
        CompletableFuture<Void> f14 = CompletableFuture.runAsync(() -> {
            try {
                EcdTaxPoint ecbAxle = new EcdTaxPoint();
                List<EcdTaxPoint> list = ecdTaxPointService.selectList(ecbAxle);
                for (EcdTaxPoint ecdTaxPoint : list) {
                    EcduTaxPoint ecduTaxPoint = new EcduTaxPoint();
                    BeanUtils.copyProperties(ecdTaxPoint, ecduTaxPoint);
                    ecduTaxPoint.setName(ecdTaxPoint.getPointName());
                    ecduTaxPoint.setEcCompanyId(ecCompanyId);
                    ecduTaxPointService.insert(ecduTaxPoint);
                }
                log.info("保存税点数据完成！");
            } catch (Exception e) {
                ab.set(Boolean.TRUE);
                log.error("保存税点数据异常！", e.getCause());
            }
        }, executor);
        log.info("-----------------准备进入异步的join------------------------");
        CompletableFuture.allOf(f9, f10, f11, f12, f13, f14).join();
        log.info("-----------------异步的join完成------------------------");
        //先创建材料类型，再创建材料
        // 创建材料类型
        Map<Integer, Integer> materialTypeMap = new HashMap<>();
        try {
            EcbMaterialType type = new EcbMaterialType();
            type.setStartType(true);
            List<EcbMaterialType> ecbMaterialTypeList = ecbMaterialTypeMapper.getList(type);
            for (EcbMaterialType ecbMaterialType : ecbMaterialTypeList) {
                EcbuMaterialType ecbuMaterialType = new EcbuMaterialType();
                ecbuMaterialType.setEcCompanyId(ecCompanyId);
                ecbuMaterialType.setStartType(true);
                ecbuMaterialType.setSortId(ecbMaterialType.getSortId());
                ecbuMaterialType.setFullName(ecbMaterialType.getFullName());
                ecbuMaterialType.setDescription(ecbMaterialType.getDescription());
                ecbuMaterialType.setMaterialType(ecbMaterialType.getMaterialType());
                ecbuMaterialType.setAddTime(new Date());
                ecbuMaterialTypeMapper.insert(ecbuMaterialType);
                materialTypeMap.put(ecbMaterialType.getId(), ecbuMaterialType.getId());
            }
            log.info("保存材料类型完成！");
        } catch (Exception e) {
            log.error("保存材料类型异常！", e.getCause());
            ab.set(Boolean.TRUE);
        }
        // 创建材料
        if (!ab.get()) {
            try {
                List<EcbMaterials> ecbMaterialsList = ecbMaterialsMapper.getList(null);
                for (EcbMaterials ecbMaterials : ecbMaterialsList) {
                    EcbuMaterials ecbuMaterials = new EcbuMaterials();
                    ecbuMaterials.setEcCompanyId(ecCompanyId);
                    ecbuMaterials.setMaterialTypeId(materialTypeMap.get(ecbMaterials.getMaterialTypeId()));
                    ecbuMaterials.setConductorType(ecbMaterials.getConductorType());
                    ecbuMaterials.setStartType(true);
                    ecbuMaterials.setSortId(ecbMaterials.getSortId());
                    ecbuMaterials.setFullName(ecbMaterials.getFullName());
                    ecbuMaterials.setUnitPrice(ecbMaterials.getUnitPrice());
                    ecbuMaterials.setDensity(ecbMaterials.getDensity());
                    ecbuMaterials.setResistivity(ecbMaterials.getResistivity());
                    ecbuMaterials.setDescription(ecbMaterials.getDescription());
                    ecbuMaterials.setAddTime(new Date());
                    ecbuMaterialsMapper.insert(ecbuMaterials);
                }
                log.info("保存材料完成！");
            } catch (Exception e) {
                log.error("保存材料异常！", e.getCause());
                ab.set(Boolean.TRUE);
            }
        }

        if (!ab.get()) {
            log.info("前面基础资料创建没问题，进入下面的创建");
            try {
                log.info("--------------------进入最后的创建-------------------------");
                List<EcbuDelivery> listEcbuDelivery = ecbuDeliveryModel.getListStart(ecCompanyId);
                for (EcbuDelivery ecbuDelivery : listEcbuDelivery) {
                    Integer ecbudId = ecbuDelivery.getEcbudId();
                    String deliveryName = ecbuDelivery.getDeliveryName();
                    EcbDelivery ecbDelivery = ecbDeliveryModel.getObjectPassDeliveryName(deliveryName);
                    // 快递
                    if (ecbDelivery.getDeliveryType() == 1) {
                        // 快递
                        List<EcbdMoney> listEcbdMoney = ecbdMoneyModel.getListPassEcbdId(ecbDelivery.getEcbdId());
                        for (EcbdMoney ecbdMoney : listEcbdMoney) {
                            EcbudMoney ecbudMoney = new EcbudMoney();
                            ecbudMoney.setEcbudId(ecbudId);
                            ecbudMoney.setSortId(ecbdMoney.getSortId());
                            ecbudMoney.setStartType(ecbdMoney.getStartType());
                            ecbudMoney.setEcpId(ecbdMoney.getEcpId());
                            ecbudMoney.setProvinceName(ecbdMoney.getProvinceName());
                            ecbudMoney.setFirstMoney(ecbdMoney.getFirstMoney());
                            ecbudMoney.setFirstWeight(ecbdMoney.getFirstWeight());
                            ecbudMoney.setContinueMoney(ecbdMoney.getContinueMoney());
                            ecbudMoneyModel.deal(ecbudMoney);
                        }
                    } else if (ecbDelivery.getDeliveryType() == 2) {
                        // 快运重量区间
                        EcbdWeight ecbdWeight = ecbdWeightlModel.getObjectPassEcbdId(ecbDelivery.getEcbdId());
                        EcbudWeight ecbudWeight = new EcbudWeight();
                        ecbudWeight.setEcbudId(ecbudId);
                        ecbudWeight.setStartWeight1(ecbdWeight.getStartWeight1());
                        ecbudWeight.setEndWeight1(ecbdWeight.getEndWeight1());
                        ecbudWeight.setStartWeight2(ecbdWeight.getStartWeight2());
                        ecbudWeight.setEndWeight2(ecbdWeight.getEndWeight2());
                        ecbudWeight.setStartWeight3(ecbdWeight.getStartWeight3());
                        ecbudWeight.setEndWeight3(ecbdWeight.getEndWeight3());
                        ecbudWeight.setStartWeight4(ecbdWeight.getStartWeight4());
                        ecbudWeight.setEndWeight4(ecbdWeight.getEndWeight4());
                        ecbudWeight.setStartWeight5(ecbdWeight.getStartWeight5());
                        ecbudWeight.setEndWeight5(ecbdWeight.getEndWeight5());
                        ecbudWeightModel.deal(ecbudWeight);
                        // 2.物流
                        List<EcbdPrice> listEcbdPrice = ecbdPriceModel.getListPassEcbdId(ecbDelivery.getEcbdId());
                        for (EcbdPrice ecbdPrice : listEcbdPrice) {
                            EcbudPrice price = new EcbudPrice();
                            price.setEcbudId(ecbudId);
                            price.setSortId(ecbdPrice.getSortId());
                            price.setStartType(ecbdPrice.getStartType());
                            price.setEcpId(ecbdPrice.getEcpId());
                            price.setProvinceName(ecbdPrice.getProvinceName());
                            price.setFirstPrice(ecbdPrice.getFirstPrice());
                            price.setPrice1(ecbdPrice.getPrice1());
                            price.setPrice2(ecbdPrice.getPrice2());
                            price.setPrice3(ecbdPrice.getPrice3());
                            price.setPrice4(ecbdPrice.getPrice4());
                            price.setPrice5(ecbdPrice.getPrice5());
                            ecbudPriceModel.deal(price);
                        }
                    }
                }
                // 批量写入用户省级表
                ecduPccModel.load(ecCompanyId);
                //创建型号类型和型号
                //暂存系统型号类型与用户型号类型的对照关系
                Map<Integer, Integer> silkMap = new HashMap<>();
                List<EcSilk> listSilk = ecSilkServiceModel.getListStart();
                int silkSort = 1;
                for (EcSilk ecSilk : listSilk) {
                    EcuSilk ecuSilk = new EcuSilk();
                    ecuSilk.setCompanyId(ecCompanyId);
                    ecuSilk.setSortId(silkSort);
                    ecuSilk.setStartType(true);
                    ecuSilk.setAbbreviation(ecSilk.getAbbreviation());
                    ecuSilk.setFullName(ecSilk.getFullName());
                    ecuSilkService.insert(ecuSilk);
                    silkSort = silkSort + 1;
                    //根据系统型号类型id查询系统的型号
                    List<EcSilkModel> ecSilkModels = ecSilkModelService.selectListBySilkId(ecSilk.getEcsId());
                    //刚刚写入的用户型号Id
                    Integer ecusId = ecuSilk.getEcusId();
                    silkMap.put(ecSilk.getEcsId(), ecusId);
                    for (EcSilkModel ecSilkModel : ecSilkModels) {
                        EcuSilkModel ecuSilkModel = new EcuSilkModel();
                        BeanUtils.copyProperties(ecSilkModel, ecuSilkModel);
                        ecuSilkModel.setCompanyId(ecCompanyId);
                        ecuSilkModel.setEcuSilkId(ecusId);
                        ecuSilkModelService.insert(ecuSilkModel);
                    }
                }
                EcqLevel ecqLevel = new EcqLevel();
                ecqLevel.setStartType(true);
                List<EcqLevel> ecqLevels = ecqLevelService.getList(ecqLevel);
                //if (!ecqLevels.isEmpty()) {
                //    log.info("插入质量等级信息  {}", ecqLevels.size());
                //    //根据系统的id获取前面插入的用户的材料id
                //    Map<Integer, Integer> integerIntegerMap = ecbuInsulationModel.getMapAll(ecCompanyId);
                //    Map<Integer, Integer> bagMap = ecbuBagModel.getMapAll(ecCompanyId);
                //    Map<Integer, Integer> shieldModelMap = ecbuShieldModel.getMapAll(ecCompanyId);
                //    Map<Integer, Integer> steelBandMap = ecbuSteelbandModel.getMapAll(ecCompanyId);
                //    Map<Integer, Integer> sheathMap = ecbuSheathModel.getMapAll(ecCompanyId);
                //    Map<Integer, Integer> micaTapMap = ecbuMicaTapeModel.getMapAll(ecCompanyId);
                //    Map<Integer, Integer> infillMap = ecbuInfillingModel.getMapAll(ecCompanyId);
                //    for (EcqLevel level : ecqLevels) {
                //        // 先创建相应的用户质量等级
                //        //查找用户的型号ID
                //        Integer ecuSilkId = silkMap.get(level.getEcsId());
                //        EcquLevel ecquLevel = new EcquLevel();
                //        ecquLevel.setEcusId(ecuSilkId);
                //        // 获取导体
                //        EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcbcIdAndEcCompanyId(level.getEcbcId(), ecCompanyId);
                //        if (ObjUtil.isNull(ecbuConductor)) {
                //            log.error("查询到的导体为空 --->{}", level.getEcbcId());
                //            continue;
                //        }
                //        ecquLevel.setEcbucId(ecbuConductor.getEcbucId());
                //        ecquLevel.setEcCompanyId(ecCompanyId);
                //        ecquLevel.setStartType(true);
                //        ecquLevel.setPowerId(1);
                //        ecquLevel.setDefaultType(level.getDefaultType());
                //        ecquLevel.setName(level.getName());
                //        ecquLevel.setDescription(level.getDescription());
                //        ecquLevelModel.deal(ecquLevel);
                //        //根据系统质量等级ID查询成本库表
                //        List<EcOffer> listEcOffer = ecOfferModel.getList(level.getEcqlId());
                //        for (EcOffer ecOffer : listEcOffer) {
                //            EcuOffer recordEcuOffer = new EcuOffer();
                //            recordEcuOffer.setEcCompanyId(ecCompanyId);
                //            //刚刚插入的用户质量等级ID
                //            recordEcuOffer.setEcqulId(ecquLevel.getEcqulId());
                //            recordEcuOffer.setEcbucId(ecbuConductor.getEcbucId());
                //            recordEcuOffer.setStartType(true);
                //            recordEcuOffer.setAreaStr(ecOffer.getAreaStr());
                //            recordEcuOffer.setAddPercent(new BigDecimal("0"));
                //            recordEcuOffer.setFireSilkNumber(ecOffer.getFireSilkNumber());
                //            recordEcuOffer.setFireRootNumber(ecOffer.getFireRootNumber());
                //            recordEcuOffer.setFireMembrance(ecOffer.getFireMembrance());
                //            recordEcuOffer.setFirePress(ecOffer.getFirePress());
                //            recordEcuOffer.setFireStrand(ecOffer.getFireStrand());
                //            recordEcuOffer.setZeroSilkNumber(ecOffer.getZeroSilkNumber());
                //            recordEcuOffer.setZeroRootNumber(ecOffer.getZeroRootNumber());
                //            recordEcuOffer.setZeroMembrance(ecOffer.getZeroMembrance());
                //            recordEcuOffer.setZeroPress(ecOffer.getZeroPress());
                //            recordEcuOffer.setZeroStrand(ecOffer.getZeroStrand());
                //            // 绝缘
                //            Integer ecbuiId = 0;
                //            if (integerIntegerMap.get(ecOffer.getEcbiId()) != null) {
                //                ecbuiId = integerIntegerMap.get(ecOffer.getEcbiId());
                //            }
                //            recordEcuOffer.setEcbuiId(ecbuiId);
                //            recordEcuOffer.setInsulationFireThickness(ecOffer.getInsulationFireThickness());
                //            recordEcuOffer.setInsulationZeroThickness(ecOffer.getInsulationZeroThickness());
                //            // 包带
                //            Integer ecbubId = 0;
                //            if (bagMap.get(ecOffer.getEcbbId()) != null) {
                //                ecbubId = bagMap.get(ecOffer.getEcbbId());
                //            }
                //            recordEcuOffer.setEcbubId(ecbubId);
                //            recordEcuOffer.setBagThickness(ecOffer.getBagThickness());
                //            // 铠装包带
                //            if (bagMap.get(ecOffer.getEcbb22Id()) != null) {
                //                ecbubId = bagMap.get(ecOffer.getEcbb22Id());
                //            }
                //            recordEcuOffer.setEcbub22Id(ecbubId);
                //            recordEcuOffer.setBag22Thickness(ecOffer.getBagThickness());
                //            // 屏蔽
                //            Integer ecbusId = 0;
                //            if (shieldModelMap.get(ecOffer.getEcbShieldId()) != null) {
                //                ecbusId = shieldModelMap.get(ecOffer.getEcbShieldId());
                //            }
                //            recordEcuOffer.setEcbuShieldId(ecbusId);
                //            recordEcuOffer.setShieldThickness(ecOffer.getShieldThickness());
                //            recordEcuOffer.setShieldPercent(ecOffer.getShieldPercent());
                //            // 钢带
                //            Integer ecbusbId = 0;
                //            if (steelBandMap.get(ecOffer.getEcbsbId()) != null) {
                //                ecbusbId = steelBandMap.get(ecOffer.getEcbsbId());
                //            }
                //            recordEcuOffer.setEcbusbId(ecbusbId);
                //            recordEcuOffer.setSteelbandThickness(ecOffer.getSteelbandThickness());
                //            recordEcuOffer.setSteelbandStorey(ecOffer.getSteelbandStorey());
                //            // 护套
                //            Integer ecbusid = 0;
                //            if (sheathMap.get(ecOffer.getEcbSheathId()) != null) {
                //                ecbusid = sheathMap.get(ecOffer.getEcbSheathId());
                //            }
                //            recordEcuOffer.setEcbuSheathId(ecbusid);
                //            recordEcuOffer.setSheathThickness(ecOffer.getSheathThickness());
                //            recordEcuOffer.setSheath22Thickness(ecOffer.getSheath22Thickness());
                //            // 云母带
                //            Integer ecbumId = 0;
                //            if (micaTapMap.get(ecOffer.getEcbmId()) != null) {
                //                ecbumId = micaTapMap.get(ecOffer.getEcbmId());
                //            }
                //            recordEcuOffer.setEcbumId(ecbumId);
                //            recordEcuOffer.setMicatapeThickness(ecOffer.getMicatapeThickness());
                //            // 填充物
                //            Integer ecbuinId = 0;
                //            if (infillMap.get(ecOffer.getEcbinId()) != null) {
                //                ecbuinId = infillMap.get(ecOffer.getEcbinId());
                //            }
                //            recordEcuOffer.setEcbuinId(ecbuinId);
                //            // 钢丝
                //            recordEcuOffer.setEcbuswId(0);
                //            recordEcuOffer.setSteelwireMembrance(ecOffer.getSteelwireMembrance());
                //            recordEcuOffer.setSteelwirePress(ecOffer.getSteelwirePress());
                //            // 成缆系数
                //            recordEcuOffer.setCableStrand(ecOffer.getCableStrand());
                //            recordEcuOffer.setDefaultWeight(ecOffer.getDefaultWeight());
                //            recordEcuOffer.setDefaultMoney(ecOffer.getDefaultMoney());
                //            ecuOfferModel.saveOrUpdate(recordEcuOffer);
                //        }
                //        //给规格写入用户规格表
                //        ecuOfferModel.loadArea(ecCompanyId, ecquLevel.getEcqulId());
                //    }
                //}
            } catch (Exception e) {
                ab.set(Boolean.TRUE);
                log.error("最后的创建失败", e);
                throw new RuntimeException(e.getMessage());
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
        ecbuMaterialsMapper.deleteByEcCompanyId(ecCompanyId);// 清除材料类型
        ecbuMaterialTypeMapper.deleteByEcCompanyId(ecCompanyId);// 清除材料
        //ecbuInsulationModel.deletePassEcCompanyId(ecCompanyId);// 清除绝缘
        //ecbuShieldModel.deletePassEcCompanyId(ecCompanyId);// 清除屏蔽
        //ecbuMicaTapeModel.deletePassEcCompanyId(ecCompanyId);// 清除云母带
        //ecbuInfillingModel.deletePassEcCompanyId(ecCompanyId);// 清除填充物
        //ecbuBagModel.deletePassEcCompanyId(ecCompanyId);// 清除包带
        //ecbuSteelbandModel.deletePassEcCompanyId(ecCompanyId);// 清除钢带
        //ecbuSheathModel.deletePassEcCompanyId(ecCompanyId);// 清除护套
        ecbulUnitModel.deletePassEcCompanyId(ecCompanyId);// 清除长度单位
        ecduCompanyModel.deletePassEcCompanyId(ecCompanyId);// 清除公司数据
        ecbuPlatformCompanyModel.deletePassEcCompanyId(ecCompanyId);// 清除平台公司
        ecbuStoreModel.deletePassEcCompanyId(ecCompanyId);// 清除默认仓库
        // 清除快递数据
        List<EcbuDelivery> listEcbuDelivery = ecbuDeliveryModel.getListStart(ecCompanyId);
        for (EcbuDelivery ecbuDelivery : listEcbuDelivery) {
            Integer ecbudId = ecbuDelivery.getEcbudId();
            ecbudWeightModel.deletePassEcbudId(ecbudId);// 清除物流模型
            ecbudPriceModel.deletePassEcbudId(ecbudId);// 清除物流
            ecbudMoneyModel.deletePassEcbudId(ecbudId);// 清除快递
        }
        ecuOfferModel.deletePassEcCompanyId(ecCompanyId); // 清除国标库
        ecbuAxleService.deletePassEcCompanyId(ecCompanyId);//清除木轴
        ecduTaxPointService.deletePassEcCompanyId(ecCompanyId);//清除税点
    }

}
