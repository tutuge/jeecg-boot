package org.jeecg.modules.cable.model.load;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

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
    EcuOfferModel ecuOfferModel;
    @Resource
    EcbulUnitModel ecbulUnitModel; //用户长度单位
    @Resource
    EcduCompanyModel ecduCompanyModel; //用户客户的公司
    @Resource
    EcbuPlatformCompanyModel ecbuPlatformCompanyModel; //用户平台
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel; //用户物流
    @Resource
    EcbuStoreModel ecbuStoreModel; //用户仓库
    @Resource
    EcbudWeightModel ecbudWeightModel;// 物流模型
    @Resource
    EcbudMoneyModel ecbudMoneyModel;
    @Resource
    EcbudPriceModel ecbudPriceModel;
    @Resource
    private BaseRegister baseRegister;

    /**
     * 初始化公司基础数据
     *
     * @param ecCompanyId 公司ID
     */
    @Transactional(rollbackFor = Exception.class, isolation = READ_COMMITTED)
    public void load(Integer ecCompanyId) {
        final AtomicBoolean ab = new AtomicBoolean(false);
        try {
            baseRegister.base(ecCompanyId, ab);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("初始化客户失败");
        } finally {
            //如果内部返回的这个值本身就是true，说名在多线程内就错了，直接清理报错
            if (ab.get()) {
                log.error("进入最后的删除");
                clean(ecCompanyId);
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