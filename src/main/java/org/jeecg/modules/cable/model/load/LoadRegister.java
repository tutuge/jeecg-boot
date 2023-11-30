package org.jeecg.modules.cable.model.load;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPlatformCompany;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdWeight;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
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
import org.jeecg.modules.cable.model.userEcable.*;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import org.jeecg.modules.cable.model.userQuality.EcquLevelModel;
import org.jeecg.modules.cable.service.systemDelivery.EcSilkModelService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

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
    EcbuSteelBandModel ecbuSteelbandModel;
    @Resource
    EcbuSheathModel ecbuSheathModel;
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
    private PlatformTransactionManager transactionManager;
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executor;


    /**
     * 初始化公司基础数据
     *
     * @param ecCompanyId 公司ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void load(Integer ecCompanyId) {
        //final CountDownLatch al = new CountDownLatch(12);
        final AtomicBoolean ab = new AtomicBoolean(false);
        //TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        CompletableFuture<Void> base = base(ecCompanyId, ab);
        base.join();
        //如果内部返回的这个值本身就是true，说名在多线程内就错了，直接清理报错
        if (ab.get()) {
            clean(ecCompanyId);
            throw new RuntimeException("初始化客户失败");
        }
        //执行主线程
        log.info("进入创建的主线程");
        try {
            log.info("进入最后的任务");
            saveOffer(ecCompanyId);
            log.info("最后的任务执行完毕");
        } catch (Exception e) {
            log.error("进入最后的手动删除", e.getCause());
            clean(ecCompanyId);
            throw new RuntimeException("初始化客户失败");
        }
    }

    private void saveOffer(Integer ecCompanyId) {
        log.error("--------------------进入最后的创建-------------------------");
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
        ecduPccModel.load(1, ecCompanyId);// 加载txt
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
            ecuSilkService.save(ecuSilk);
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
        if (!ecqLevels.isEmpty()) {
            //根据系统的id获取前面插入的用户的材料id
            Map<Integer, Integer> integerIntegerMap = ecbuInsulationModel.getMapAll(ecCompanyId);
            Map<Integer, Integer> bagMap = ecbuBagModel.getMapAll(ecCompanyId);
            Map<Integer, Integer> shieldModelMap = ecbuShieldModel.getMapAll(ecCompanyId);
            Map<Integer, Integer> steelBandMap = ecbuSteelbandModel.getMapAll(ecCompanyId);
            Map<Integer, Integer> sheathMap = ecbuSheathModel.getMapAll(ecCompanyId);
            Map<Integer, Integer> micaTapMap = ecbuMicaTapeModel.getMapAll(ecCompanyId);
            Map<Integer, Integer> infillMap = ecbuInfillingModel.getMapAll(ecCompanyId);
            for (EcqLevel level : ecqLevels) {
                // 先创建相应的用户质量等级
                //查找用户的型号ID
                Integer ecuSilkId = silkMap.get(level.getEcsId());
                EcquLevel ecquLevel = new EcquLevel();
                ecquLevel.setEcusId(ecuSilkId);
                // 获取导体
                EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcbcIdAndEcCompanyId(level.getEcbcId(), ecCompanyId);
                if (ObjUtil.isNull(ecbuConductor)) {
                    log.error("查询到的导体为空 --->{}", level.getEcbcId());
                    continue;
                }
                ecquLevel.setEcbucId(ecbuConductor.getEcbucId());
                ecquLevel.setEcCompanyId(ecCompanyId);
                ecquLevel.setStartType(true);
                ecquLevel.setPowerId(1);
                ecquLevel.setDefaultType(level.getDefaultType());
                ecquLevel.setName(level.getName());
                ecquLevel.setDescription(level.getDescription());
                ecquLevelModel.deal(ecquLevel);
                //根据系统质量等级ID查询成本库表
                List<EcOffer> listEcOffer = ecOfferModel.getList(level.getEcqlId());
                for (EcOffer ecOffer : listEcOffer) {
                    EcuOffer recordEcuOffer = new EcuOffer();
                    recordEcuOffer.setEcCompanyId(ecCompanyId);
                    //刚刚插入的用户质量等级ID
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
                    Integer ecbuiId = 0;
                    if (integerIntegerMap.get(ecOffer.getEcbiId()) != null) {
                        ecbuiId = integerIntegerMap.get(ecOffer.getEcbiId());
                    }
                    recordEcuOffer.setEcbuiId(ecbuiId);
                    recordEcuOffer.setInsulationFireThickness(ecOffer.getInsulationFireThickness());
                    recordEcuOffer.setInsulationZeroThickness(ecOffer.getInsulationZeroThickness());
                    // 包带
                    Integer ecbubId = 0;
                    if (bagMap.get(ecOffer.getEcbbId()) != null) {
                        ecbubId = bagMap.get(ecOffer.getEcbbId());
                    }
                    recordEcuOffer.setEcbubId(ecbubId);
                    recordEcuOffer.setBagThickness(ecOffer.getBagThickness());
                    // 铠装包带
                    if (bagMap.get(ecOffer.getEcbb22Id()) != null) {
                        ecbubId = bagMap.get(ecOffer.getEcbb22Id());
                    }
                    recordEcuOffer.setEcbub22Id(ecbubId);
                    recordEcuOffer.setBag22Thickness(ecOffer.getBagThickness());
                    // 屏蔽
                    Integer ecbusId = 0;
                    if (shieldModelMap.get(ecOffer.getEcbShieldId()) != null) {
                        ecbusId = shieldModelMap.get(ecOffer.getEcbShieldId());
                    }
                    recordEcuOffer.setEcbuShieldId(ecbusId);
                    recordEcuOffer.setShieldThickness(ecOffer.getShieldThickness());
                    recordEcuOffer.setShieldPercent(ecOffer.getShieldPercent());
                    // 钢带
                    Integer ecbusbId = 0;
                    if (steelBandMap.get(ecOffer.getEcbsbId()) != null) {
                        ecbusbId = steelBandMap.get(ecOffer.getEcbsbId());
                    }
                    recordEcuOffer.setEcbusbId(ecbusbId);
                    recordEcuOffer.setSteelbandThickness(ecOffer.getSteelbandThickness());
                    recordEcuOffer.setSteelbandStorey(ecOffer.getSteelbandStorey());
                    // 护套
                    Integer ecbusid = 0;
                    if (sheathMap.get(ecOffer.getEcbuSheathId()) != null) {
                        ecbusid = sheathMap.get(ecOffer.getEcbuSheathId());
                    }
                    recordEcuOffer.setEcbuSheathId(ecbusid);
                    recordEcuOffer.setSheathThickness(ecOffer.getSheathThickness());
                    recordEcuOffer.setSheath22Thickness(ecOffer.getSheath22Thickness());
                    // 云母带
                    Integer ecbumId = 0;
                    if (micaTapMap.get(ecOffer.getEcbmId()) != null) {
                        ecbumId = micaTapMap.get(ecOffer.getEcbmId());
                    }
                    recordEcuOffer.setEcbumId(ecbumId);
                    recordEcuOffer.setMicatapeThickness(ecOffer.getMicatapeThickness());
                    // 填充物
                    Integer ecbuinId = 0;
                    if (infillMap.get(ecOffer.getEcbinId()) != null) {
                        ecbuinId = infillMap.get(ecOffer.getEcbinId());
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
                //给规格写入用户规格表
                ecuOfferModel.loadArea(ecCompanyId, ecquLevel.getEcqulId());
            }
        }
    }

    private CompletableFuture<Void> base(Integer ecCompanyId, AtomicBoolean ab) {
        //导体->云母带->绝缘->填充物->包袋->屏蔽->钢带->外护套
        // 加载导体
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
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
                ecbuConductorModel.loadData(ecCompanyId);// 加截txt
                //al.countDown();
                //log.info("保存导体！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存导体异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载云母带
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
                List<EcbMicaTape> listMicaTape = ecbuMicaTapeModel.getListStart();
                for (EcbMicaTape ecbMicatape : listMicaTape) {
                    EcbuMicaTape recordMicaTape = new EcbuMicaTape();
                    recordMicaTape.setEcbmId(ecbMicatape.getEcbmId());
                    recordMicaTape.setEcCompanyId(ecCompanyId);
                    recordMicaTape.setStartType(true);
                    recordMicaTape.setName("");
                    recordMicaTape.setUnitPrice(ecbMicatape.getUnitPrice());
                    recordMicaTape.setDensity(ecbMicatape.getDensity());
                    recordMicaTape.setDescription("");
                    ecbuMicaTapeModel.deal(recordMicaTape);
                }
                ecbuMicaTapeModel.loadData(ecCompanyId);// 加截txt
                //al.countDown();
                //log.info("保存云母带！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存云母带异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载绝缘
        CompletableFuture<Void> f3 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
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
                ecbuInsulationModel.loadData(ecCompanyId);// 加截txt
                //al.countDown();
                //log.info("保存绝缘！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存绝缘异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载填充物
        CompletableFuture<Void> f4 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
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
                ecbuInfillingModel.loadData(ecCompanyId);// txt文档
                //al.countDown();
                //log.info("保存填充物！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存填充物异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载包带
        CompletableFuture<Void> f5 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
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
                ecbuBagModel.loadData(ecCompanyId);// txt文档
                //al.countDown();
                //log.info("保存包带！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存包带异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载屏蔽
        CompletableFuture<Void> f6 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
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
                ecbuShieldModel.loadData(ecCompanyId);// txt文档
                //al.countDown();
                //log.info("保存屏蔽！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存屏蔽异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载钢带
        CompletableFuture<Void> f7 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
                List<EcbSteelBand> listSteelBand = ecbuSteelbandModel.getListStart();
                for (EcbSteelBand ecbSteelBand : listSteelBand) {
                    EcbuSteelBand recordSteelBand = new EcbuSteelBand();
                    recordSteelBand.setEcbsbId(ecbSteelBand.getEcbsbId());
                    recordSteelBand.setEcCompanyId(ecCompanyId);
                    recordSteelBand.setStartType(true);
                    recordSteelBand.setName("");
                    recordSteelBand.setUnitPrice(ecbSteelBand.getUnitPrice());
                    recordSteelBand.setDensity(ecbSteelBand.getDensity());
                    recordSteelBand.setDescription("");
                    ecbuSteelbandModel.deal(recordSteelBand);
                }
                ecbuSteelbandModel.loadData(ecCompanyId);// txt文档
                //al.countDown();
                //log.info("保存钢带！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存钢带异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载护套
        CompletableFuture<Void> f8 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
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
                //al.countDown();
                //log.info("保存护套！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存护套异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载长度单位
        CompletableFuture<Void> f9 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
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
                ecbulUnitModel.loadData(ecCompanyId);
                //al.countDown();
                //log.info("保存长度单位！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存长度单位异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载公司数据
        CompletableFuture<Void> f10 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
                List<EcdCompany> listEcdCompany = ecdCompanyModel.getListStart();
                for (int i = 0; i < listEcdCompany.size(); i++) {
                    EcdCompany ecdCompany = listEcdCompany.get(i);
                    EcduCompany recordEcduCompany = new EcduCompany();
                    recordEcduCompany.setEcCompanyId(ecCompanyId);
                    recordEcduCompany.setStartType(true);
                    recordEcduCompany.setSortId(ecdCompany.getSortId());
                    if (i == 0) {
                        recordEcduCompany.setDefaultType(true);
                    } else {
                        recordEcduCompany.setDefaultType(false);
                    }
                    recordEcduCompany.setAbbreviation(ecdCompany.getAbbreviation());
                    recordEcduCompany.setFullName(ecdCompany.getFullName());
                    recordEcduCompany.setLogoImg(ecdCompany.getLogoImg());
                    recordEcduCompany.setSealImg(ecdCompany.getSealImg());
                    recordEcduCompany.setBillPercentType(ecdCompany.getBillPercentType());
                    recordEcduCompany.setDescription(ecdCompany.getDescription());
                    ecduCompanyModel.deal(recordEcduCompany);
                }
                //al.countDown();
                //log.info("保存公司数据！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存公司数据异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 平台公司(天猫\淘宝等)数据
        CompletableFuture<Void> f11 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
            try {
                List<EcbPlatformCompanyVo> listEcbPlatformCompany = ecbPlatformcompanyModel.getListStart();
                for (EcbPlatformCompany ecbPlatformCompany : listEcbPlatformCompany) {
                    EcbuPlatformCompany recordEcbuPlatformCompany = new EcbuPlatformCompany();
                    recordEcbuPlatformCompany.setEcCompanyId(ecCompanyId);
                    recordEcbuPlatformCompany.setStartType(true);
                    recordEcbuPlatformCompany.setSortId(ecbPlatformCompany.getSortId());
                    recordEcbuPlatformCompany.setPlatformId(ecbPlatformCompany.getPlatformId());
                    recordEcbuPlatformCompany.setPcName(ecbPlatformCompany.getPcName());
                    recordEcbuPlatformCompany.setPcPercent(ecbPlatformCompany.getPcPercent());
                    recordEcbuPlatformCompany.setDescription(ecbPlatformCompany.getDescription());
                    // log.info("recordEcbuPcompany + " + CommonFunction.getGson().toJson(recordEcbuPcompany));
                    ecbuPlatformCompanyModel.saveOrUpdate(recordEcbuPlatformCompany);
                }
                ecbuPlatformCompanyModel.loadData(ecCompanyId);
                //al.countDown();
                //log.info("保存平台公司(天猫/淘宝等)数据！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                log.info("保存平台公司(天猫/淘宝等)数据异常！", e.getCause());
                //al.countDown();
                ab.set(Boolean.TRUE);
                //return false;
            }
            //    return true;
            //});
        }, executor);
        // 加载默认仓库
        CompletableFuture<Void> f12 = CompletableFuture.runAsync(() -> {
            //transactionTemplate.execute((var transactionStatus) -> {
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
                //al.countDown();
                //log.info("保存默认仓库数据！{}", al.getCount());
                //al.await();
                //if (ab.get()) throw new RuntimeException("手动抛错");
            } catch (Exception e) {
                //事务回滚；
                //transactionStatus.setRollbackOnly();
                //al.countDown();
                ab.set(Boolean.TRUE);
                log.error("保存默认仓库数据异常！", e.getCause());
                //return false;
            }
            //return true;
            //});
        }, executor);
        log.info("-----------------准备进入异步的join------------------------");
        return CompletableFuture.allOf(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12);
    }

    /**
     * 清空某公司下的数据
     *
     * @param ecCompanyId
     */
    @Transactional(rollbackFor = Exception.class)
    public void clean(Integer ecCompanyId) {
        ecbuConductorModel.deleteByEcCompanyId(ecCompanyId);// 清除导体
        ecbuInsulationModel.deletePassEcCompanyId(ecCompanyId);// 清除绝缘
        ecbuShieldModel.deletePassEcCompanyId(ecCompanyId);// 清除屏蔽
        ecbuMicaTapeModel.deletePassEcCompanyId(ecCompanyId);// 清除云母带
        ecbuInfillingModel.deletePassEcCompanyId(ecCompanyId);// 清除填充物
        ecbuBagModel.deletePassEcCompanyId(ecCompanyId);// 清除包带
        ecbuSteelbandModel.deletePassEcCompanyId(ecCompanyId);// 清除钢带
        ecbuSheathModel.deletePassEcCompanyId(ecCompanyId);// 清除护套
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
    }

    // loadZeyang
    //public void loadZeyang() {
    //    Integer ecCompanyId = 6;
    //    // 加载国标库
    //    List<EcSilk> listSilk = ecSilkServiceModel.getListStart();
    //    // log.info(CommonFunction.getGson().toJson(CommonFunction.getGson().toJson(listSilk)));
    //    EcquLevel recordEcquLevel = new EcquLevel();
    //    EcquLevel ecquLevel;
    //    EcuOffer recordEcuOffer = new EcuOffer();
    //    for (EcSilk ecSilk : listSilk) {
    //        List<EcOffer> listEcOffer = ecOfferModel.getList(ecSilk.getEcsId());
    //        log.info("listEcOffer + " + listEcOffer);
    //        if (!listEcOffer.isEmpty()) {
    //            // 获取导体
    //            EcbuConductor ecbuConductor;
    //            int ecbcId = 0;
    //            if (ecSilk.getEcsId() == 2) {// yjv
    //                ecbcId = 3;
    //            } else if (ecSilk.getEcsId() == 10) {// YJLV
    //                ecbcId = 2;
    //            } else if (ecSilk.getEcsId() == 6) {// BV
    //                ecbcId = 3;
    //            }
    //            ecbuConductor = ecbuConductorModel.getObjectPassEcbcIdAndEcCompanyId(ecbcId, ecCompanyId);
    //            // 先创建相应的质量等级
    //            recordEcquLevel.setEcusId(ecSilk.getEcsId());
    //            recordEcquLevel.setEcbucId(ecbuConductor.getEcbucId());
    //            recordEcquLevel.setEcCompanyId(ecCompanyId);
    //            recordEcquLevel.setStartType(true);
    //            recordEcquLevel.setPowerId(1);
    //            recordEcquLevel.setName(ecSilk.getAbbreviation() + "国标");
    //            recordEcquLevel.setDescription("");
    //            ecquLevelModel.deal(recordEcquLevel);
    //            ecquLevel = ecquLevelModel.getObjectPassEcCompanyIdAndName(ecCompanyId, ecSilk.getAbbreviation() + "国标");
    //            // 创建国标库
    //            for (EcOffer ecOffer : listEcOffer) {
    //                recordEcuOffer.setEcCompanyId(ecCompanyId);
    //                recordEcuOffer.setEcqulId(ecquLevel.getEcqulId());
    //                recordEcuOffer.setEcbucId(ecbuConductor.getEcbucId());
    //                recordEcuOffer.setStartType(true);
    //                recordEcuOffer.setAreaStr(ecOffer.getAreaStr());
    //                recordEcuOffer.setAddPercent(new BigDecimal("0"));
    //                recordEcuOffer.setFireSilkNumber(ecOffer.getFireSilkNumber());
    //                recordEcuOffer.setFireRootNumber(ecOffer.getFireRootNumber());
    //                recordEcuOffer.setFireMembrance(ecOffer.getFireMembrance());
    //                recordEcuOffer.setFirePress(ecOffer.getFirePress());
    //                recordEcuOffer.setFireStrand(ecOffer.getFireStrand());
    //                recordEcuOffer.setZeroSilkNumber(ecOffer.getZeroSilkNumber());
    //                recordEcuOffer.setZeroRootNumber(ecOffer.getZeroRootNumber());
    //                recordEcuOffer.setZeroMembrance(ecOffer.getZeroMembrance());
    //                recordEcuOffer.setZeroPress(ecOffer.getZeroPress());
    //                recordEcuOffer.setZeroStrand(ecOffer.getZeroStrand());
    //                // 绝缘
    //                EcbuInsulation ecbuInsulation = ecbuInsulationModel
    //                        .getObjectPassEcCompanyIdAndEcbiId(ecCompanyId, ecOffer.getEcbiId());
    //                Integer ecbuiId = 0;
    //                if (ecbuInsulation != null) {
    //                    ecbuiId = ecbuInsulation.getEcbuiId();
    //                }
    //                recordEcuOffer.setEcbuiId(ecbuiId);
    //                recordEcuOffer.setInsulationFireThickness(ecOffer.getInsulationFireThickness());
    //                recordEcuOffer.setInsulationZeroThickness(ecOffer.getInsulationZeroThickness());
    //                // 包带
    //                EcbuBag ecbuBag = ecbuBagModel
    //                        .getObjectPassEcCompanyIdAndEcbbId(ecCompanyId, ecOffer.getEcbbId());
    //                Integer ecbubId = 0;
    //                if (ecbuBag != null) {
    //                    ecbubId = ecbuBag.getEcbubId();
    //                }
    //                recordEcuOffer.setEcbubId(ecbubId);
    //                recordEcuOffer.setBagThickness(ecOffer.getBagThickness());
    //                // 铠装包带
    //                ecbuBag = ecbuBagModel.getObjectPassEcCompanyIdAndEcbbId(ecCompanyId, ecOffer.getEcbb22Id());
    //                if (ecbuBag != null) {
    //                    ecbubId = ecbuBag.getEcbubId();
    //                }
    //                recordEcuOffer.setEcbub22Id(ecbubId);
    //                recordEcuOffer.setBag22Thickness(ecOffer.getBagThickness());
    //                // 屏蔽
    //                EcbuShield ecbuShield = ecbuShieldModel.getObjectPassEcCompanyIdAndEcbsId(ecCompanyId, ecOffer.getEcbShieldId());
    //                Integer ecbusId = 0;
    //                if (ecbuShield != null) {
    //                    ecbusId = ecbuShield.getEcbusId();
    //                }
    //                recordEcuOffer.setEcbuShieldId(ecbusId);
    //                recordEcuOffer.setShieldThickness(ecOffer.getShieldThickness());
    //                recordEcuOffer.setShieldPercent(ecOffer.getShieldPercent());
    //                // 钢带
    //                EcbuSteelband ecbuSteelband = ecbuSteelbandModel.getObjectPassEcCompanyIdAndEcbsbId(ecCompanyId, ecOffer.getEcbsbId());
    //                Integer ecbusbId = 0;
    //                if (ecbuSteelband != null) {
    //                    ecbusbId = ecbuSteelband.getEcbusId();
    //                }
    //                recordEcuOffer.setEcbusbId(ecbusbId);
    //                recordEcuOffer.setSteelbandThickness(ecOffer.getSteelbandThickness());
    //                recordEcuOffer.setSteelbandStorey(ecOffer.getSteelbandStorey());
    //                // 护套
    //                EcbuSheath ecbuSheath = ecbuSheathModel
    //                        .getObjectPassEcCompanyIdAndEcbsId(ecCompanyId, ecOffer.getEcbuSheathId());
    //                Integer ecbusid = 0;
    //                if (ecbuSheath != null) {
    //                    ecbusid = ecbuSheath.getEcbusId();
    //                }
    //                recordEcuOffer.setEcbuSheathId(ecbusid);
    //                recordEcuOffer.setSheathThickness(ecOffer.getSheathThickness());
    //                recordEcuOffer.setSheath22Thickness(ecOffer.getSheath22Thickness());
    //                // 云母带
    //                EcbuMicaTape ecbuMicatape = ecbuMicaTapeModel.getObjectPassEcCompanyIdAndEcbmId(ecCompanyId, ecOffer.getEcbmId());
    //                Integer ecbumId = 0;
    //                if (ecbuMicatape != null) {
    //                    ecbumId = ecbuMicatape.getEcbumId();
    //                }
    //                recordEcuOffer.setEcbumId(ecbumId);
    //                recordEcuOffer.setMicatapeThickness(ecOffer.getMicatapeThickness());
    //                // 填充物
    //                EcbuInfilling ecbuInfilling = ecbuInfillingModel.getObjectPassEcCompanyIdAndEcbinId(ecCompanyId, ecOffer.getEcbinId());
    //                Integer ecbuinId = 0;
    //                if (ecbuInfilling != null) {
    //                    ecbuinId = ecbuInfilling.getEcbuiId();
    //                }
    //                recordEcuOffer.setEcbuinId(ecbuinId);
    //                // 钢丝
    //                recordEcuOffer.setEcbuswId(0);
    //                recordEcuOffer.setSteelwireMembrance(ecOffer.getSteelwireMembrance());
    //                recordEcuOffer.setSteelwirePress(ecOffer.getSteelwirePress());
    //                ecuOfferModel.saveOrUpdate(recordEcuOffer);
    //            }
    //            Integer ecqulId = ecquLevel.getEcqulId();
    //            ecquLevelModel.deal(ecCompanyId);// 加载load为集成数据
    //            ecuOfferModel.loadArea(ecCompanyId, ecqulId);// 加载质量等级对应的截面库ecuArea
    //        }
    //    }
    //}
}