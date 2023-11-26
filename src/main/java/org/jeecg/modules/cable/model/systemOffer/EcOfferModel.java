package org.jeecg.modules.cable.model.systemOffer;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.systemOffer.offer.vo.EcOfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.model.systemEcable.*;
import org.jeecg.modules.cable.service.price.EcOfferService;
import org.jeecg.modules.cable.service.systemEcable.EcbMicaTapeService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.jeecg.modules.cable.tools.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jeecg.modules.cable.tools.EcableFunction.getExternalDiameter;

@Service
@Slf4j
public class EcOfferModel {
    ExcelUtils excelUtils = new ExcelUtils();
    @Resource
    EcOfferService ecOfferService;
    @Resource
    private EcbInsulationModel ecbInsulationModel;
    @Resource
    private EcbBagModel ecbBagModel;
    @Resource
    private EcbShieldModel ecbShieldModel;
    @Resource
    private EcbSteelbandModel ecbSteelbandModel;
    @Resource
    private EcbSheathModel ecbSheathModel;
    @Resource
    private EcbMicaTapeService ecbMicatapeService;
    @Resource
    private EcbInfillingModel ecbInfillingModel;
    @Resource
    EcableEcOfferFunction ecableEcOfferFunction;
    @Resource
    private EcqLevelService ecqLevelService;


    public List<EcOffer> getList(Integer ecqlId) {
        EcOffer record = new EcOffer();
        record.setEcqlId(ecqlId);
        return ecOfferService.getList(record);
    }

