package org.jeecg.modules.cable.model.load;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class LoadRegister {
    @Resource
    private BaseRegister baseRegister;


    /**
     * 初始化公司基础数据
     *
     * @param ecCompanyId 公司ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void load(Integer ecCompanyId) {
        final AtomicBoolean ab = new AtomicBoolean(false);
        baseRegister.base(ecCompanyId, ab);
        //如果内部返回的这个值本身就是true，说名在多线程内就错了，直接清理报错
        if (ab.get()) {
            log.error("进入最后的删除");
            baseRegister.clean(ecCompanyId);
            throw new RuntimeException("初始化客户失败");
        }
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