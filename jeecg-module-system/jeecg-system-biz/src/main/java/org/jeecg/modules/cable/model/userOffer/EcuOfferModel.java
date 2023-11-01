package org.jeecg.modules.cable.model.userOffer;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.userOffer.offer.vo.OfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.model.efficiency.EcdAreaModel;
import org.jeecg.modules.cable.model.quality.EcquLevelModel;
import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.model.userEcable.*;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import org.jeecg.modules.cable.service.quality.EcuAreaService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userOffer.EcuOfferService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.EcableEcuOfferFunction;
import org.jeecg.modules.cable.tools.ExcelUtils;
import org.jeecg.modules.cable.tools.StringTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuOfferModel {

    @Resource
    EcuOfferService ecuOfferService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcquLevelService ecquLevelService;
    @Resource
    EcbuInsulationModel ecbuInsulationModel;
    @Resource
    EcbuBagModel ecbuBagModel;
    @Resource
    EcbuShieldModel ecbuShieldModel;
    @Resource
    EcbuSteelbandModel ecbuSteelbandModel;
    @Resource
    EcbuSheathModel ecbuSheathModel;
    @Resource
    EcbuMicatapeModel ecbuMicatapeModel;
    @Resource
    EcbuInfillingModel ecbuInfillingModel;
    @Resource
    EcuAreaService ecuAreaService;
    @Resource
    EcdAreaModel ecdAreaModel;
    ExcelUtils excelUtils = new ExcelUtils();
    @Resource
    EcableEcuOfferFunction ecableEcuOfferFunction;
    @Resource
    EcquLevelModel ecquLevelModel;
    @Resource
    EcSilkModel ecSilkModel;// 丝型号
    @Resource
    EcuoCoreModel ecuoCoreModel;// 芯数
    @Resource
    EcuoAreaModel ecuoAreaModel;// 平方数
    @Resource
    EcuoProgrammeModel ecuoProgrammeModel;

    // importDeal
    @Transactional(rollbackFor = Exception.class)
    public void importDeal(MultipartFile file, Integer ecqulId) throws Exception {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

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
        String cableStrandStr = "";// 成缆绞合系数
        List<Map<String, Object>> listObject = new ArrayList<>();
        Integer i = 0;
        for (List<Object> objects : listob) {
            log.info("i + " + i);
            if (objects.size() < 10) {
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
            if (objects.size() >= 25) {
                infillingStr = objects.get(25).toString();// 填充物类型
                cableStrandStr = objects.get(26).toString();// 成缆绞合系数
            }
            mapObject.put("ecqulId", ecqulId);
            mapObject.put("addPercentStr", addPercentStr);
            mapObject.put("areaStr", areaStr);
            mapObject.put("fireSilkNumberStr", fireSilkNumberStr);
            mapObject.put("fireRootNumberStr", fireRootNumberStr);
            mapObject.put("fireStrandStr", fireStrandStr);
            mapObject.put("zeroSilkNumberStr", zeroSilkNumberStr);
            mapObject.put("zeroRootNumberStr", zeroRootNumberStr);
            mapObject.put("zeroStrandStr", zeroStrandStr);
            mapObject.put("insulationStr", insulationStr);
            log.info("insulationStr + " + insulationStr);
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
//        EcUser recordEcUser = new EcUser();
//        recordEcUser.setEcuId(ecuId);
//        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcuOffer record;
        Integer ii = 0;
        for (Map<String, Object> mapObject : listObject) {
            System.out.println(ii);
            // break;
            ii++;
            areaStr = mapObject.get("areaStr").toString();// 截面积
            addPercentStr = mapObject.get("addPercentStr").toString();
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
            // 成本加点
            BigDecimal addPercent = new BigDecimal("0");
            if (!"".equals(addPercentStr)) {
                addPercent = new BigDecimal(addPercentStr);
            }
            // log.info("add_percent + " + addPercent);
            BigDecimal fireSilkNumber = new BigDecimal(fireSilkNumberStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            // log.info("fireRootNumberStr + " + fireRootNumberStr);
            Integer fireRootNumber = Integer.parseInt(fireRootNumberStr);// 粗芯根数
            BigDecimal fireStrand = new BigDecimal("0");// 粗芯丝绞合系数
            // log.info("fireStrandStr + " + fireStrandStr);
            if (!"0".equals(fireStrandStr) && !"".equals(fireStrandStr)) {
                fireStrand = new BigDecimal(fireStrandStr);
            }
            BigDecimal zeroSilkNumber = new BigDecimal("0");
            if (!"".equals(zeroSilkNumberStr)) {
                zeroSilkNumber = new BigDecimal(zeroSilkNumberStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
            }
            Integer zeroRootNumber = 0;
            if (!"".equals(zeroRootNumberStr)) {
                zeroRootNumber = Integer.parseInt(fireRootNumberStr);
            }
            BigDecimal zeroStrand = new BigDecimal("0");// 细芯丝绞合系数
            if (!"0".equals(zeroStrandStr) && !"".equals(zeroStrandStr)) {
                zeroStrand = new BigDecimal(zeroStrandStr);
            }
            Integer ecbuiId = 0;// 绝缘
            BigDecimal insulationFireThickness = new BigDecimal("0");
            if (!"0".equals(insulationFireThicknessStr) && !"".equals(insulationFireThicknessStr)) {
                insulationFireThickness = new BigDecimal(insulationFireThicknessStr)
                        .divide(new BigDecimal("1000"), 18, RoundingMode.HALF_UP);
            }
            BigDecimal insulationZeroThickness = new BigDecimal("0");
            if (!"0".equals(insulationZeroThicknessStr) && !"".equals(insulationZeroThicknessStr)) {
                insulationZeroThickness = new BigDecimal(insulationZeroThicknessStr)
                        .divide(new BigDecimal("1000"), 18, RoundingMode.HALF_UP);
            }
            EcbuInsulation ecbuInsulation = ecbuInsulationModel.getInsulationPassInsulationStr(ecuId, insulationStr);
            if (ecbuInsulation != null) {
                ecbuiId = ecbuInsulation.getEcbuiId();
            } else {
                ecbuInsulation = ecbuInsulationModel.getInsulationPassFullName(ecuId, insulationStr);
                if (ecbuInsulation != null) {
                    ecbuiId = ecbuInsulation.getEcbuiId();
                }
            }
            // 包带
            Integer ecbubId = 0;
            EcbuBag ecbuBag = ecbuBagModel.getObjectPassBagStr(ecuId, bagStr);
            if (ecbuBag != null) {
                ecbubId = ecbuBag.getEcbubId();
            }
            BigDecimal bagThickness = new BigDecimal("0");
            if (!"".equals(bagThicknessStr)) {
                bagThickness = new BigDecimal(bagThicknessStr)
                        .divide(new BigDecimal("1000"), 6, RoundingMode.HALF_UP);
            }
            // 铠装包带包带
            Integer ecbub22Id = 0;
            EcbuBag ecbu22Bag = ecbuBagModel.getObjectPassBagStr(ecuId, bag22Str);
            if (ecbu22Bag != null) {
                ecbub22Id = ecbu22Bag.getEcbubId();
            }
            BigDecimal bag22Thickness = new BigDecimal("0");
            // log.info("bag22ThicknessStr + " + bag22ThicknessStr);
            if (!"".equals(bag22ThicknessStr)) {
                bag22Thickness = new BigDecimal(bag22ThicknessStr)
                        .divide(new BigDecimal("1000"), 6, RoundingMode.HALF_UP);
            }
            // log.info("bag22Thickness + " + bag22Thickness);
            // 屏蔽
            Integer ecbusId = 0;
            EcbuShield ecbuShield = ecbuShieldModel.getObjectPassShieldStr(ecuId, shieldStr);
            if (ecbuShield != null) {
                ecbusId = ecbuShield.getEcbusId();
            }
            BigDecimal shieldThickness = new BigDecimal(0);
            if (!"".equals(shieldThicknessStr) && !"0".equals(shieldThicknessStr)) {
                shieldThickness = new BigDecimal(shieldThicknessStr)
                        .divide(new BigDecimal("1000"), 6, RoundingMode.HALF_UP);
            }
            BigDecimal shieldPercent = new BigDecimal(0);
            shieldPercentStr = shieldPercentStr.replace("%", "");
            if (!shieldPercentStr.isEmpty() && !"0".equals(shieldPercentStr)) {
                shieldPercent = new BigDecimal(shieldPercentStr);
            }
            // 钢带
            Integer ecbusbId = 0;
            EcbuSteelband ecbuSteelband = ecbuSteelbandModel.getObjectPassSteelbandStr(ecuId, steelbandStr);
            if (ecbuSteelband != null) {
                ecbusbId = ecbuSteelband.getEcbusId();
            }
            BigDecimal steelbandThickness = new BigDecimal(0);
            if (!"".equals(steelbandThicknessStr)) {
                steelbandThickness = new BigDecimal(steelbandThicknessStr).divide(new BigDecimal("1000"), 6, RoundingMode.HALF_UP);
            }
            Integer steelbandStorey = 0;
            if (!"".equals(steelbandStoreyStr) && !"0".equals(steelbandStoreyStr)) {
                steelbandStorey = Integer.parseInt(steelbandStoreyStr);
            }
            // 护套
            Integer ecbusid = 0;
            EcbuSheath ecbuSheath = ecbuSheathModel.getObjectPassSheathStr(ecuId, sheathStr);
            if (ecbuSheath != null) {
                ecbusid = ecbuSheath.getEcbusId();
            }
            // log.info("ecbusid + " + ecbusid);
            BigDecimal sheathThickness = new BigDecimal("0");
            BigDecimal sheath22Thickness = new BigDecimal("0");// 铠装
            if (!"".equals(sheathThicknessStr)) {
                sheathThickness = new BigDecimal(sheathThicknessStr).divide(new BigDecimal("1000"), 6, RoundingMode.HALF_UP);
            }
            if (!"".equals(sheath22ThicknessStr)) {
                sheath22Thickness = new BigDecimal(sheath22ThicknessStr)
                        .divide(new BigDecimal("1000"), 6, RoundingMode.HALF_UP);
            }
            // 云母带
            Integer ecbumId = 0;
            EcbuMicatape ecbuMicatape = ecbuMicatapeModel.getObjectPassMicatapeStr(ecuId);
            if (ecbuMicatape != null) {
                ecbumId = ecbuMicatape.getEcbumId();
            }
            BigDecimal micatapeThickness = new BigDecimal("0");
            if (!"".equals(micatapeThicknessStr)) {
                micatapeThickness = new BigDecimal(micatapeThicknessStr)
                        .divide(new BigDecimal("1000"), 6, RoundingMode.HALF_UP);
            }
            // 填充物
            Integer ecbuinId = 0;
            // log.info("infillingStr + " + infillingStr);
            EcbuInfilling ecbuInfilling = ecbuInfillingModel.getObjectPassInfillingStr(ecuId, infillingStr);
            if (ecbuInfilling != null) {
                ecbuinId = ecbuInfilling.getEcbuiId();
            }
            // 成缆绞合系数
            BigDecimal cableStrand = new BigDecimal("0");
            if (!"".equals(cableStrandStr)) {
                cableStrand = new BigDecimal(cableStrandStr);
            }
            // 插入
            EcquLevel recordEcquLevel = new EcquLevel();
            recordEcquLevel.setEcqulId(ecqulId);
            EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
            Integer ecbucId = ecquLevel.getEcbucId();
            Boolean startType = true;
            Integer sortId = 1;
            record = new EcuOffer();
            record.setEcqulId(Integer.valueOf(ecqulId));
            EcuOffer ecuOffer = ecuOfferService.getObject(record);
            if (ecuOffer != null) {
                sortId = ecuOffer.getSortId() + 1;
            }
            Integer fireMembrance = 0;// 粗芯过膜
            BigDecimal firePress = new BigDecimal("0");// 粗芯压型
            Integer zeroMembrance = 0;// 细芯过膜
            BigDecimal zeroPress = new BigDecimal("0");// 细芯压型
            Integer ecbuswId = 0;// 钢丝类型
            BigDecimal steelwireMembrance = new BigDecimal("0");// 钢丝过膜
            BigDecimal steelwirePress = new BigDecimal("0");// 钢丝压型
            record = new EcuOffer();
            record.setEcqulId(ecqulId);
            record.setAreaStr(areaStr);
            ecuOffer = ecuOfferService.getObject(record);
            record.setEcqulId(ecqulId);// 电缆质量等级ID
            record.setEcbucId(ecbucId);// 导体ID
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(startType);// 是否开启
            record.setSortId(sortId);// 排序
            record.setAddPercent(addPercent);
            record.setAreaStr(areaStr);// 截面str
            record.setFireSilkNumber(fireSilkNumber);// 粗芯丝号
            record.setFireRootNumber(fireRootNumber);// 粗芯根数
            record.setFireMembrance(fireMembrance);// 粗芯过膜
            record.setFirePress(firePress);// 粗芯压型
            record.setZeroSilkNumber(zeroSilkNumber);// 细芯丝号
            record.setZeroRootNumber(zeroRootNumber);// 粗芯根数
            record.setZeroMembrance(zeroMembrance);// 细芯过膜
            record.setZeroPress(zeroPress);// 细芯过型
            record.setEcbuiId(ecbuiId);// 绝缘类型
            record.setInsulationFireThickness(insulationFireThickness);// 粗芯绝缘厚度
            record.setInsulationZeroThickness(insulationZeroThickness);// 细芯绝缘厚度
            record.setEcbubId(ecbubId);// 包带类型
            record.setBagThickness(bagThickness);// 包带厚度
            record.setEcbub22Id(ecbub22Id);// 铠装包带类型
            record.setBag22Thickness(bag22Thickness);// 铠装包带厚度
            record.setEcbuShieldId(ecbusId);// 屏蔽类型
            record.setShieldThickness(shieldThickness);// 屏蔽厚度
            record.setShieldPercent(shieldPercent);// 屏蔽编织系数
            record.setEcbusbId(ecbusbId);// 钢带类型
            record.setSteelbandThickness(steelbandThickness);// 钢带厚度
            record.setSteelbandStorey(steelbandStorey);// 钢带层数
            record.setEcbuSheathId(ecbusid);// 护套类型
            record.setSheathThickness(sheathThickness);// 护套厚度
            record.setSheath22Thickness(sheath22Thickness);// 铠装护套厚度
            record.setEcbumId(ecbumId);// 云母带类型
            record.setMicatapeThickness(micatapeThickness);// 云母带厚度
            record.setFireStrand(fireStrand);// 粗芯绞合系数
            record.setZeroStrand(zeroStrand);// 细芯绞合系数
            record.setEcbuinId(ecbuinId);// 填充物类型
            record.setEcbuswId(ecbuswId);// 钢丝类型
            record.setSteelwireMembrance(steelwireMembrance);// 钢丝过膜
            record.setSteelwirePress(steelwirePress);// 钢丝压型
            record.setCableStrand(cableStrand);// 成缆绞合系数
            record.setDefaultWeight(new BigDecimal("0"));// 默认重量
            record.setDefaultMoney(new BigDecimal("0"));// 默认金额
            // log.info("record + " + CommonFunction.getGson().toJson(record));
            if (ecuOffer != null) {
                record.setEcuoId(ecuOffer.getEcuoId());
                record.setSortId(ecuOffer.getSortId());
                ecuOfferService.update(record);
            } else {
                ecuOfferService.insert(record);
            }
            dealDefaultWeightAndDefaultMoney(ecqulId, areaStr);// 修改默认重量和金额
            ecuoCoreModel.deal(ecqulId, areaStr);// 添加芯数表
            ecuoAreaModel.load(ecqulId, areaStr);// 添加平方数表
        }
        loadArea(ecuId, ecqulId);// 加载质量等级对应的截面库ecuArea
    }

    // loadArea 加载质量等级对应的截面库ecuArea
    public void loadArea(Integer ecuId, Integer ecqulId) {
        ecuAreaService.deletePassEcqulId(ecqulId);
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);

        EcuOffer record = new EcuOffer();
        record.setEcqulId(ecqulId);
        record.setStartType(true);
        List<EcuOffer> list = ecuOfferService.getList(record);
        EcuArea recordArea = new EcuArea();
        recordArea.setEcqulId(ecqulId);
        recordArea.setEcCompanyId(ecUser.getEcCompanyId());
        for (EcuOffer ecuOffer : list) {
            String areaStr = ecuOffer.getAreaStr();
            Integer sortId = 1;
            EcuArea ecuArea = ecuAreaService.getLatestObject(recordArea);
            if (ecuArea != null) {
                sortId = ecuArea.getSortId() + 1;
            }
            recordArea.setEcCompanyId(ecUser.getEcCompanyId());
            recordArea.setEcqulId(ecqulId);
            recordArea.setStartType(true);
            recordArea.setSortId(sortId);
            recordArea.setAreaStr(areaStr);
            recordArea.setEffectTime(System.currentTimeMillis());
            ecuAreaService.insert(recordArea);
        }
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(true);
        List<EcuArea> listArea = ecuAreaService.getList(recordArea);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(listArea));
        ecdAreaModel.deal(ecUser.getEcCompanyId(), ecqulId, txtList);
    }

    // 根据EcuqInput提供的数据返回EcuOffer
    public EcuOffer getOfferPassEcuqInput(EcuqInput ecuqInput) {
        EcuOffer ecuOffer = null;
        Integer ecqulId = 0;
        if (ecuqInput.getEcqulId() != 0) {
            ecqulId = ecuqInput.getEcqulId();
        }
        Integer storeId = 0;
        if (ecuqInput.getStoreId() != 0) {
            storeId = ecuqInput.getStoreId();
        }
        String areaStr = ecuqInput.getAreaStr();
        // log.info("areaStr + " + areaStr);
        if (!(ecqulId == 0
                || storeId == 0
                || "".equals(areaStr))) {
            EcuOffer record = new EcuOffer();
            record.setEcqulId(ecqulId);
            record.setAreaStr(areaStr);
            // log.info("record + " + CommonFunction.getGson().toJson(record));
            EcuOffer object = ecuOfferService.getObject(record);
            if (object != null) {
                ecuOffer = object;
            }
        }
        return ecuOffer;
    }

    // getListAndCount
    public OfferVo getListAndCount(OfferListBo bo) {
        EcuOffer record = new EcuOffer();
        Boolean startType = bo.getStartType();
        Integer ecqulId = bo.getEcqulId();
        record.setStartType(startType);
        record.setEcqulId(ecqulId);
        List<EcuOffer> list = ecuOfferService.getList(record);
//        for (EcuOffer ecuOffer : list) {
//            // 导体数据
//            Map<String, Object> mapConductor = ecableEcuOfferFunction.getConductorData(ecuOffer);
//            BigDecimal fireDiameter = new BigDecimal(mapConductor.get("fireDiameter").toString());
//            BigDecimal zeroDiameter = new BigDecimal(mapConductor.get("zeroDiameter").toString());
//            BigDecimal conductorWeight = new BigDecimal(mapConductor.get("conductorWeight").toString());
//            BigDecimal conductorMoney = new BigDecimal(mapConductor.get("conductorMoney").toString());
//            // 云母带数据
//            Map<String, Object> mapMicatape = ecableEcuOfferFunction.getMicatapeData(ecuOffer, fireDiameter, zeroDiameter);
//            BigDecimal fireMicatapeRadius = new BigDecimal(mapMicatape.get("fireMicatapeRadius").toString());
//            BigDecimal zeroMicatapeRadius = new BigDecimal(mapMicatape.get("zeroMicatapeRadius").toString());
//            BigDecimal micatapeWeight = new BigDecimal(mapMicatape.get("micatapeWeight").toString());// 云母带重量
//            BigDecimal micatapeMoney = new BigDecimal(mapMicatape.get("micatapeMoney").toString());// 云母带金额
//            // 绝缘数据
//            Map<String, Object> mapInsulation = ecableEcuOfferFunction
//                    .getInsulationData(ecuOffer, fireDiameter,
//                            zeroDiameter,
//                            fireMicatapeRadius,
//                            zeroMicatapeRadius);
//            BigDecimal insulationWeight = new BigDecimal(mapInsulation.get("insulationWeight").toString());// 绝缘重量
//            BigDecimal insulationMoney = new BigDecimal(mapInsulation.get("insulationMoney").toString());// 绝缘金额
//            // 填充物数据
//            Map<String, Object> mapInfilling = ecableEcuOfferFunction
//                    .getInfillingData(ecuOffer, fireDiameter, zeroDiameter);
//            BigDecimal externalDiameter = new BigDecimal(mapInfilling.get("externalDiameter").toString());//导体外径
//            BigDecimal infillingWeight = new BigDecimal(mapInfilling.get("infillingWeight").toString());// 填充物重量
//            BigDecimal infillingMoney = new BigDecimal(mapInfilling.get("infillingMoney").toString());// 填充物金额
//            // 包带数据
//            Map<String, Object> mapBag = ecableEcuOfferFunction.getBagData(ecuOffer, externalDiameter);
//            BigDecimal bagWeight = new BigDecimal(mapBag.get("bagWeight").toString());// 包带重量
//            BigDecimal bagMoney = new BigDecimal(mapBag.get("bagMoney").toString());// 包带金额
//            // 钢带数据
//            Map<String, Object> mapSteelband = ecableEcuOfferFunction
//                    .getSteelbandData(ecuOffer, externalDiameter);
//            BigDecimal steelbandWeight = new BigDecimal(mapSteelband.get("steelbandWeight").toString());// 钢带重量
//            BigDecimal steelbandMoney = new BigDecimal(mapSteelband.get("steelbandMoney").toString());// 钢带金额
//            // 护套数据
//            Map<String, Object> mapSheath = ecableEcuOfferFunction.getSheathData(ecuOffer, externalDiameter);
//            BigDecimal sheathWeight = new BigDecimal(mapSheath.get("sheathWeight").toString());// 护套重量
//            BigDecimal sheathMoney = new BigDecimal(mapSheath.get("sheathMoney").toString());// 护套金额
//            BigDecimal defaultWeight = conductorWeight
//                    .add(micatapeWeight)
//                    .add(bagWeight)
//                    .add(sheathWeight)
//                    .add(insulationWeight)
//                    .add(infillingWeight)
//                    .add(steelbandWeight)
//                    .add(sheathWeight);
//            BigDecimal defaultMoney = conductorMoney
//                    .add(micatapeMoney)
//                    .add(bagMoney)
//                    .add(sheathMoney)
//                    .add(insulationMoney)
//                    .add(infillingMoney)
//                    .add(steelbandMoney)
//                    .add(sheathMoney);
//            ecuOffer.setDefaultWeight(defaultWeight);
//            ecuOffer.setDefaultMoney(defaultMoney);
//            record = new EcuOffer();
//            record.setEcuoId(ecuOffer.getEcuoId());
//            record.setDefaultWeight(defaultWeight);
//            record.setDefaultMoney(defaultMoney);
//            ecuOfferService.update(record);
//        }
        long count = ecuOfferService.getCount(record);
        return new OfferVo(list, count, record);
    }

    // getObject
    public EcuOffer getObject(OfferBo bo) {
        EcuOffer record = new EcuOffer();
        record.setEcuoId(bo.getEcqulId());
        return ecuOfferService.getObject(record);
    }

    // start
    public String start(OfferStartBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecuoId = bo.getEcuoId();
        EcuOffer record = new EcuOffer();
        record.setEcuoId(ecuoId);
        EcuOffer ecuOffer = ecuOfferService.getObject(record);
        Boolean startType = ecuOffer.getStartType();
        Boolean startType1 = bo.getStartType();

        String msg = "";
        if (startType1 != null) {
            if (!"0".equals(startType1)) {
                startType = !"2".equals(startType1);
            }
            if (!startType) {

                msg = "数据禁用成功";
            } else {

                msg = "数据启用成功";
            }
        } else {
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
        }
        record = new EcuOffer();
        record.setEcuoId(ecuOffer.getEcuoId());
        record.setStartType(startType);
        ecuOfferService.update(record);
        loadArea(ecUser.getEcuId(), ecuOffer.getEcqulId());// 加载质量等级对应的截面库ecuArea
        return msg;
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(OfferInsertBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecuId = ecUser.getEcuId();
        Integer ecuoId = bo.getEcuoId();

        EcuOffer record = new EcuOffer();
        String msg = "";
        if (ObjectUtil.isNull(ecuoId)) {// 插入
            record.setEcuoId(ecuoId);
            record.setEcqulId(bo.getEcqulId());
            record.setAreaStr(bo.getAreaStr());
            EcuOffer ecuOffer = ecuOfferService.getObject(record);
            log.info(CommonFunction.getGson().toJson(record));
            if (ecuOffer != null) {
                msg = "截面积已占用";
            } else {
                Integer ecqulId = bo.getEcqulId();
                EcquLevel recordEcquLevel = new EcquLevel();
                recordEcquLevel.setEcqulId(ecqulId);
                EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
                Integer ecbucId = ecquLevel.getEcbucId();
                Boolean startType = false;
                Integer sortId = 1;
                ecuOffer = ecuOfferService.getObject(record);
                if (ecuOffer != null) {
                    sortId = ecuOffer.getSortId() + 1;
                }
                record.setEcqulId(ecqulId);// 电缆质量等级ID
                record.setEcbucId(ecbucId);// 导体ID
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setStartType(startType);// 是否开启
                record.setSortId(sortId);// 排序
                record.setAreaStr(bo.getAreaStr());// 截面str
                record.setAddPercent(new BigDecimal("0"));// 成本加点
                record.setFireSilkNumber(new BigDecimal("0"));// 火丝丝号
                record.setFireRootNumber(0);// 粗芯根数
                record.setFireMembrance(0);// 粗芯过膜
                record.setFirePress(new BigDecimal("0"));// 粗芯压型
                record.setZeroSilkNumber(new BigDecimal("0"));// 细芯丝号
                record.setZeroRootNumber(0);// 细芯根数
                record.setZeroMembrance(0);// 细芯过膜
                record.setZeroPress(new BigDecimal("0"));// 细芯过型
                record.setEcbuiId(0);// 绝缘类型
                record.setInsulationFireThickness(new BigDecimal("0"));// 粗芯绝缘厚度
                record.setInsulationZeroThickness(new BigDecimal("0"));// 细芯绝缘厚度
                record.setEcbubId(0);// 包带类型
                record.setBagThickness(new BigDecimal("0"));// 包带厚度
                record.setEcbuShieldId(0);// 屏蔽类型
                record.setShieldThickness(new BigDecimal("0"));// 屏蔽厚度
                record.setShieldPercent(new BigDecimal("0"));// 屏蔽编织系数
                record.setEcbusbId(0);// 钢带类型
                record.setSteelbandThickness(new BigDecimal("0"));// 钢带厚度
                record.setSteelbandStorey(0);// 钢带层数
                // record.setEcbusid(0);//护套类型
                record.setSheathThickness(new BigDecimal("0"));// 护套厚度
                record.setSheath22Thickness(new BigDecimal("0"));// 铠装护套厚度
                record.setEcbumId(0);// 云母带类型
                record.setMicatapeThickness(new BigDecimal("0"));// 云母带厚度
                record.setFireStrand(new BigDecimal("0"));// 粗芯绞合系数
                record.setZeroStrand(new BigDecimal("0"));// 细芯绞合系数
                record.setEcbuinId(0);// 填充物类型
                record.setEcbuswId(0);// 钢丝类型
                record.setSteelwireMembrance(new BigDecimal("0"));// 钢丝过膜
                record.setSteelwirePress(new BigDecimal("0"));// 钢丝压型
                ecuOfferService.insert(record);
                loadArea(ecuId, ecqulId);// 加载质量等级对应的截面库ecuArea
                dealDefaultWeightAndDefaultMoney(record.getEcqulId(), record.getAreaStr());
                msg = "插入数据成功";
            }
        } else {
            record.setEcuoId(ecuoId);
            if (bo.getAddPercent() != null) {
                record.setAddPercent(bo.getAddPercent());
            }
            if (bo.getAreaStr() != null) {
                record.setAreaStr(bo.getAreaStr());// 截面str
            }
            if (bo.getFireSilkNumber() != null) {
                record.setFireSilkNumber(bo.getFireSilkNumber());// 粗芯丝号
            }
            if (bo.getFireRootNumber() != null) {
                record.setFireRootNumber(bo.getFireRootNumber());// 粗芯根数
            }
            if (bo.getFireStrand() != null) {
                record.setFireStrand(bo.getFireStrand());// 粗芯绞合系数
            }
            if (bo.getZeroSilkNumber() != null) {
                record.setZeroSilkNumber(bo.getZeroSilkNumber());// 细芯丝号
            }
            if (bo.getZeroRootNumber() != null) {
                record.setZeroRootNumber(bo.getZeroRootNumber());// 细芯丝号
            }
            if (bo.getZeroStrand() != null) {
                record.setZeroStrand(bo.getZeroStrand());// 细芯绞合系数
            }
            if (bo.getEcbuiId() != null) {
                record.setEcbuiId(bo.getEcbuiId());// 绝缘类型
            }
            if (bo.getInsulationFireThickness() != null) {
                record.setInsulationFireThickness(bo.getInsulationFireThickness());// 粗芯绝缘厚度
            }
            if (bo.getInsulationZeroThickness() != null) {
                record.setInsulationZeroThickness(bo.getInsulationZeroThickness());// 细芯绝缘厚度
            }
            if (bo.getEcbubId() != null) {
                record.setEcbubId(bo.getEcbubId());// 包带类型
            }
            if (bo.getBagThickness() != null) {
                record.setBagThickness(bo.getBagThickness());// 包带厚度
            }
            if (bo.getEcbusid() != null) {
                record.setEcbuShieldId(bo.getEcbusid());// 屏蔽类型
            }
            if (bo.getShieldThickness() != null) {
                record.setShieldThickness(bo.getShieldThickness());// 屏蔽厚度
            }
            if (bo.getShieldPercent() != null) {
                record.setShieldPercent(bo.getShieldPercent());// 屏蔽编织系数
            }
            if (bo.getEcbusbId() != null) {
                record.setEcbusbId(bo.getEcbusbId());// 钢带类型
            }
            if (bo.getSteelbandThickness() != null) {
                record.setSteelbandThickness(bo.getSteelbandThickness());// 钢带厚度
            }
            if (bo.getSteelbandStorey() != null) {
                record.setSteelbandStorey(bo.getSteelbandStorey());// 钢带层数
            }
            if (bo.getEcbusid() != null) {
                record.setEcbuSheathId(bo.getEcbusid());// 护套类型
            }
            if (bo.getSheathThickness() != null) {
                record.setSheathThickness(bo.getSheathThickness());// 护套厚度
            }
            if (bo.getSheath22Thickness() != null) {
                record.setSheath22Thickness(bo.getSheath22Thickness());// 护套厚度
            }
            if (bo.getEcbumId() != null) {
                record.setEcbumId(bo.getEcbumId());// 云母带类型
            }
            if (bo.getMicatapeThickness() != null) {
                record.setMicatapeThickness(bo.getMicatapeThickness());// 云母带厚度
            }
            if (bo.getEcbuinId() != null) {
                record.setEcbuinId(bo.getEcbuinId());// 填充物类型
            }
            ecuOfferService.update(record);
            record = new EcuOffer();
            record.setEcuoId(ecuoId);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            EcuOffer ecuOffer = ecuOfferService.getObject(record);
            dealDefaultWeightAndDefaultMoney(ecuOffer.getEcqulId(), ecuOffer.getAreaStr());
            msg = "数据更新成功";
        }
        return msg;
    }

    // sort
    public void sort(OfferBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        Integer ecuoId = bo.getEcuoId();
        Integer sortId = bo.getSortId();

        EcuOffer record = new EcuOffer();
        record.setEcuoId(ecuoId);
        record.setSortId(sortId);
        ecuOfferService.update(record);
        record = new EcuOffer();
        record.setEcuoId(ecuoId);
        EcuOffer ecuOffer = ecuOfferService.getObject(record);
        loadArea(ecuId, ecuOffer.getEcqulId());// 加载质量等级对应的截面库ecuArea
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(OfferBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        Integer ecuoId = bo.getEcuoId();
        EcuOffer record = new EcuOffer();
        record.setEcuoId(ecuoId);
        EcuOffer ecuOffer = ecuOfferService.getObject(record);
        Integer sortId = ecuOffer.getSortId();
        record = new EcuOffer();
        record.setSortId(sortId);
        record.setEcqulId(ecuOffer.getEcqulId());
        List<EcuOffer> list = ecuOfferService.getList(record);
        Integer ecuo_id;
        for (EcuOffer ecu_offer : list) {
            ecuo_id = ecu_offer.getEcuoId();
            sortId = ecu_offer.getSortId() - 1;
            record.setEcuoId(ecuo_id);
            record.setSortId(sortId);
            ecuOfferService.update(record);
        }
        record = new EcuOffer();
        record.setEcuoId(ecuoId);
        ecuOfferService.delete(record);
        record = new EcuOffer();
        record.setEcuoId(ecuoId);
        ecuOffer = ecuOfferService.getObject(record);
        loadArea(ecuId, ecuOffer.getEcqulId());// 加载质量等级对应的截面库ecuArea
    }

    // getEcSilkPassEcqulId
    public EcSilk getEcSilkPassEcqulId(SilkBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcquLevel ecquLevel = ecquLevelModel.getObjectPassEcqulId(ecqulId);
        Integer ecsId = ecquLevel.getEcsId();
        return ecSilkModel.getObjectPassEcsId(ecsId);
    }

    // export 导出数据
    public void exportData(HttpServletResponse response, Integer ecqulId) throws IOException {
        log.info("h1");
        EcquLevel recordEcquLevel = new EcquLevel();
        recordEcquLevel.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
        String excelName = ecquLevel.getName();
        HSSFWorkbook wb = new HSSFWorkbook();// 创建excel文件
        HSSFSheet sheet = wb.createSheet(excelName); // 创建sheet页
        HSSFCellStyle cellStyle = wb.createCellStyle(); // 设置表格属性
        HSSFFont font = wb.createFont();
        font.setFontName("仿宋_GB2312");
        font.setFontHeightInPoints((short) 14);  // 字体大小
        font.setFontHeight((short) 22);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.setHorizontallyCenter(true);
        sheet.setVerticallyCenter(true);
        log.info("h2");
        for (Integer i = 0; i < 21; i++) {
            if (i == 0) {// 成本加点
                sheet.setColumnWidth(i, 3200); // 设置列宽
            } else if (i == 1) {// 截面积
                sheet.setColumnWidth(i, 3200); // 设置列宽
            } else {
                sheet.setColumnWidth(i, 6400); // 设置列宽
            }
            sheet.setDefaultColumnStyle(i, cellStyle);
        }
        // 创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("截面积");
        titleRow.createCell(1).setCellValue("生产利润点");
        titleRow.createCell(2).setCellValue("粗芯丝号");
        titleRow.createCell(3).setCellValue("粗芯丝数");
        titleRow.createCell(4).setCellValue("粗芯丝绞合系数");
        titleRow.createCell(5).setCellValue("细芯丝号");
        titleRow.createCell(6).setCellValue("细芯丝数");
        titleRow.createCell(7).setCellValue("细芯丝绞合系数");
        titleRow.createCell(8).setCellValue("绝缘类型");
        titleRow.createCell(9).setCellValue("粗芯绝缘厚度");
        titleRow.createCell(10).setCellValue("细芯绝缘厚度");
        titleRow.createCell(11).setCellValue("非铠装绕包带类型");
        titleRow.createCell(12).setCellValue("非铠装绕包带厚度");
        titleRow.createCell(13).setCellValue("铠装绕包带类型");
        titleRow.createCell(14).setCellValue("铠装绕包带厚度");
        titleRow.createCell(15).setCellValue("屏蔽类型");
        titleRow.createCell(16).setCellValue("屏蔽厚度");
        titleRow.createCell(17).setCellValue("屏蔽编织密度");
        titleRow.createCell(18).setCellValue("钢带类型");
        titleRow.createCell(19).setCellValue("钢带厚度");
        titleRow.createCell(20).setCellValue("钢带层数");
        titleRow.createCell(21).setCellValue("护套类型");
        titleRow.createCell(22).setCellValue("护套厚度");
        titleRow.createCell(23).setCellValue("铠装护套厚度");
        titleRow.createCell(24).setCellValue("云母带厚度");
        titleRow.createCell(25).setCellValue("填充物类型");
        titleRow.createCell(26).setCellValue("成缆系数");
        titleRow.setHeight((short) 400);
        EcuOffer recordEcuOffer = new EcuOffer();
        recordEcuOffer.setEcqulId(ecqulId);
        List<EcuOffer> list = ecuOfferService.getList(recordEcuOffer);
        log.info("h3");
        Integer sortId = 1;
        for (EcuOffer ecuOffer : list) {
            if (sortId < list.size()) {
                log.info("sortId + " + sortId);
                String addPercentStr = (ecuOffer.getAddPercent().multiply(new BigDecimal("100"))).setScale(0, RoundingMode.HALF_UP).toString();// 成本加点
                if (ecuOffer.getAddPercent().compareTo(new BigDecimal("0")) == 0) {
                    addPercentStr = "0%";
                } else {
                    addPercentStr = addPercentStr + "%";
                }
                String areaStr = ecuOffer.getAreaStr();// 截面积
                String fireSilkNumberStr = ecuOffer.getFireSilkNumber()
                        .multiply(new BigDecimal("1000")).toString();// 粗芯丝号
                String fireRootNumberStr = String.valueOf(ecuOffer.getFireRootNumber());// 粗芯丝数
                String fireStrandStr = String.valueOf(ecuOffer.getFireStrand());// 粗芯丝数
                if (ecuOffer.getFireSilkNumber().compareTo(new BigDecimal("0")) == 0) {
                    fireSilkNumberStr = "";
                    fireRootNumberStr = "";
                    fireStrandStr = "";
                }
                String zeroSilkNumberStr = ecuOffer.getZeroSilkNumber()
                        .multiply(new BigDecimal("1000")).toString();// 细芯丝号
                String zeroRootNumberStr = String.valueOf(ecuOffer.getZeroRootNumber());// 粗芯丝数
                String zeroStrandStr = String.valueOf(ecuOffer.getZeroStrand());// 粗芯丝数
                if (ecuOffer.getZeroSilkNumber().compareTo(new BigDecimal("0")) == 0) {
                    zeroSilkNumberStr = "";
                    zeroRootNumberStr = "";
                    zeroStrandStr = "";
                }
                String insulationNameStr;// 绝缘类型
                if (ecuOffer.getEcbInsulation() != null) {
                    insulationNameStr = ecuOffer.getEcbInsulation().getAbbreviation();// 绝缘类型
                } else {
                    insulationNameStr = "";// 绝缘类型
                }
                String insulationFireThicknessStr = ecuOffer.getInsulationFireThickness().multiply(new BigDecimal("1000")).toString();// 粗芯厚度
                if (ecuOffer.getInsulationFireThickness().compareTo(new BigDecimal("0")) == 0) {
                    insulationFireThicknessStr = "0";
                }
                String insulationZeroThicknessStr = ecuOffer.getInsulationZeroThickness().toString();// 细芯厚度
                if (ecuOffer.getInsulationZeroThickness().compareTo(new BigDecimal("0")) == 0) {
                    insulationZeroThicknessStr = "0";
                }
                String bagNameStr;// 包带类型
                if (ecuOffer.getEcbBag() != null) {
                    bagNameStr = ecuOffer.getEcbBag().getAbbreviation();// 包带类型
                } else {
                    bagNameStr = "";// 包带类型
                }
                String bagThicknessStr = ecuOffer.getBagThickness().multiply(new BigDecimal("1000")).toString();// 包带厚度
                if (ecuOffer.getBagThickness().compareTo(new BigDecimal("0")) == 0) {
                    bagThicknessStr = "0";
                }
                String bag22NameStr;// 包带类型
                if (ecuOffer.getEcb22Bag() != null) {
                    bag22NameStr = ecuOffer.getEcb22Bag().getAbbreviation();// 包带类型
                } else {
                    bag22NameStr = "";// 包带类型
                }
                String bag22ThicknessStr = "0";
                if (ecuOffer.getBag22Thickness() != null && ecuOffer.getBag22Thickness().compareTo(new BigDecimal("0")) != 0) {
                    bag22ThicknessStr = ecuOffer.getBag22Thickness().multiply(new BigDecimal("1000")).toString();// 包带厚度
                }
                String shieldNameStr;// 屏蔽类型
                if (ecuOffer.getEcbShield() != null) {
                    shieldNameStr = ecuOffer.getEcbShield().getAbbreviation();// 屏蔽类型
                } else {
                    shieldNameStr = "";// 屏蔽类型
                }
                String shieldThicknessStr = ecuOffer.getShieldThickness().multiply(new BigDecimal("1000")).toString();// 屏蔽厚度
                if (ecuOffer.getShieldThickness().compareTo(new BigDecimal("0")) == 0) {
                    shieldThicknessStr = "0";
                }
                String shieldPercentStr = (ecuOffer.getShieldPercent()
                        .multiply(new BigDecimal("100"))).setScale(0, RoundingMode.HALF_UP)
                        .toString();// 屏蔽编织密度
                if (ecuOffer.getShieldPercent().compareTo(new BigDecimal("0")) == 0) {
                    shieldPercentStr = "0%";
                } else {
                    shieldPercentStr = shieldPercentStr + "%";
                }
                String steelbandNameStr;// 钢带类型
                if (ecuOffer.getEcbSteelband() != null) {
                    steelbandNameStr = ecuOffer.getEcbSteelband().getAbbreviation();// 钢带类型
                } else {
                    steelbandNameStr = "";// 钢带类型
                }
                String steelbandThicknessStr = ecuOffer.getSteelbandThickness().multiply(new BigDecimal("1000")).toString();// 钢带厚度
                if (ecuOffer.getSteelbandThickness().compareTo(new BigDecimal("0")) == 0) {
                    steelbandThicknessStr = "0";
                }
                String steelbandStoreyStr = ecuOffer.getSteelbandStorey().toString();// 钢带层数
                if (ecuOffer.getSteelbandStorey() == 0) {
                    steelbandStoreyStr = "0";
                }
                String sheathNameStr;// 护套类型
                if (ecuOffer.getEcbSheath() != null) {
                    sheathNameStr = ecuOffer.getEcbSheath().getAbbreviation();// 护套类型
                } else {
                    sheathNameStr = "";// 护套类型
                }
                String sheathThicknessStr = ecuOffer.getSheathThickness()
                        .multiply(new BigDecimal("1000")).toString();// 护套厚度
                if (ecuOffer.getSheathThickness().compareTo(new BigDecimal("0")) == 0) {
                    sheathThicknessStr = "0";
                }
                String sheath22ThicknessStr = ecuOffer.getSheath22Thickness()
                        .multiply(new BigDecimal("1000")).toString();// 护套厚度
                if (ecuOffer.getSheath22Thickness().compareTo(new BigDecimal("0")) == 0) {
                    sheath22ThicknessStr = "0";
                }
                String micatapeThicknessStr = ecuOffer.getMicatapeThickness().multiply(new BigDecimal("1000")).toString();// 云母带厚度厚度
                if (ecuOffer.getMicatapeThickness().compareTo(new BigDecimal("0")) == 0) {
                    micatapeThicknessStr = "0";
                }
                String infillingNameStr;// 填充物类型
                if (ecuOffer.getEcbInfilling() != null) {
                    infillingNameStr = ecuOffer.getEcbInfilling().getAbbreviation();// 填充物类型
                } else {
                    infillingNameStr = "";// 填充物类型
                }
                String cableStrandStr = "";// 成缆系数
                if (ecuOffer.getCableStrand() != null) {
                    cableStrandStr = String.valueOf(ecuOffer.getCableStrand());// 填充物类型
                }
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                dataRow.setRowStyle(cellStyle);
                dataRow.createCell(0).setCellValue(areaStr);// 截面积
                dataRow.createCell(1).setCellValue(addPercentStr);// 成本加点
                dataRow.createCell(2).setCellValue(fireSilkNumberStr);// 粗芯丝号
                dataRow.createCell(3).setCellValue(fireRootNumberStr);// 粗芯丝数
                dataRow.createCell(4).setCellValue(fireStrandStr);// 粗芯绞合系数
                dataRow.createCell(5).setCellValue(zeroSilkNumberStr);// 细芯丝号
                dataRow.createCell(6).setCellValue(zeroRootNumberStr);// 细芯丝数
                dataRow.createCell(7).setCellValue(zeroStrandStr);// 细芯丝绞合系数
                dataRow.createCell(8).setCellValue(insulationNameStr);// 绝缘类型
                dataRow.createCell(9).setCellValue(insulationFireThicknessStr);// 绝缘粗芯厚度
                dataRow.createCell(10).setCellValue(insulationZeroThicknessStr);// 绝缘细芯厚度
                dataRow.createCell(11).setCellValue(bagNameStr);// 包带类型
                dataRow.createCell(12).setCellValue(bagThicknessStr);// 包带厚度
                dataRow.createCell(13).setCellValue(bag22NameStr);// 铠装包带类型
                dataRow.createCell(14).setCellValue(bag22ThicknessStr);// 铠装包带厚度
                dataRow.createCell(15).setCellValue(shieldNameStr);// 屏蔽类型
                dataRow.createCell(16).setCellValue(shieldThicknessStr);// 屏蔽厚度
                dataRow.createCell(17).setCellValue(shieldPercentStr);// 屏蔽编织密度
                dataRow.createCell(18).setCellValue(steelbandNameStr);// 钢带类型
                dataRow.createCell(19).setCellValue(steelbandThicknessStr);// 钢带厚度
                dataRow.createCell(20).setCellValue(steelbandStoreyStr);// 钢带层数
                dataRow.createCell(21).setCellValue(sheathNameStr);// 护套类型
                dataRow.createCell(22).setCellValue(sheathThicknessStr);// 护套厚度
                dataRow.createCell(23).setCellValue(sheath22ThicknessStr);// 铠装护套厚度
                dataRow.createCell(24).setCellValue(micatapeThicknessStr);// 云母带厚度
                dataRow.createCell(25).setCellValue(infillingNameStr);// 填充物
                dataRow.createCell(26).setCellValue(cableStrandStr);// 成缆系数
                dataRow.setHeight((short) 400);
            }
            sortId++;
        }
        // 设置下载时客户端Excel的名称   （上面注释的改进版本，上面的中文不支持）
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(
                (excelName).getBytes(),
                StandardCharsets.UTF_8) + ".xls");
        OutputStream ouputStream = response.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
        wb.close();
    }

    // getAddPercentList 返回要成本加点的数据
    public List<String> getAddPercentList(ProgrammeBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcuOffer record = new EcuOffer();
        record.setEcqulId(ecqulId);
        List<EcuOffer> listOffer = ecuOfferService.getList(record);
        Integer ecuopId = bo.getEcuopId();
        EcuoProgramme ecuoProgramme = ecuoProgrammeModel.getObjectPassEcuopId(ecuopId);
        String coreStr = ecuoProgramme.getCoreStr();
        String[] listCore = CommonFunction.getGson().fromJson(coreStr, String[].class);
        // log.info("listCore + " + CommonFunction.getGson().toJson(listCore));
        String areaStr = ecuoProgramme.getAreaStr();
        String[] listArea = CommonFunction.getGson().fromJson(areaStr, String[].class);
        // log.info("listArea + " + CommonFunction.getGson().toJson(listArea));
        List<String> idArr = new ArrayList<>();
        for (EcuOffer ecuOffer : listOffer) {
            String[] areaArr = (ecuOffer.getAreaStr()).split("\\+");
            String[] fireArr = areaArr[0].split("\\*");
            String[] zeroArr;
            String core = fireArr[0];
            String area = fireArr[1];
            if (areaArr.length == 2) {
                zeroArr = areaArr[1].split("\\*");
                core = fireArr[0] + zeroArr[0];
            }
            Boolean flagCore = StringTools.isContainString(core, listCore);
            // log.info("flagCore + " + flagCore);
            if (flagCore) {
                Boolean floatArea = StringTools.isContainString(area, listArea);
                // log.info("flagArea + " + floatArea);
                if (floatArea) {
                    idArr.add(String.valueOf(ecuOffer.getEcuoId()));
                }
            }
        }
        return idArr;
    }

    // dealAddPercentProgramme 成本加点按照方案执行
    public List<String> dealAddPercentProgramme(ProgrammeBo bo) {

        Integer ecqulId = bo.getEcqulId();
        EcuOffer record = new EcuOffer();
        record.setEcqulId(ecqulId);
        List<EcuOffer> listOffer = ecuOfferService.getList(record);
        Integer ecuopId = bo.getEcuopId();
        EcuoProgramme ecuoProgramme = ecuoProgrammeModel.getObjectPassEcuopId(ecuopId);
        String coreStr = ecuoProgramme.getCoreStr();
        String[] listCore = CommonFunction.getGson().fromJson(coreStr, String[].class);
        String areaStr = ecuoProgramme.getAreaStr();
        String[] listArea = CommonFunction.getGson().fromJson(areaStr, String[].class);
        List<String> idArr = new ArrayList<>();
        for (EcuOffer ecuOffer : listOffer) {
            String[] areaArr = (ecuOffer.getAreaStr()).split("\\+");
            String[] fireArr = areaArr[0].split("\\*");
            String[] zeroArr;
            String core = fireArr[0];
            String area = fireArr[1];
            if (areaArr.length == 2) {
                zeroArr = areaArr[1].split("\\*");
                core = fireArr[0] + zeroArr[0];
            }
            Boolean flagCore = StringTools.isContainString(core, listCore);
            // log.info("flagCore + " + flagCore);
            if (flagCore) {
                Boolean floatArea = StringTools.isContainString(area, listArea);
                // log.info("flagArea + " + floatArea);
                if (floatArea) {
                    record.setEcuoId(ecuOffer.getEcuoId());
                    record.setAddPercent(ecuoProgramme.getAddPercent());
                    ecuOfferService.update(record);
                    idArr.add(String.valueOf(ecuOffer.getEcuoId()));
                }
            }
        }
        return idArr;
    }

    // getStructureData
    public ProgrammeVo getStructureData(OfferStructBo bo) {
        Integer ecuoId = bo.getEcuoId();
        String silkName = bo.getSilkName();
        EcuOffer ecuOffer = getObjectPassEcuoId(ecuoId);
        // 导体数据
        ConductorComputeExtendBo mapConductor = ecableEcuOfferFunction.getConductorData(ecuOffer);
        BigDecimal fireDiameter = mapConductor.getFireDiameter();
        BigDecimal zeroDiameter = mapConductor.getZeroDiameter();
        BigDecimal conductorWeight = mapConductor.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = mapConductor.getConductorMoney();// 导体金额
        // 云母带数据
        BigDecimal fireMicatapeRadius = new BigDecimal("0");
        BigDecimal zeroMicatapeRadius = new BigDecimal("0");
        BigDecimal micatapeWeight = new BigDecimal("0");// 云母带重量
        BigDecimal micatapeMoney = new BigDecimal("0");// 云母带金额
        if (silkName.contains("N") || silkName.contains("NH")) {
            MicatapeComputeBo mapMicatape = ecableEcuOfferFunction.getMicatapeData(ecuOffer, fireDiameter, zeroDiameter);
            fireMicatapeRadius = mapMicatape.getFireMicatapeRadius();
            zeroMicatapeRadius = mapMicatape.getZeroMicatapeRadius();
            micatapeWeight = mapMicatape.getMicatapeWeight();// 云母带重量
            micatapeMoney = mapMicatape.getMicatapeMoney();// 云母带金额
        }
        // 绝缘数据
        InsulationComputeBo mapInsulation = ecableEcuOfferFunction.getInsulationData(ecuOffer, fireDiameter,
                zeroDiameter,
                fireMicatapeRadius,
                zeroMicatapeRadius);
        BigDecimal insulationWeight = mapInsulation.getInsulationWeight();// 绝缘重量
        BigDecimal insulationMoney = mapInsulation.getInsulationMoney();// 绝缘金额
        // 填充物数据
        InfillingComputeBo mapInfilling = ecableEcuOfferFunction.getInfillingData(ecuOffer, fireDiameter, zeroDiameter);
        BigDecimal externalDiameter = mapInfilling.getExternalDiameter();
        BigDecimal infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
        BigDecimal infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额
        // 包带数据
        BagComputeBo mapBag = ecableEcuOfferFunction.getBagData(ecuOffer, externalDiameter);
        BigDecimal bagWeight = mapBag.getBagWeight();// 包带重量
        BigDecimal bagMoney = mapBag.getBagMoney();// 包带金额
        // 钢带数据
        BigDecimal steelbandWeight = new BigDecimal("0");// 钢带重量
        BigDecimal steelbandMoney = new BigDecimal("0");// 钢带金额
        if (silkName.contains("22") || silkName.contains("23")) {
            // 钢带数据
            SteelBandComputeBo mapSteelband = ecableEcuOfferFunction.getSteelbandData(ecuOffer, externalDiameter);
            steelbandWeight = mapSteelband.getSteelbandWeight();// 钢带重量
            steelbandMoney = mapSteelband.getSteelbandMoney();// 钢带金额
        }

        // 护套数据
        SheathComputeBo mapSheath = ecableEcuOfferFunction.getSheathData(ecuOffer, externalDiameter);
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

    /***===数据模型===***/
    // deal
    @Transactional(rollbackFor = Exception.class)
    public void deal(EcuOffer record) {
        EcuOffer recordEcuOffer = new EcuOffer();
        recordEcuOffer.setEcCompanyId(record.getEcCompanyId());
        recordEcuOffer.setEcqulId(record.getEcqulId());
        recordEcuOffer.setAreaStr(record.getAreaStr());
        EcuOffer object = ecuOfferService.getObject(recordEcuOffer);
        if (object != null) {
            ecuOfferService.update(record);
        } else {
            Integer sortId = 1;
            EcuOffer ecuOffer = ecuOfferService.getObject(record);
            if (ecuOffer != null) {
                sortId = ecuOffer.getSortId() + 1;
            }
            record.setSortId(sortId);
            ecuOfferService.insert(record);
        }
        ecuoCoreModel.deal(record.getEcqulId(), record.getAreaStr());// 添加芯数表
        ecuoAreaModel.load(record.getEcqulId(), record.getAreaStr());// 添加平方数表
    }

    // delete
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcuOffer record = new EcuOffer();
        record.setEcCompanyId(ecCompanyId);
        ecuOfferService.delete(record);
    }

    // getObjectPassEcuoId
    public EcuOffer getObjectPassEcuoId(Integer ecuoId) {
        EcuOffer record = new EcuOffer();
        record.setEcuoId(ecuoId);
        return ecuOfferService.getObject(record);
    }


    public void dealDefaultWeightAndDefaultMoney(Integer ecqulId, String areaStr) {
        EcuOffer record = new EcuOffer();
        record.setEcqulId(ecqulId);
        record.setAreaStr(areaStr);
        EcuOffer ecuOffer = ecuOfferService.getObject(record);
        // 导体数据
        ConductorComputeExtendBo mapConductor = ecableEcuOfferFunction.getConductorData(ecuOffer);
        BigDecimal fireDiameter = mapConductor.getFireDiameter();//粗芯直径
        BigDecimal zeroDiameter = mapConductor.getZeroDiameter();//细芯直径
        BigDecimal conductorWeight = mapConductor.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = mapConductor.getConductorMoney();// 导体金额
        // 云母带数据
        MicatapeComputeBo mapMicatape = ecableEcuOfferFunction.getMicatapeData(ecuOffer, fireDiameter, zeroDiameter);
        BigDecimal fireMicatapeRadius = mapMicatape.getFireMicatapeRadius();
        BigDecimal zeroMicatapeRadius = mapMicatape.getZeroMicatapeRadius();
        BigDecimal micatapeWeight = mapMicatape.getMicatapeWeight();// 云母带重量
        BigDecimal micatapeMoney = mapMicatape.getMicatapeMoney();// 云母带金额
        // 绝缘数据
        InsulationComputeBo mapInsulation = ecableEcuOfferFunction.getInsulationData(ecuOffer, fireDiameter,
                zeroDiameter,
                fireMicatapeRadius,
                zeroMicatapeRadius);
        BigDecimal insulationWeight = mapInsulation.getInsulationWeight();// 绝缘重量
        BigDecimal insulationMoney = mapInsulation.getInsulationMoney();// 绝缘金额
        // 填充物数据
        InfillingComputeBo mapInfilling = ecableEcuOfferFunction.getInfillingData(ecuOffer, fireDiameter, zeroDiameter);
        BigDecimal externalDiameter = mapInfilling.getExternalDiameter();
        BigDecimal infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
        BigDecimal infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额
        // 包带数据
        BagComputeBo mapBag = ecableEcuOfferFunction.getBagData(ecuOffer, externalDiameter);
        BigDecimal bagWeight = mapBag.getBagWeight();// 包带重量
        BigDecimal bagMoney = mapBag.getBagMoney();// 包带金额
        // 钢带数据
        SteelBandComputeBo mapSteelband = ecableEcuOfferFunction.getSteelbandData(ecuOffer, externalDiameter);
        BigDecimal steelbandWeight = mapSteelband.getSteelbandWeight();// 钢带重量
        BigDecimal steelbandMoney = mapSteelband.getSteelbandMoney();// 钢带金额
        // 护套数据
        SheathComputeBo mapSheath = ecableEcuOfferFunction.getSheathData(ecuOffer, externalDiameter);
        BigDecimal sheathWeight = mapSheath.getSheathWeight();// 护套重量
        BigDecimal sheathMoney = mapSheath.getSheathMoney();// 护套金额
        BigDecimal defaultWeight = conductorWeight
                .add(micatapeWeight)
                .add(bagWeight)
                .add(sheathWeight)
                .add(insulationWeight)
                .add(infillingWeight)
                .add(steelbandWeight)
                .add(sheathWeight);
        BigDecimal defaultMoney = conductorMoney
                .add(micatapeMoney)
                .add(bagMoney)
                .add(sheathMoney)
                .add(insulationMoney)
                .add(infillingMoney)
                .add(steelbandMoney)
                .add(sheathMoney);
        record.setEcuoId(ecuOffer.getEcuoId());
        record.setDefaultWeight(defaultWeight);
        record.setDefaultMoney(defaultMoney);
        ecuOfferService.update(record);
    }
}