    // importDeal
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> importDeal(MultipartFile file, Integer ecqlId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Integer status;
        String code;
        String msg;
        //MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //MultipartFile file = multipartRequest.getFile("file");
        assert file != null;
        InputStream in = file.getInputStream();
        List<List<Object>> listob = excelUtils.getListByExcel(in, file.getOriginalFilename());
        String areaStr;// 截面积
        String addPercentStr;// 成本加点
        String fireSilkNumberStr;// 粗芯丝号
        String fireRootNumberStr;// 粗芯丝号
        String fireStrandStr;// 粗芯丝绞合系数
        String zeroSilkNumberStr;// 细芯丝号
        String zeroRootNumberStr;// 细芯丝号
        String zeroStrandStr;// 细芯丝绞合系数
        String insulationStr;// 绝缘类型
        String insulationFireThicknessStr;// 粗芯绝缘厚度
        String insulationZeroThicknessStr;// 细芯芯绝缘厚度
        String bagStr;// 包带类型
        String bagThicknessStr;// 包带厚度
        String bag22Str;// 铠装包带类型
        String bag22ThicknessStr;// 铠装包带厚度
        String shieldStr;// 屏蔽类型
        String shieldThicknessStr;// 屏蔽厚度
        String shieldPercentStr;// 屏蔽编织系数
        String steelbandStr;// 钢带类型
        String steelbandThicknessStr;// 钢带厚度
        String steelbandStoreyStr;// 钢带厚度
        String sheathStr;// 护套类型
        String sheathThicknessStr;// 护套厚度
        String sheath22ThicknessStr;// 铠装厚度
        String micatapeThicknessStr;// 云母带厚度
        String infillingStr = "";// 填充物类型
        String cableStrandStr = "";
        List<Map<String, Object>> listObject = new ArrayList<>();
        Integer i = 0;
        for (List<Object> objects : listob) {
            log.info("i + " + i);
            if (objects.size() < 10) {
                break;
            }
            if (i > 1000) {
                break;
            }
            log.info(CommonFunction.getGson().toJson(objects));
            i++;
            Map<String, Object> mapObject = new HashMap<>();
            areaStr = objects.get(0).toString();// 截面积
            addPercentStr = objects.get(1).toString();// 成本加点
            fireSilkNumberStr = objects.get(2).toString();// 粗芯丝号
            fireRootNumberStr = objects.get(3).toString();// 粗芯根数
            fireStrandStr = objects.get(4).toString();// 粗芯丝绞合系数
            zeroSilkNumberStr = objects.get(5).toString();// 细芯丝号
            zeroRootNumberStr = objects.get(6).toString();// 细芯根数
            zeroStrandStr = objects.get(7).toString();// 细芯丝绞合系数
            insulationStr = objects.get(8).toString();// 绝缘类型
            insulationFireThicknessStr = objects.get(9).toString();// 粗芯绝缘厚度
            insulationZeroThicknessStr = objects.get(10).toString();// 细芯绝缘厚度
            bagStr = objects.get(11).toString();// 包带类型
            bagThicknessStr = objects.get(12).toString();// 包带厚度
            bag22Str = objects.get(13).toString();// 铠装包带类型
            bag22ThicknessStr = objects.get(14).toString();// 铠装包带厚度
            shieldStr = objects.get(15).toString();// 屏蔽类型
            shieldThicknessStr = objects.get(16).toString();// 屏蔽厚度
            shieldPercentStr = objects.get(17).toString();// 屏蔽编织系数
            steelbandStr = objects.get(18).toString();// 钢带类型
            steelbandThicknessStr = objects.get(19).toString();// 钢带厚度
            steelbandStoreyStr = objects.get(20).toString();// 钢带层数
            sheathStr = objects.get(21).toString();// 护套类型
            sheathThicknessStr = objects.get(22).toString();// 护套厚度
            sheath22ThicknessStr = objects.get(23).toString();// 铠装护套厚度
            micatapeThicknessStr = objects.get(24).toString();// 云母带厚度
            // log.info("objects.size + " + objects.size());
            if (objects.size() >= 26) {
                infillingStr = objects.get(25).toString();// 填充物类型
                cableStrandStr = objects.get(26).toString();// 成缆系数
            }
            mapObject.put("areaStr", areaStr);
            mapObject.put("addPercentStr", addPercentStr);
            mapObject.put("fireSilkNumberStr", fireSilkNumberStr);
            mapObject.put("fireRootNumberStr", fireRootNumberStr);
            mapObject.put("fireStrandStr", fireStrandStr);
            mapObject.put("zeroSilkNumberStr", zeroSilkNumberStr);
            mapObject.put("zeroRootNumberStr", zeroRootNumberStr);
            mapObject.put("zeroStrandStr", zeroStrandStr);
            mapObject.put("insulationStr", insulationStr);
            mapObject.put("insulationFireThicknessStr", insulationFireThicknessStr);
            mapObject.put("insulationZeroThicknessStr", insulationZeroThicknessStr);
            mapObject.put("bagStr", bagStr);
            mapObject.put("bagThicknessStr", bagThicknessStr);
            mapObject.put("bag22Str", bag22Str);
            mapObject.put("bag22ThicknessStr", bag22ThicknessStr);
            mapObject.put("shieldStr", shieldStr);
            mapObject.put("shieldThicknessStr", shieldThicknessStr);
            mapObject.put("shieldPercentStr", shieldPercentStr);
            mapObject.put("steelbandStr", steelbandStr);
            mapObject.put("steelbandThicknessStr", steelbandThicknessStr);
            mapObject.put("steelbandStoreyStr", steelbandStoreyStr);
            mapObject.put("sheathStr", sheathStr);
            mapObject.put("sheathThicknessStr", sheathThicknessStr);
            mapObject.put("sheath22ThicknessStr", sheath22ThicknessStr);
            mapObject.put("micatapeThicknessStr", micatapeThicknessStr);
            mapObject.put("infillingStr", infillingStr);
            mapObject.put("cableStrandStr", cableStrandStr);
            listObject.add(mapObject);
        }
        // log.info(listObject.toString());
        EcOffer record;
        Integer ii = 0;
        for (Map<String, Object> mapObject : listObject) {
            System.out.println(ii);
            ii++;
            areaStr = mapObject.get("areaStr").toString();// 截面积
            addPercentStr = mapObject.get("addPercentStr").toString();// 成本加点
            fireSilkNumberStr = mapObject.get("fireSilkNumberStr").toString();// 粗芯丝号
            fireRootNumberStr = mapObject.get("fireRootNumberStr").toString();// 粗芯根数
            fireStrandStr = mapObject.get("fireStrandStr").toString();// 粗芯丝绞合系数
            zeroSilkNumberStr = mapObject.get("zeroSilkNumberStr").toString();// 细芯丝号
            zeroRootNumberStr = mapObject.get("zeroRootNumberStr").toString();// 粗芯根数
            zeroStrandStr = mapObject.get("zeroStrandStr").toString();// 细芯丝绞合系数
            insulationStr = mapObject.get("insulationStr").toString();// 绝缘类型
            insulationFireThicknessStr = mapObject.get("insulationFireThicknessStr").toString();// 粗芯绝缘厚度
            insulationZeroThicknessStr = mapObject.get("insulationZeroThicknessStr").toString();// 细芯绝缘厚度
            bagStr = mapObject.get("bagStr").toString();// 包带类型
            bagThicknessStr = mapObject.get("bagThicknessStr").toString();// 包带厚度
            bag22Str = mapObject.get("bag22Str").toString();// 铠装包带类型
            bag22ThicknessStr = mapObject.get("bag22ThicknessStr").toString();// 铠装包带厚度
            shieldStr = mapObject.get("shieldStr").toString();// 屏蔽类型
            shieldThicknessStr = mapObject.get("shieldThicknessStr").toString();// 屏蔽厚度
            shieldPercentStr = mapObject.get("shieldPercentStr").toString();// 屏蔽编织密度
            steelbandStr = mapObject.get("steelbandStr").toString();// 钢带类型
            steelbandThicknessStr = mapObject.get("steelbandThicknessStr").toString();// 钢带厚度
            steelbandStoreyStr = mapObject.get("steelbandStoreyStr").toString();// 钢带层数
            sheathStr = mapObject.get("sheathStr").toString();// 护套类型
            sheathThicknessStr = mapObject.get("sheathThicknessStr").toString();// 护套厚度
            sheath22ThicknessStr = mapObject.get("sheath22ThicknessStr").toString();// 铠装护套厚度
            micatapeThicknessStr = mapObject.get("micatapeThicknessStr").toString();// 云母带厚度
            infillingStr = mapObject.get("infillingStr").toString();// 填充物类型
            cableStrandStr = mapObject.get("cableStrandStr").toString();
            BigDecimal addPercent = BigDecimal.ZERO;
            if (!"".equals(addPercentStr)) {
                addPercent = new BigDecimal(addPercentStr);
            }
            // 火线数据
            BigDecimal fireSilkNumber = new BigDecimal(fireSilkNumberStr)
                    .divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            Integer fireRootNumber = Integer.parseInt(fireRootNumberStr);// 粗芯根数
            BigDecimal fireStrand = BigDecimal.ONE;// 粗芯丝绞合系数
            if (!"0".equals(fireStrandStr) && !"".equals(fireStrandStr)) {
                fireStrand = new BigDecimal(fireStrandStr);
            }
            BigDecimal zeroSilkNumber = BigDecimal.ZERO;
            if (!"".equals(zeroSilkNumberStr)) {
                zeroSilkNumber = new BigDecimal(zeroSilkNumberStr).divide(new BigDecimal("1000"), 18, RoundingMode.HALF_UP);
            }
            Integer zeroRootNumber = 0;
            if (!"".equals(zeroRootNumberStr)) {
                zeroRootNumber = Integer.parseInt(fireRootNumberStr);
            }
            BigDecimal zeroStrand = BigDecimal.ONE;// 细芯丝绞合系数
            if (!"0".equals(zeroStrandStr) && !"".equals(zeroStrandStr)) {
                zeroStrand = new BigDecimal(zeroStrandStr);
            }
            Integer ecbiId = 0;// 绝缘
            BigDecimal insulationFireThickness = BigDecimal.ZERO;
            if (!"0".equals(insulationFireThicknessStr) && !"".equals(insulationFireThicknessStr)) {
                insulationFireThickness = new BigDecimal(insulationFireThicknessStr)
                        .divide(new BigDecimal("1000"), 18, RoundingMode.HALF_UP);
            }
            BigDecimal insulationZeroThickness = BigDecimal.ZERO;
            if (!"0".equals(insulationZeroThicknessStr) && !"".equals(insulationZeroThicknessStr)) {
                insulationZeroThickness = new BigDecimal(insulationZeroThicknessStr)
                        .divide(new BigDecimal("1000"), 18, RoundingMode.HALF_UP);
            }
            EcbInsulation ecbInsulation = ecbInsulationModel.getObjectPassAbbreviation(insulationStr);
            if (ecbInsulation != null) {
                ecbiId = ecbInsulation.getEcbiId();
            }
            // 包带
            Integer ecbbId = 0;
            EcbBag ecbBag = ecbBagModel.getObjectPassAbbreviation(bagStr);
            if (ecbBag != null) {
                ecbbId = ecbBag.getEcbbId();
            }
            BigDecimal bagThickness = BigDecimal.ZERO;
            if (!"".equals(bagThicknessStr)) {
                bagThickness = new BigDecimal(bagThicknessStr)
                        .divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            // 铠装包带包带
            Integer ecbb22Id = 0;
            EcbBag ecbu22Bag = ecbBagModel.getObjectPassAbbreviation(bag22Str);
            if (ecbu22Bag != null) {
                ecbb22Id = ecbu22Bag.getEcbbId();
            }
            BigDecimal bag22Thickness = BigDecimal.ZERO;
            if (!"".equals(bag22ThicknessStr)) {
                bag22Thickness = new BigDecimal(bag22ThicknessStr)
                        .divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            // 屏蔽
            Integer ecbsId = 0;
            EcbShield ecbuShield = ecbShieldModel.getObjectPassAbbreviation(shieldStr);
            if (ecbuShield != null) {
                ecbsId = ecbuShield.getEcbsId();
            }
            BigDecimal shieldThickness = new BigDecimal(0);
            if (!"".equals(shieldThicknessStr) && !"0".equals(shieldThicknessStr)) {
                shieldThickness = new BigDecimal(shieldThicknessStr)
                        .divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            BigDecimal shieldPercent = new BigDecimal(0);
            shieldPercentStr = shieldPercentStr.replace("%", "");
            if (!shieldPercentStr.isEmpty() && !"0".equals(shieldPercentStr)) {
                shieldPercent = new BigDecimal(shieldPercentStr);
            }
            // 钢带
            Integer ecbsbId = 0;
            EcbSteelBand ecbSteelband = ecbSteelbandModel.getObjectPassAbbreviation(steelbandStr);
            if (ecbSteelband != null) {
                ecbsbId = ecbSteelband.getEcbsbId();
            }
            BigDecimal steelbandThickness = new BigDecimal(0);
            if (!"".equals(steelbandThicknessStr)) {
                steelbandThickness = new BigDecimal(steelbandThicknessStr)
                        .divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            Integer steelbandStorey = 0;
            if (!"".equals(steelbandStoreyStr) && !"0".equals(steelbandStoreyStr)) {
                steelbandStorey = Integer.parseInt(steelbandStoreyStr);
            }
            // 护套
            Integer ecbsid = 0;
            EcbSheath ecbSheath = ecbSheathModel.getObjectPassAbbreviation(sheathStr);
            if (ecbSheath != null) {
                ecbsid = ecbSheath.getEcbsId();
            }
            BigDecimal sheathThickness = BigDecimal.ZERO;
            BigDecimal sheath22Thickness = BigDecimal.ZERO;// 铠装
            if (!"".equals(sheathThicknessStr)) {
                sheathThickness = new BigDecimal(sheathThicknessStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            if (!"".equals(sheath22ThicknessStr)) {
                sheath22Thickness = new BigDecimal(sheath22ThicknessStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            // 云母带
            Integer ecbmId = 0;
            EcbMicaTape ecbMicatape = ecbMicatapeService.getObject(null);
            if (ecbMicatape != null) {
                ecbmId = ecbMicatape.getEcbmId();
            }
            BigDecimal micatapeThickness = BigDecimal.ZERO;
            if (!"".equals(micatapeThicknessStr)) {
                micatapeThickness = new BigDecimal(micatapeThicknessStr)
                        .divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            // 填充物
            Integer ecbinId = 0;
            EcbInfilling ecbInfilling = ecbInfillingModel.getObjectPassAbbreviation(infillingStr);
            if (ecbInfilling != null) {
                ecbinId = ecbInfilling.getEcbinId();
            }
            // 成缆系数
            BigDecimal cableStrand = new BigDecimal(cableStrandStr);
            // 插入
            Integer sortId = 1;
            record = new EcOffer();
            record.setEcqlId(ecqlId);
            EcOffer ecOffer = ecOfferService.getObject(record);
            if (ecOffer != null) {
                sortId = ecOffer.getSortId() + 1;
            }
            Integer fireMembrance = 0;// 粗芯过膜
            BigDecimal firePress = BigDecimal.ZERO;// 粗芯压型
            Integer zeroMembrance = 0;// 细芯过膜
            BigDecimal zeroPress = BigDecimal.ZERO;// 细芯压型
            Integer ecbuswId = 0;// 钢丝类型
            BigDecimal steelwireMembrance = BigDecimal.ZERO;// 钢丝过膜
            BigDecimal steelwirePress = BigDecimal.ZERO;// 钢丝压型
            record = new EcOffer();
            //record.setEcsId(ecsId);
            record.setAreaStr(areaStr);
            record.setSortId(sortId);// 排序
            record.setAreaStr(areaStr);// 截面str
            record.setAddPercent(addPercent);
            record.setFireSilkNumber(fireSilkNumber);// 粗芯丝号
            record.setFireRootNumber(fireRootNumber);// 粗芯根数
            record.setFireMembrance(fireMembrance);// 粗芯过膜
            record.setFirePress(firePress);// 粗芯压型
            record.setZeroSilkNumber(zeroSilkNumber);// 细芯丝号
            record.setZeroRootNumber(zeroRootNumber);// 粗芯根数
            record.setZeroMembrance(zeroMembrance);// 细芯过膜
            record.setZeroPress(zeroPress);// 细芯过型
            record.setEcbiId(ecbiId);// 绝缘类型
            record.setInsulationFireThickness(insulationFireThickness);// 粗芯绝缘厚度
            record.setInsulationZeroThickness(insulationZeroThickness);// 细芯绝缘厚度
            record.setEcbbId(ecbbId);// 包带类型
            record.setBagThickness(bagThickness);// 包带厚度
            record.setEcbb22Id(ecbb22Id);// 铠装包带类型
            record.setBag22Thickness(bag22Thickness);// 铠装包带厚度
            record.setEcbShieldId(ecbsId);// 屏蔽类型
            record.setShieldThickness(shieldThickness);// 屏蔽厚度
            record.setShieldPercent(shieldPercent);// 屏蔽编织系数
            record.setEcbsbId(ecbsbId);// 钢带类型
            record.setSteelbandThickness(steelbandThickness);// 钢带厚度
            record.setSteelbandStorey(steelbandStorey);// 钢带层数
            record.setEcbuSheathId(ecbsid);// 护套类型
            record.setSheathThickness(sheathThickness);// 护套厚度
            record.setSheath22Thickness(sheath22Thickness);// 铠装护套厚度
            record.setEcbmId(ecbmId);// 云母带类型
            record.setMicatapeThickness(micatapeThickness);// 云母带厚度
            record.setFireStrand(fireStrand);// 粗芯绞合系数
            record.setZeroStrand(zeroStrand);// 细芯绞合系数
            record.setEcbinId(ecbinId);// 填充物类型
            record.setEcbswId(ecbuswId);// 钢丝类型
            record.setSteelwireMembrance(steelwireMembrance);// 钢丝过膜
            record.setSteelwirePress(steelwirePress);// 钢丝压型
            record.setCableStrand(cableStrand);
            record.setDefaultWeight(new BigDecimal("0"));
            record.setDefaultMoney(new BigDecimal("0"));
            // log.info(CommonFunction.getGson().toJson(record));
            EcOffer recordEcOffer = new EcOffer();
            recordEcOffer.setEcqlId(ecqlId);
            recordEcOffer.setAreaStr(areaStr);
            ecOffer = ecOfferService.getObject(recordEcOffer);
            if (ecOffer != null) {
                record.setEcoId(ecOffer.getEcoId());
                record.setSortId(ecOffer.getSortId());
                ecOfferService.update(record);
            } else {
                ecOfferService.insert(record);
            }
            dealDefaultWeightAndDefaultMoney(ecqlId, areaStr);// 修改默认重量和金额
        }
        status = 3;// 数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    // loadSteelbandThicknessAndSheathThickness 加载钢带和护套的厚度
    public void loadSteelBandThicknessAndSheathThickness() {
        EcOffer record = new EcOffer();
        record.setEcsId(2);// yjv
        List<EcOffer> list = ecOfferService.getList(record);
        ConductorComputeBo conductorObj;
        BigDecimal steelbandThickness = BigDecimal.ZERO;
        BigDecimal sheathThickness;
        for (EcOffer ecOffer : list) {
            conductorObj = EcableFunction.getConductorData(ecOffer);
            BigDecimal fireDiameter = conductorObj.getFireDiameter();
            BigDecimal zeroDiameter = conductorObj.getZeroDiameter();
            BigDecimal wideDiameter = fireDiameter// 粗芯直径
                    .add(ecOffer.getMicatapeThickness().multiply(new BigDecimal("2")))
                    .add(ecOffer.getInsulationFireThickness().multiply(new BigDecimal("2")));
            BigDecimal fineDiameter = zeroDiameter// 细芯直径
                    .add(ecOffer.getMicatapeThickness().multiply(new BigDecimal("2")))
                    .add(ecOffer.getInsulationZeroThickness().multiply(new BigDecimal("2")));
            String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
            BigDecimal externalDiameter = getExternalDiameter(areaArr, wideDiameter, fineDiameter);// 外径
            // log.info("externalDiameter + " + externalDiameter);
            if (externalDiameter.compareTo(new BigDecimal("0.03")) < 1) {
                steelbandThickness = new BigDecimal("0.0002");
            } else if (externalDiameter.compareTo(new BigDecimal("0.03")) > 0
                    && externalDiameter.compareTo(new BigDecimal("0.07")) < 1) {
                steelbandThickness = new BigDecimal("0.0005");
            } else if (externalDiameter.compareTo(new BigDecimal("0.07")) > 0) {
                steelbandThickness = new BigDecimal("0.0008");
            }
            // log.info("steelbandThickness + " + steelbandThickness);
            sheathThickness = externalDiameter
                    .add(steelbandThickness.multiply(new BigDecimal("2")))
                    .multiply(new BigDecimal("0.035"))
                    .add(new BigDecimal("0.001"));
            record.setEcoId(ecOffer.getEcoId());
            record.setSteelbandThickness(steelbandThickness);
            record.setSheathThickness(sheathThickness);
            record.setSheath22Thickness(sheathThickness);
            // log.info(CommonFunction.getGson().toJson(record));
            ecOfferService.update(record);
        }
    }


    // dealDefaultWeightAndDefaultMoney
    public void dealDefaultWeightAndDefaultMoney(Integer ecqlId, String areaStr) {
        EcOffer record = new EcOffer();
        record.setEcqlId(ecqlId);
        record.setAreaStr(areaStr);
        EcOffer ecOffer = ecOfferService.getObject(record);
        // 导体数据
        ConductorComputeExtendBo mapConductor = ecableEcOfferFunction.getConductorData(ecOffer);
        BigDecimal fireDiameter = mapConductor.getFireDiameter();//粗芯直径
        BigDecimal zeroDiameter = mapConductor.getZeroDiameter();//细芯直径
        BigDecimal conductorWeight = mapConductor.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = mapConductor.getConductorMoney();// 导体金额
        // 云母带数据
        MicaTapeComputeBo mapMicaTape = ecableEcOfferFunction.getMicaTapeData(ecOffer, fireDiameter, zeroDiameter);
        BigDecimal fireMicatapeRadius = mapMicaTape.getFireMicaTapeRadius();
        BigDecimal zeroMicaTapeRadius = mapMicaTape.getZeroMicaTapeRadius();
        BigDecimal micatapeWeight = mapMicaTape.getMicaTapeWeight();// 云母带重量
        BigDecimal micatapeMoney = mapMicaTape.getMicaTapeMoney();// 云母带金额
        // 绝缘数据
        InsulationComputeBo mapInsulation = ecableEcOfferFunction
                .getInsulationData(ecOffer, fireDiameter, zeroDiameter, fireMicatapeRadius, zeroMicaTapeRadius);
        BigDecimal insulationWeight = mapInsulation.getInsulationWeight();// 绝缘重量
        BigDecimal insulationMoney = mapInsulation.getInsulationMoney();// 绝缘金额
        // 填充物数据
        InfillingComputeBo mapInfilling = ecableEcOfferFunction.getInfillingData(ecOffer, fireDiameter, zeroDiameter);
        BigDecimal externalDiameter = mapInfilling.getExternalDiameter();
        BigDecimal infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
        BigDecimal infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额
        // 包带数据
        BagComputeBo mapBag = ecableEcOfferFunction.getBagData(ecOffer, externalDiameter);
        BigDecimal bagWeight = mapBag.getBagWeight();// 包带重量
        BigDecimal bagMoney = mapBag.getBagMoney();// 包带金额
        // 钢带数据
        SteelBandComputeBo mapSteelBand = ecableEcOfferFunction.getSteelBandData(ecOffer, externalDiameter);
        BigDecimal steelBandWeight = mapSteelBand.getSteelbandWeight();// 钢带重量
        BigDecimal steelBandMoney = mapSteelBand.getSteelbandMoney();// 钢带金额
        // 护套数据
        SheathComputeBo mapSheath = ecableEcOfferFunction.getSheathData(ecOffer, externalDiameter);
        BigDecimal sheathWeight = mapSheath.getSheathWeight();// 护套重量
        BigDecimal sheathMoney = mapSheath.getSheathMoney();// 护套金额
        BigDecimal defaultWeight = conductorWeight
                .add(micatapeWeight)
                .add(bagWeight)
                .add(sheathWeight)
                .add(insulationWeight)
                .add(infillingWeight)
                .add(steelBandWeight)
                .add(sheathWeight);
        BigDecimal defaultMoney = conductorMoney
                .add(micatapeMoney)
                .add(bagMoney)
                .add(sheathMoney)
                .add(insulationMoney)
                .add(infillingMoney)
                .add(steelBandMoney)
                .add(sheathMoney);
        record.setEcoId(ecOffer.getEcoId());
        record.setDefaultWeight(defaultWeight);
        record.setDefaultMoney(defaultMoney);
        // log.info("record + " + CommonFunction.getGson().toJson(record));
        ecOfferService.update(record);
    }

    public EcOfferVo getListAndCount(EcOfferListBo bo) {
        EcOffer record = new EcOffer();
        Boolean startType = bo.getStartType();
        Integer ecqulId = bo.getEcqlId();
        record.setStartType(startType);
        record.setEcqlId(ecqulId);
        List<EcOffer> list = ecOfferService.getList(record);
        //Long count = ecuOfferService.getCount(record);
        return new EcOfferVo(list, list.size());
    }

    public EcOffer getObject(EcOfferBaseBo bo) {
        EcOffer record = new EcOffer();
        record.setEcoId(bo.getEcoId());
        return ecOfferService.getObject(record);
    }

    public void start(List<EcOfferStartBo> bos) {
        for (EcOfferStartBo bo : bos) {
            Integer ecoId = bo.getEcoId();
            EcOffer record = new EcOffer();
            record.setEcoId(ecoId);
            record.setStartType(bo.getStartType());
            ecOfferService.update(record);
        }
    }

    public String saveOrUpdate(EcOfferInsertBo bo) {
        Integer ecoId = bo.getEcoId();
        EcOffer record = new EcOffer();
        String msg = "";
        if (ObjectUtil.isNull(ecoId)) {// 插入
            record.setEcoId(ecoId);
            record.setEcqlId(bo.getEcqlId());
            record.setAreaStr(bo.getAreaStr());
            EcOffer ecuOffer = ecOfferService.getObject(record);
            log.info(CommonFunction.getGson().toJson(record));
            if (ecuOffer != null) {
                throw new RuntimeException("截面积已占用");
            }
            Integer ecqulId = bo.getEcqlId();
            EcqLevel recordEcquLevel = new EcqLevel();
            recordEcquLevel.setEcqlId(ecqulId);
            EcqLevel ecquLevel = ecqLevelService.getObject(recordEcquLevel);
            Integer ecbcId = ecquLevel.getEcbcId();
            Boolean startType = false;
            int sortId = 1;
            ecuOffer = ecOfferService.getObject(record);
            if (ecuOffer != null) {
                sortId = ecuOffer.getSortId() + 1;
            }
            record.setEcqlId(ecqulId);// 电缆质量等级ID
            record.setStartType(startType);// 是否开启
            record.setSortId(sortId);// 排序
            record.setAreaStr(bo.getAreaStr());// 截面str
            record.setAddPercent(BigDecimal.ZERO);// 成本加点
            record.setFireSilkNumber(BigDecimal.ZERO);// 火丝丝号
            record.setFireRootNumber(0);// 粗芯根数
            record.setFireMembrance(0);// 粗芯过膜
            record.setFirePress(BigDecimal.ZERO);// 粗芯压型
            record.setZeroSilkNumber(BigDecimal.ZERO);// 细芯丝号
            record.setZeroRootNumber(0);// 细芯根数
            record.setZeroMembrance(0);// 细芯过膜
            record.setZeroPress(BigDecimal.ZERO);// 细芯过型
            record.setEcbiId(0);// 绝缘类型
            record.setInsulationFireThickness(BigDecimal.ZERO);// 粗芯绝缘厚度
            record.setInsulationZeroThickness(BigDecimal.ZERO);// 细芯绝缘厚度
            record.setEcbbId(0);// 包带类型
            record.setBagThickness(BigDecimal.ZERO);// 包带厚度
            record.setEcbShieldId(0);// 屏蔽类型
            record.setShieldThickness(BigDecimal.ZERO);// 屏蔽厚度
            record.setShieldPercent(BigDecimal.ZERO);// 屏蔽编织系数
            record.setEcbsbId(0);// 钢带类型
            record.setSteelbandThickness(BigDecimal.ZERO);// 钢带厚度
            record.setSteelbandStorey(0);// 钢带层数
            // record.setEcbusid(0);//护套类型
            record.setSheathThickness(BigDecimal.ZERO);// 护套厚度
            record.setSheath22Thickness(BigDecimal.ZERO);// 铠装护套厚度
            record.setEcbmId(0);// 云母带类型
            record.setMicatapeThickness(BigDecimal.ZERO);// 云母带厚度
            record.setFireStrand(BigDecimal.ZERO);// 粗芯绞合系数
            record.setZeroStrand(BigDecimal.ZERO);// 细芯绞合系数
            record.setEcbinId(0);// 填充物类型
            record.setEcbswId(0);// 钢丝类型
            record.setSteelwireMembrance(BigDecimal.ZERO);// 钢丝过膜
            record.setSteelwirePress(BigDecimal.ZERO);// 钢丝压型
            ecOfferService.insert(record);
            //loadArea(sysUser.getEcCompanyId(), ecqulId);// 加载质量等级对应的截面库ecuArea
            dealDefaultWeightAndDefaultMoney(record.getEcqlId(), record.getAreaStr());
            msg = "插入数据成功";
        } else {
            record.setEcoId(ecoId);
            record.setAddPercent(bo.getAddPercent());
            record.setAreaStr(bo.getAreaStr());// 截面str
            record.setFireSilkNumber(bo.getFireSilkNumber());// 粗芯丝号
            record.setFireRootNumber(bo.getFireRootNumber());// 粗芯根数
            record.setFireStrand(bo.getFireStrand());// 粗芯绞合系数
            record.setZeroSilkNumber(bo.getZeroSilkNumber());// 细芯丝号
            record.setZeroRootNumber(bo.getZeroRootNumber());// 细芯丝号
            record.setZeroStrand(bo.getZeroStrand());// 细芯绞合系数
            record.setEcbiId(bo.getEcbuiId());// 绝缘类型
            record.setInsulationFireThickness(bo.getInsulationFireThickness());// 粗芯绝缘厚度
            record.setInsulationZeroThickness(bo.getInsulationZeroThickness());// 细芯绝缘厚度
            record.setEcbbId(bo.getEcbbId());// 包带类型
            record.setBagThickness(bo.getBagThickness());// 包带厚度
            record.setEcbShieldId(bo.getEcbsid());// 屏蔽类型
            record.setShieldThickness(bo.getShieldThickness());// 屏蔽厚度
            record.setShieldPercent(bo.getShieldPercent());// 屏蔽编织系数
            record.setEcbsbId(bo.getEcbsbId());// 钢带类型
            record.setSteelbandThickness(bo.getSteelbandThickness());// 钢带厚度
            record.setSteelbandStorey(bo.getSteelbandStorey());// 钢带层数
            record.setEcbuSheathId(bo.getEcbsid());// 护套类型
            record.setSheathThickness(bo.getSheathThickness());// 护套厚度
            record.setSheath22Thickness(bo.getSheath22Thickness());// 护套厚度
            record.setEcbmId(bo.getEcbmId());// 云母带类型
            record.setMicatapeThickness(bo.getMicatapeThickness());// 云母带厚度
            record.setEcbinId(bo.getEcbinId());// 填充物类型
            ecOfferService.update(record);
            record = new EcOffer();
            record.setEcoId(ecoId);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            EcOffer ecuOffer = ecOfferService.getObject(record);
            dealDefaultWeightAndDefaultMoney(ecuOffer.getEcqlId(), ecuOffer.getAreaStr());
            msg = "数据更新成功";
        }
        return msg;
    }

    public void sort(List<EcOfferSortBo> bos) {
        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        for (EcOfferSortBo bo : bos) {
            Integer ecuoId = bo.getEcoId();
            Integer sortId = bo.getSortId();
            EcOffer record = new EcOffer();
            record.setEcoId(ecuoId);
            record.setSortId(sortId);
            ecOfferService.update(record);
        }
        //EcOffer ecuOffer = ecOfferService.getObject(record);
        //loadArea(sysUser.getEcCompanyId(), ecOffer.getEcqulId());// 加载质量等级对应的截面库ecuArea
    }

    public void delete(EcOfferBaseBo bo) {
        Integer ecoId = bo.getEcoId();
        EcOffer offer = new EcOffer();
        offer.setEcoId(ecoId);
        EcOffer object = ecOfferService.getObject(offer);
        if (ObjUtil.isNull(object)) {
            throw new RuntimeException("当前数据不存在");
        }
        //质量等级ID
        Integer ecqlId = object.getEcqlId();
        //查询小于此数组排序后的数组，用于重新排序
        Integer sortId = object.getSortId();
        //因为排序，所以将本质量等级下的所有排序都减一
        ecOfferService.reduceSort(ecqlId, sortId);
        //EcuOffer record = new EcuOffer();
        //record.setEcuoId(ecoId);
        //ecuOfferService.delete(record);
        //loadArea(sysUser.getEcCompanyId(), ecqlId);// 加载质量等级对应的截面库ecuArea
    }

    public ProgrammeVo getStructureData(EcOfferStructBo bo) {
        Integer ecoId = bo.getEcoId();
        String silkName = bo.getSilkName();
        EcOffer ecOffer = getObjectPassEcoId(ecoId);
        // 导体数据
        ConductorComputeExtendBo mapConductor = ecableEcOfferFunction.getConductorData(ecOffer);
        BigDecimal fireDiameter = mapConductor.getFireDiameter();
        BigDecimal zeroDiameter = mapConductor.getZeroDiameter();
        BigDecimal conductorWeight = mapConductor.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = mapConductor.getConductorMoney();// 导体金额
        // 云母带数据
        BigDecimal fireMicatapeRadius = BigDecimal.ZERO;
        BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;
        BigDecimal micatapeWeight = BigDecimal.ZERO;// 云母带重量
        BigDecimal micatapeMoney = BigDecimal.ZERO;// 云母带金额
        if (silkName.contains("N") || silkName.contains("NH")) {
            MicaTapeComputeBo micaTapeData = ecableEcOfferFunction.getMicaTapeData(ecOffer, fireDiameter, zeroDiameter);
            fireMicatapeRadius = micaTapeData.getFireMicaTapeRadius();
            zeroMicatapeRadius = micaTapeData.getZeroMicaTapeRadius();
            micatapeWeight = micaTapeData.getMicaTapeWeight();// 云母带重量
            micatapeMoney = micaTapeData.getMicaTapeMoney();// 云母带金额
        }
        // 绝缘数据
        InsulationComputeBo mapInsulation = ecableEcOfferFunction.getInsulationData(ecOffer, fireDiameter,
                zeroDiameter,
                fireMicatapeRadius,
                zeroMicatapeRadius);
        BigDecimal insulationWeight = mapInsulation.getInsulationWeight();// 绝缘重量
        BigDecimal insulationMoney = mapInsulation.getInsulationMoney();// 绝缘金额
        // 填充物数据
        InfillingComputeBo mapInfilling = ecableEcOfferFunction.getInfillingData(ecOffer, fireDiameter, zeroDiameter);
        BigDecimal externalDiameter = mapInfilling.getExternalDiameter();
        BigDecimal infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
        BigDecimal infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额
        // 包带数据
        BagComputeBo mapBag = ecableEcOfferFunction.getBagData(ecOffer, externalDiameter);
        BigDecimal bagWeight = mapBag.getBagWeight();// 包带重量
        BigDecimal bagMoney = mapBag.getBagMoney();// 包带金额
        // 钢带数据
        BigDecimal steelbandWeight = BigDecimal.ZERO;// 钢带重量
        BigDecimal steelbandMoney = BigDecimal.ZERO;// 钢带金额
        if (silkName.contains("22") || silkName.contains("23")) {
            // 钢带数据
            SteelBandComputeBo mapSteelband = ecableEcOfferFunction.getSteelBandData(ecOffer, externalDiameter);
            steelbandWeight = mapSteelband.getSteelbandWeight();// 钢带重量
            steelbandMoney = mapSteelband.getSteelbandMoney();// 钢带金额
        }

        // 护套数据
        SheathComputeBo mapSheath = ecableEcOfferFunction.getSheathData(ecOffer, externalDiameter);
        BigDecimal sheathWeight = mapSheath.getSheathWeight();// 护套重量
        BigDecimal sheathMoney = mapSheath.getSheathMoney();// 护套金额

        ProgrammeVo programmeVo = new ProgrammeVo();
        //导体
        programmeVo.setConductorWeight(conductorWeight);
        programmeVo.setConductorMoney(conductorMoney);
        //云母带
        programmeVo.setMicatapeWeight(micatapeWeight);
        programmeVo.setMicatapeMoney(micatapeMoney);
        //绝缘
        programmeVo.setInsulationWeight(insulationWeight);
        programmeVo.setInsulationMoney(insulationMoney);
        //填充物
        programmeVo.setInfillingWeight(infillingWeight);
        programmeVo.setInfillingMoney(infillingMoney);
        //包带
        programmeVo.setBagWeight(bagWeight);
        programmeVo.setBagMoney(bagMoney);
        //钢带
        programmeVo.setSteelBandWeight(steelbandWeight);
        programmeVo.setSteelBandMoney(steelbandMoney);
        //护套
        programmeVo.setSheathWeight(sheathWeight);
        programmeVo.setSheathMoney(sheathMoney);
        return programmeVo;
    }

    private EcOffer getObjectPassEcoId(Integer ecoId) {
        EcOffer record = new EcOffer();
        record.setEcoId(ecoId);
        return ecOfferService.getObject(record);
    }
}
