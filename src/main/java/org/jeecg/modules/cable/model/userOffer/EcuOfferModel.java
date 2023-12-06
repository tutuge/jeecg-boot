package org.jeecg.modules.cable.model.userOffer;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.userOffer.offer.vo.EcuOfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.entity.userQuality.EcuArea;
import org.jeecg.modules.cable.model.systemEcable.EcSilkServiceModel;
import org.jeecg.modules.cable.model.userEcable.*;
import org.jeecg.modules.cable.model.userQuality.EcquLevelModel;
import org.jeecg.modules.cable.service.userOffer.EcuOfferService;
import org.jeecg.modules.cable.service.userQuality.EcquLevelService;
import org.jeecg.modules.cable.service.userQuality.EcuAreaService;
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
import java.util.*;

@Service
@Slf4j
public class EcuOfferModel {

    @Resource
    EcuOfferService ecuOfferService;
    @Resource
    EcquLevelService ecquLevelService;
    @Resource
    EcbuInsulationModel ecbuInsulationModel;
    @Resource
    EcbuBagModel ecbuBagModel;
    @Resource
    EcbuShieldModel ecbuShieldModel;
    @Resource
    EcbuSteelBandModel ecbuSteelbandModel;
    @Resource
    EcbuSheathModel ecbuSheathModel;
    @Resource
    EcbuMicaTapeModel ecbuMicatapeModel;
    @Resource
    EcbuInfillingModel ecbuInfillingModel;
    @Resource
    EcuAreaService ecuAreaService;
    ExcelUtils excelUtils = new ExcelUtils();
    @Resource
    EcableEcuOfferFunction ecableEcuOfferFunction;
    @Resource
    EcquLevelModel ecquLevelModel;
    @Resource
    EcSilkServiceModel ecSilkServiceModel;// 丝型号
    @Resource
    EcuoCoreModel ecuoCoreModel;// 芯数
    @Resource
    EcuoAreaModel ecuoAreaModel;// 平方数
    @Resource
    private EcuoProgrammeModel ecuoProgrammeModel;

    @Transactional(rollbackFor = Exception.class)
    public Result<?> importDeal(MultipartFile file, Integer ecqulId) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        // 信息
        assert file != null;
        int successNum = 0, failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        EcquLevel recordEcquLevel = new EcquLevel();
        recordEcquLevel.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
        if (ObjUtil.isNull(ecquLevel)) {
            throw new RuntimeException("质量等级不存在！");
        }
        Integer ecbucId = ecquLevel.getEcbucId();

        try {
            InputStream in = file.getInputStream();
            List<List<Object>> listob = excelUtils.getListByExcel(in, file.getOriginalFilename());
            if (!listob.isEmpty()) {
                //绝缘
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuInsulation = ecbuInsulationModel.getInsulationPassInsulationStr(ecCompanyId);
                //包带
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuBag = ecbuBagModel.getObjectPassBagStr(ecCompanyId);
                //屏蔽
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuShield = ecbuShieldModel.getObjectPassShieldStr(ecCompanyId);
                //钢带
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuSteelband = ecbuSteelbandModel.getObjectPassSteelBandStr(ecCompanyId);
                // 护套
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuSheath = ecbuSheathModel.getObjectPassSheathStr(ecCompanyId);
                // 云母带
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuMicaTape = ecbuMicatapeModel.getObjectPassMicaTape(ecCompanyId);
                // 填充物
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuInfilling = ecbuInfillingModel.getObjectPassInfillingStr(ecCompanyId);
                for (int i = 0; i < listob.size(); i++) {
                    try {
                        List<Object> objects = listob.get(i);
                        if (objects.size() != 28) {
                            failureMsg.append("<br/>成本库表 " + "第" + i + "行" + "导入出错");
                            failureNum++;
                            continue;
                        }
                        String areaStr = objects.get(0).toString();// 截面积
                        String addPercentStr = objects.get(1).toString();// 成本加点
                        String fireSilkNumberStr = objects.get(2).toString();// 粗芯丝号
                        String fireRootNumberStr = objects.get(3).toString();// 粗芯根数
                        String fireStrandStr = objects.get(4).toString();// 粗芯丝绞合系数
                        String zeroSilkNumberStr = objects.get(5).toString();// 细芯丝号
                        String zeroRootNumberStr = objects.get(6).toString();// 细芯根数
                        String zeroStrandStr = objects.get(7).toString();// 细芯丝绞合系数
                        String insulationStr = objects.get(8).toString();// 绝缘类型
                        String insulationFireThicknessStr = objects.get(9).toString();// 粗芯绝缘厚度
                        String insulationZeroThicknessStr = objects.get(10).toString();// 细芯绝缘厚度
                        String bagStr = objects.get(11).toString();// 包带类型
                        String bagThicknessStr = objects.get(12).toString();// 包带厚度
                        String bag22Str = objects.get(13).toString();// 铠装包带类型
                        String bag22ThicknessStr = objects.get(14).toString();// 铠装包带厚度
                        String shieldStr = objects.get(15).toString();// 屏蔽类型
                        String shieldThicknessStr = objects.get(16).toString();// 屏蔽厚度
                        String shieldPercentStr = objects.get(17).toString();// (屏蔽系数(%))屏蔽编织系数
                        String steelBandStr = objects.get(18).toString();// 钢带类型
                        String steelBandThicknessStr = objects.get(19).toString();// 钢带厚度
                        String steelBandStoreyStr = objects.get(20).toString();// 钢带层数
                        String sheathStr = objects.get(21).toString();// 护套类型
                        String sheathThicknessStr = objects.get(22).toString();// 护套厚度
                        String sheath22ThicknessStr = objects.get(23).toString();// 铠装护套厚度
                        String micaTapeStr = objects.get(24).toString();// 云母带类型
                        String micaTapeThicknessStr = objects.get(25).toString();// 云母带厚度
                        String infillingStr = objects.get(26).toString();// 填充物类型
                        String cableStrandStr = objects.get(27).toString();// 成缆绞合系数
                        // 成本加点
                        BigDecimal addPercent = BigDecimal.ZERO;
                        if (StrUtil.isNotBlank(addPercentStr)) {
                            String replace = addPercentStr.replace("%", "");
                            addPercent = new BigDecimal(replace).divide(BigDecimal.valueOf(100D), 2, RoundingMode.HALF_UP);
                        }
                        BigDecimal fireSilkNumber = new BigDecimal(fireSilkNumberStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
                        Integer fireRootNumber = Integer.parseInt(fireRootNumberStr);// 粗芯根数
                        BigDecimal fireStrand = BigDecimal.ZERO;// 粗芯丝绞合系数
                        if (!"0".equals(fireStrandStr) && !"".equals(fireStrandStr)) {
                            fireStrand = new BigDecimal(fireStrandStr);
                        }
                        BigDecimal zeroSilkNumber = BigDecimal.ZERO;
                        if (!"".equals(zeroSilkNumberStr)) {
                            zeroSilkNumber = new BigDecimal(zeroSilkNumberStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
                        }
                        int zeroRootNumber = 0;
                        if (!"".equals(zeroRootNumberStr)) {
                            zeroRootNumber = Integer.parseInt(fireRootNumberStr);
                        }
                        BigDecimal zeroStrand = BigDecimal.ZERO;// 细芯丝绞合系数
                        if (!"0".equals(zeroStrandStr) && !"".equals(zeroStrandStr)) {
                            zeroStrand = new BigDecimal(zeroStrandStr);
                        }
                        Integer ecbuiId = 0;// 绝缘
                        BigDecimal insulationFireThickness = BigDecimal.ZERO;
                        if (!"0".equals(insulationFireThicknessStr) && !"".equals(insulationFireThicknessStr)) {
                            insulationFireThickness = new BigDecimal(insulationFireThicknessStr).divide(new BigDecimal("1000"), 18, RoundingMode.HALF_UP);
                        }
                        BigDecimal insulationZeroThickness = BigDecimal.ZERO;
                        if (!"0".equals(insulationZeroThicknessStr) && !"".equals(insulationZeroThicknessStr)) {
                            insulationZeroThickness = new BigDecimal(insulationZeroThicknessStr).divide(new BigDecimal("1000"), 18, RoundingMode.HALF_UP);
                        }
                        if (ecbuInsulation != null) {
                            Map<String, Integer> key = ecbuInsulation.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(insulationStr);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbuiId = k;
                                } else {
                                    Map<String, Integer> value = ecbuInsulation.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(insulationStr);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbuiId = v;
                                        }
                                    }
                                }
                            }
                        }
                        // 包带
                        Integer ecbubId = 0;
                        if (ecbuBag != null) {
                            Map<String, Integer> key = ecbuBag.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(bagStr);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbubId = k;
                                } else {
                                    Map<String, Integer> value = ecbuBag.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(bagStr);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbubId = v;
                                        }
                                    }
                                }
                            }
                        }

                        BigDecimal bagThickness = BigDecimal.ZERO;
                        if (!"".equals(bagThicknessStr)) {
                            bagThickness = new BigDecimal(bagThicknessStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
                        }
                        // 铠装包带包带
                        Integer ecbub22Id = 0;
                        if (ecbuBag != null) {
                            Map<String, Integer> key = ecbuBag.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(bag22Str);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbub22Id = k;
                                } else {
                                    Map<String, Integer> value = ecbuBag.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(bag22Str);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbub22Id = v;
                                        }
                                    }
                                }
                            }
                        }
                        BigDecimal bag22Thickness = BigDecimal.ZERO;
                        if (!"".equals(bag22ThicknessStr)) {
                            bag22Thickness = new BigDecimal(bag22ThicknessStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
                        }
                        // 屏蔽
                        Integer ecbusId = 0;
                        if (ecbuShield != null) {
                            Map<String, Integer> key = ecbuShield.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(shieldStr);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbusId = k;
                                } else {
                                    Map<String, Integer> value = ecbuShield.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(shieldStr);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbusId = v;
                                        }
                                    }
                                }
                            }
                        }

                        BigDecimal shieldThickness = BigDecimal.ZERO;
                        if (!"".equals(shieldThicknessStr) && !"0".equals(shieldThicknessStr)) {
                            shieldThickness = new BigDecimal(shieldThicknessStr)
                                    .divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
                        }
                        BigDecimal shieldPercent = BigDecimal.ZERO;
                        shieldPercentStr = shieldPercentStr.replace("%", "");
                        if (!shieldPercentStr.isEmpty() && !"0".equals(shieldPercentStr)) {
                            shieldPercent = new BigDecimal(shieldPercentStr);
                        }
                        // 钢带
                        Integer ecbusbId = 0;
                        if (ecbuSteelband != null) {
                            Map<String, Integer> key = ecbuSteelband.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(steelBandStr);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbusbId = k;
                                } else {
                                    Map<String, Integer> value = ecbuSteelband.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(steelBandStr);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbusbId = v;
                                        }
                                    }
                                }
                            }
                        }

                        BigDecimal steelbandThickness = BigDecimal.ZERO;
                        if (!"".equals(steelBandThicknessStr)) {
                            steelbandThickness = new BigDecimal(steelBandThicknessStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
                        }
                        int steelbandStorey = 0;
                        if (!"".equals(steelBandStoreyStr) && !"0".equals(steelBandStoreyStr)) {
                            steelbandStorey = Integer.parseInt(steelBandStoreyStr);
                        }
                        // 护套
                        Integer ecbuSheathId = 0;
                        if (ecbuSheath != null) {
                            Map<String, Integer> key = ecbuSheath.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(sheathStr);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbuSheathId = k;
                                } else {
                                    Map<String, Integer> value = ecbuSheath.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(sheathStr);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbuSheathId = v;
                                        }
                                    }
                                }
                            }
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
                        Integer ecbumId = 0;
                        if (ecbuMicaTape != null) {
                            //ecbumId = ecbuMicaTape.getEcbumId();
                            Map<String, Integer> key = ecbuMicaTape.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(micaTapeStr);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbumId = k;
                                } else {
                                    Map<String, Integer> value = ecbuMicaTape.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(micaTapeStr);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbumId = v;
                                        }
                                    }
                                }
                            }
                        }

                        BigDecimal micatapeThickness = BigDecimal.ZERO;
                        if (!"".equals(micaTapeThicknessStr)) {
                            micatapeThickness = new BigDecimal(micaTapeThicknessStr).divide(new BigDecimal("1000"), 16, RoundingMode.HALF_UP);
                        }
                        // 填充物
                        Integer ecbuinId = 0;
                        if (ecbuInfilling != null) {
                            Map<String, Integer> key = ecbuInfilling.getKey();
                            if (ObjUtil.isNotNull(key)) {
                                Integer k = key.get(infillingStr);
                                if (ObjUtil.isNotNull(k)) {
                                    ecbuinId = k;
                                } else {
                                    Map<String, Integer> value = ecbuInfilling.getValue();
                                    if (ObjUtil.isNotNull(value)) {
                                        Integer v = value.get(infillingStr);
                                        if (ObjUtil.isNotNull(v)) {
                                            ecbuinId = v;
                                        }
                                    }
                                }
                            }
                        }

                        // 成缆绞合系数
                        BigDecimal cableStrand = BigDecimal.ZERO;
                        if (!"".equals(cableStrandStr)) {
                            cableStrand = new BigDecimal(cableStrandStr);
                        }
                        // 插入
                        Boolean startType = true;
                        int sortId = 1;
                        EcuOffer record = new EcuOffer();
                        record.setEcqulId(ecqulId);
                        EcuOffer ecuOffer = ecuOfferService.getObject(record);
                        if (ecuOffer != null) {
                            sortId = ecuOffer.getSortId() + 1;
                        }
                        Integer fireMembrance = 0;// 粗芯过膜
                        BigDecimal firePress = BigDecimal.ZERO;// 粗芯压型
                        Integer zeroMembrance = 0;// 细芯过膜
                        BigDecimal zeroPress = BigDecimal.ZERO;// 细芯压型
                        Integer ecbuswId = 0;// 钢丝类型
                        BigDecimal steelwireMembrance = BigDecimal.ZERO;// 钢丝过膜
                        BigDecimal steelwirePress = BigDecimal.ZERO;// 钢丝压型
                        //根据规格获取成本库表
                        record = new EcuOffer();
                        record.setEcqulId(ecqulId);
                        record.setAreaStr(areaStr);
                        ecuOffer = ecuOfferService.getObject(record);
                        record.setEcqulId(ecqulId);// 电缆质量等级ID
                        record.setEcbucId(ecbucId);// 导体ID
                        record.setEcCompanyId(ecCompanyId);
                        record.setStartType(startType);// 是否开启
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
                        record.setEcbuSheathId(ecbuSheathId);// 护套类型
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
                        record.setDefaultWeight(BigDecimal.ZERO);// 默认重量
                        record.setDefaultMoney(BigDecimal.ZERO);// 默认金额
                        // log.info("record + " + CommonFunction.getGson().toJson(record));
                        if (ecuOffer != null) {
                            record.setEcuoId(ecuOffer.getEcuoId());
                            ecuOfferService.update(record);
                        } else {
                            //插入的写入排序
                            record.setSortId(sortId);
                            ecuOfferService.insert(record);
                        }
                        dealDefaultWeightAndDefaultMoney(ecqulId, areaStr);// 修改默认重量和金额
                        ecuoCoreModel.deal(ecqulId, areaStr);// 添加芯数表
                        ecuoAreaModel.load(ecqulId, areaStr);// 添加平方数表
                        successMsg.append("<br/>成本库表 " + "第" + i + "行" + "导入成功");
                        successNum++;
                    } catch (Exception e) {
                        failureMsg.append("<br/>成本库表 " + "第" + i + "行" + "导入出错");
                        failureNum++;
                    }
                }
                loadArea(ecCompanyId, ecqulId);// 加载质量等级对应的截面库ecuArea
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("文件导入失败:" + e.getMessage());
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return Result.ok(successMsg.toString());
    }

    // loadArea 加载质量等级对应的截面库ecuArea
    public void loadArea(Integer ecCompanyId, Integer ecqulId) {
        //先删除对应质量等级的所有的截面
        ecuAreaService.deletePassEcqulId(ecqulId);
        //获取对应质量等级ID下的成本库表所有数据
        EcuOffer record = new EcuOffer();
        record.setEcqulId(ecqulId);
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcuOffer> list = ecuOfferService.getList(record);
        int sortId = 1;
        List<EcuArea> areas = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            EcuOffer ecuOffer = list.get(i);
            String areaStr = ecuOffer.getAreaStr();
            EcuArea area = new EcuArea();
            area.setEcCompanyId(ecCompanyId);
            area.setEcqulId(ecqulId);
            area.setStartType(true);
            area.setSortId(sortId + i);
            area.setAreaStr(areaStr);
            area.setEffectTime(new Date());
            areas.add(area);
        }
        //批量插入
        if (!areas.isEmpty()) {
            ecuAreaService.batchInsert(areas);
        }
        ////写入txt
        //List<String> txtList = new ArrayList<>();
        //txtList.add(CommonFunction.getGson().toJson(areas));
        //ecdAreaModel.deal(ecCompanyId, ecqulId, txtList);
    }

    // 根据质量等级ID和规格 选择成本库表内的数据
    public EcuOffer getOfferPassEcuqInput(EcuqInput ecuqInput) {
        EcuOffer ecuOffer = null;
        int ecqulId = 0;
        if (ecuqInput.getEcqulId() != 0) {
            ecqulId = ecuqInput.getEcqulId();
        }
        int storeId = 0;
        if (ecuqInput.getEcbusId() != 0) {
            storeId = ecuqInput.getEcbusId();
        }
        String areaStr = ecuqInput.getAreaStr();
        if (!(ecqulId == 0 || storeId == 0 || "".equals(areaStr))) {
            //根据质量等级ID和规格查询成本库表
            EcuOffer object = ecuOfferService.getByLevelIdAndArea(ecqulId, areaStr);
            if (object != null) {
                ecuOffer = object;
            }
        }
        return ecuOffer;
    }


    public EcuOfferVo getListAndCount(EcuOfferListBo bo) {
        EcuOffer record = new EcuOffer();
        Boolean startType = bo.getStartType();
        Integer ecqulId = bo.getEcqulId();
        record.setStartType(startType);
        record.setEcqulId(ecqulId);
        List<EcuOffer> list = ecuOfferService.getList(record);
        //Long count = ecuOfferService.getCount(record);
        return new EcuOfferVo(list, list.size());
    }


    public EcuOffer getObject(EcuOfferBaseBo bo) {
        EcuOffer record = new EcuOffer();
        record.setEcuoId(bo.getEcuoId());
        return ecuOfferService.getObject(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public void start(List<EcuOfferStartBo> bos) {
        Set<Integer> levelId = new HashSet<>();
        for (EcuOfferStartBo bo : bos) {
            Integer ecuoId = bo.getEcuoId();
            EcuOffer record = new EcuOffer();
            record.setEcuoId(ecuoId);
            record.setStartType(bo.getStartType());
            ecuOfferService.update(record);
            levelId.add(bo.getEcqulId());
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        levelId.forEach(v -> {
            loadArea(sysUser.getEcCompanyId(), v);// 加载质量等级对应的截面库ecuArea
        });
    }


    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(EcuOfferInsertBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuoId = bo.getEcuoId();//主键ID
        String areaStr = bo.getAreaStr();//标称截面
        Integer ecqulId = bo.getEcqulId();//质量等级ID
        EcuOffer record = new EcuOffer();
        String msg = "";
        if (ObjectUtil.isNull(ecuoId)) {// 插入
            record.setEcuoId(ecuoId);
            record.setEcqulId(ecqulId);
            record.setAreaStr(areaStr);
            EcuOffer ecuOffer = ecuOfferService.getObject(record);
            if (ecuOffer != null) {
                throw new RuntimeException("截面积已占用");
            }

            EcquLevel recordEcquLevel = new EcquLevel();
            recordEcquLevel.setEcqulId(ecqulId);
            EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
            Integer ecbucId = ecquLevel.getEcbucId();
            Boolean startType = false;
            int sortId = 1;
            ecuOffer = ecuOfferService.getObject(record);
            if (ecuOffer != null) {
                sortId = ecuOffer.getSortId() + 1;
            }
            record.setEcqulId(ecqulId);// 电缆质量等级ID
            record.setEcbucId(ecbucId);// 导体ID
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(startType);// 是否开启
            record.setSortId(sortId);// 排序
            record.setAreaStr(areaStr);// 截面积
            record.setAddPercent(BigDecimal.ZERO);// 成本加点
            record.setFireSilkNumber(BigDecimal.ZERO);// 火丝丝号
            record.setFireRootNumber(0);// 粗芯根数
            record.setFireMembrance(0);// 粗芯过膜
            record.setFirePress(BigDecimal.ZERO);// 粗芯压型
            record.setZeroSilkNumber(BigDecimal.ZERO);// 细芯丝号
            record.setZeroRootNumber(0);// 细芯根数
            record.setZeroMembrance(0);// 细芯过膜
            record.setZeroPress(BigDecimal.ZERO);// 细芯过型
            record.setEcbuiId(0);// 绝缘类型
            record.setInsulationFireThickness(BigDecimal.ZERO);// 粗芯绝缘厚度
            record.setInsulationZeroThickness(BigDecimal.ZERO);// 细芯绝缘厚度
            record.setEcbubId(0);// 包带类型
            record.setBagThickness(BigDecimal.ZERO);// 包带厚度
            record.setEcbuShieldId(0);// 屏蔽类型
            record.setShieldThickness(BigDecimal.ZERO);// 屏蔽厚度
            record.setShieldPercent(BigDecimal.ZERO);// 屏蔽编织系数
            record.setEcbusbId(0);// 钢带类型
            record.setSteelbandThickness(BigDecimal.ZERO);// 钢带厚度
            record.setSteelbandStorey(0);// 钢带层数
            // record.setEcbusid(0);//护套类型
            record.setSheathThickness(BigDecimal.ZERO);// 护套厚度
            record.setSheath22Thickness(BigDecimal.ZERO);// 铠装护套厚度
            record.setEcbumId(0);// 云母带类型
            record.setMicatapeThickness(BigDecimal.ZERO);// 云母带厚度
            record.setFireStrand(BigDecimal.ZERO);// 粗芯绞合系数
            record.setZeroStrand(BigDecimal.ZERO);// 细芯绞合系数
            record.setEcbuinId(0);// 填充物类型
            record.setEcbuswId(0);// 钢丝类型
            record.setSteelwireMembrance(BigDecimal.ZERO);// 钢丝过膜
            record.setSteelwirePress(BigDecimal.ZERO);// 钢丝压型
            ecuOfferService.insert(record);
            msg = "插入数据成功";
        } else {
            record.setEcuoId(ecuoId);
            record.setAddPercent(bo.getAddPercent());
            record.setAreaStr(areaStr);// 截面str
            record.setFireSilkNumber(bo.getFireSilkNumber());// 粗芯丝号
            record.setFireRootNumber(bo.getFireRootNumber());// 粗芯根数
            record.setFireStrand(bo.getFireStrand());// 粗芯绞合系数
            record.setZeroSilkNumber(bo.getZeroSilkNumber());// 细芯丝号
            record.setZeroRootNumber(bo.getZeroRootNumber());// 细芯丝号
            record.setZeroStrand(bo.getZeroStrand());// 细芯绞合系数
            record.setEcbuiId(bo.getEcbuiId());// 绝缘类型
            record.setInsulationFireThickness(bo.getInsulationFireThickness());// 粗芯绝缘厚度
            record.setInsulationZeroThickness(bo.getInsulationZeroThickness());// 细芯绝缘厚度
            record.setEcbubId(bo.getEcbubId());// 包带类型
            record.setBagThickness(bo.getBagThickness());// 包带厚度
            record.setEcbuShieldId(bo.getEcbusid());// 屏蔽类型
            record.setShieldThickness(bo.getShieldThickness());// 屏蔽厚度
            record.setShieldPercent(bo.getShieldPercent());// 屏蔽编织系数
            record.setEcbusbId(bo.getEcbusbId());// 钢带类型
            record.setSteelbandThickness(bo.getSteelbandThickness());// 钢带厚度
            record.setSteelbandStorey(bo.getSteelbandStorey());// 钢带层数
            record.setEcbuSheathId(bo.getEcbusid());// 护套类型
            record.setSheathThickness(bo.getSheathThickness());// 护套厚度
            record.setSheath22Thickness(bo.getSheath22Thickness());// 护套厚度
            record.setEcbumId(bo.getEcbumId());// 云母带类型
            record.setMicatapeThickness(bo.getMicatapeThickness());// 云母带厚度
            record.setEcbuinId(bo.getEcbuinId());// 填充物类型
            ecuOfferService.update(record);
            msg = "数据更新成功";
        }
        loadArea(sysUser.getEcCompanyId(), ecqulId);// 加载质量等级对应的截面库ecuArea
        dealDefaultWeightAndDefaultMoney(ecqulId, areaStr);
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcuOfferSortBo> bos) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        for (EcuOfferSortBo bo : bos) {
            Integer ecuoId = bo.getEcuoId();
            Integer sortId = bo.getSortId();
            EcuOffer record = new EcuOffer();
            record.setEcuoId(ecuoId);
            record.setSortId(sortId);
            ecuOfferService.update(record);
            record = new EcuOffer();
            record.setEcuoId(ecuoId);
            EcuOffer ecuOffer = ecuOfferService.getObject(record);
            loadArea(sysUser.getEcCompanyId(), ecuOffer.getEcqulId());// 加载质量等级对应的截面库ecuArea
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcuOfferBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecuoId = bo.getEcuoId();
        EcuOffer ecuOffer = ecuOfferService.getById(ecuoId);
        if (ObjUtil.isNull(ecuOffer)) {
            throw new RuntimeException("当前数据不存在");
        }
        //质量等级ID
        Integer ecqulId = ecuOffer.getEcqulId();
        //查询小于此数组排序后的数组，用于重新排序
        Integer sortId = ecuOffer.getSortId();
        //因为排序，所以将本质量等级下的所有排序都减一
        ecuOfferService.reduceSort(ecqulId, sortId);
        EcuOffer record = new EcuOffer();
        record.setEcuoId(ecuoId);
        ecuOfferService.delete(record);
        loadArea(sysUser.getEcCompanyId(), ecqulId);// 加载质量等级对应的截面库ecuArea
    }


    public EcSilk getEcSilkPassEcqulId(EcuSilkBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcquLevel ecquLevel = ecquLevelModel.getObjectPassEcqulId(ecqulId);
        Integer ecsId = ecquLevel.getEcusId();
        return ecSilkServiceModel.getObjectPassEcsId(ecsId);
    }

    // export 导出数据
    public void exportData(HttpServletResponse response, Integer ecqulId) throws IOException {
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
        for (int i = 0; i < 27; i++) {
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
        titleRow.createCell(1).setCellValue("成本加点");
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
        titleRow.createCell(17).setCellValue("屏蔽系数(%)");
        titleRow.createCell(18).setCellValue("钢带类型");
        titleRow.createCell(19).setCellValue("钢带厚度");
        titleRow.createCell(20).setCellValue("钢带层数");
        titleRow.createCell(21).setCellValue("护套类型");
        titleRow.createCell(22).setCellValue("护套厚度");
        titleRow.createCell(23).setCellValue("铠装护套厚度");
        titleRow.createCell(24).setCellValue("云母带类型");
        titleRow.createCell(25).setCellValue("云母带厚度");
        titleRow.createCell(26).setCellValue("填充物类型");
        titleRow.createCell(27).setCellValue("成缆系数");
        titleRow.setHeight((short) 400);
        EcuOffer recordEcuOffer = new EcuOffer();
        recordEcuOffer.setEcqulId(ecqulId);
        List<EcuOffer> list = ecuOfferService.getList(recordEcuOffer);
        log.info("h3");
        int sortId = 1;
        for (EcuOffer ecuOffer : list) {
            if (sortId < list.size()) {
                String addPercentStr = (ecuOffer.getAddPercent().multiply(new BigDecimal("100")))
                        .setScale(0, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();// 成本加点
                if (ecuOffer.getAddPercent().compareTo(BigDecimal.ZERO) == 0) {
                    addPercentStr = "0%";
                } else {
                    addPercentStr = addPercentStr + "%";
                }
                String areaStr = ecuOffer.getAreaStr();// 截面积
                String fireSilkNumberStr = ecuOffer.getFireSilkNumber()
                        .multiply(new BigDecimal("1000")).stripTrailingZeros().toPlainString();// 粗芯丝号
                String fireRootNumberStr = String.valueOf(ecuOffer.getFireRootNumber());// 粗芯丝数
                String fireStrandStr = ecuOffer.getFireStrand().stripTrailingZeros().toPlainString();// 粗芯丝数
                if (ecuOffer.getFireSilkNumber().compareTo(BigDecimal.ZERO) == 0) {
                    fireSilkNumberStr = "";
                    fireRootNumberStr = "";
                    fireStrandStr = "";
                }
                String zeroSilkNumberStr = ecuOffer.getZeroSilkNumber()
                        .multiply(new BigDecimal("1000")).stripTrailingZeros().toPlainString();// 细芯丝号
                String zeroRootNumberStr = String.valueOf(ecuOffer.getZeroRootNumber());// 细芯根数
                String zeroStrandStr = ecuOffer.getZeroStrand().stripTrailingZeros().toPlainString();// 细芯绞系数
                if (ecuOffer.getZeroSilkNumber().compareTo(BigDecimal.ZERO) == 0) {
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
                String insulationFireThicknessStr = ecuOffer.getInsulationFireThickness()
                        .multiply(new BigDecimal("1000")).stripTrailingZeros().toPlainString();// 粗芯厚度
                if (ecuOffer.getInsulationFireThickness().compareTo(BigDecimal.ZERO) == 0) {
                    insulationFireThicknessStr = "0";
                }
                String insulationZeroThicknessStr = ecuOffer.getInsulationZeroThickness()
                        .stripTrailingZeros().toPlainString();// 细芯厚度
                if (ecuOffer.getInsulationZeroThickness().compareTo(BigDecimal.ZERO) == 0) {
                    insulationZeroThicknessStr = "0";
                }
                String bagNameStr;// 包带类型
                if (ecuOffer.getEcbBag() != null) {
                    bagNameStr = ecuOffer.getEcbBag().getAbbreviation();// 包带类型
                } else {
                    bagNameStr = "";// 包带类型
                }
                String bagThicknessStr = ecuOffer.getBagThickness().multiply(new BigDecimal("1000"))
                        .stripTrailingZeros().toPlainString();// 包带厚度
                if (ecuOffer.getBagThickness().compareTo(BigDecimal.ZERO) == 0) {
                    bagThicknessStr = "0";
                }
                String bag22NameStr;// 包带类型
                if (ecuOffer.getEcb22Bag() != null) {
                    bag22NameStr = ecuOffer.getEcb22Bag().getAbbreviation();// 包带类型
                } else {
                    bag22NameStr = "";// 包带类型
                }
                String bag22ThicknessStr = "0";
                if (ecuOffer.getBag22Thickness() != null && ecuOffer.getBag22Thickness().compareTo(BigDecimal.ZERO) != 0) {
                    bag22ThicknessStr = ecuOffer.getBag22Thickness().multiply(new BigDecimal("1000"))
                            .stripTrailingZeros().toPlainString();// 包带厚度
                }
                String shieldNameStr;// 屏蔽类型
                if (ecuOffer.getEcbShield() != null) {
                    shieldNameStr = ecuOffer.getEcbShield().getAbbreviation();// 屏蔽类型
                } else {
                    shieldNameStr = "";// 屏蔽类型
                }
                String shieldThicknessStr = ecuOffer.getShieldThickness().multiply(new BigDecimal("1000"))
                        .stripTrailingZeros().toPlainString();// 屏蔽厚度
                if (ecuOffer.getShieldThickness().compareTo(BigDecimal.ZERO) == 0) {
                    shieldThicknessStr = "0";
                }
                String shieldPercentStr = (ecuOffer.getShieldPercent()
                        .multiply(new BigDecimal("100"))).setScale(0, RoundingMode.HALF_UP)
                        .stripTrailingZeros().toPlainString();// 屏蔽编织密度(屏蔽系数(%))
                if (ecuOffer.getShieldPercent().compareTo(BigDecimal.ZERO) == 0) {
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
                String steelbandThicknessStr = ecuOffer.getSteelbandThickness().multiply(new BigDecimal("1000"))
                        .stripTrailingZeros().toPlainString();// 钢带厚度
                if (ecuOffer.getSteelbandThickness().compareTo(BigDecimal.ZERO) == 0) {
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
                String sheathThicknessStr = ecuOffer.getSheathThickness().multiply(new BigDecimal("1000")).stripTrailingZeros().toPlainString();// 护套厚度
                if (ecuOffer.getSheathThickness().compareTo(BigDecimal.ZERO) == 0) {
                    sheathThicknessStr = "0";
                }
                String sheath22ThicknessStr = ecuOffer.getSheath22Thickness().multiply(new BigDecimal("1000")).stripTrailingZeros().toPlainString();// 护套厚度
                if (ecuOffer.getSheath22Thickness().compareTo(BigDecimal.ZERO) == 0) {
                    sheath22ThicknessStr = "0";
                }

                String micaTypeNameStr;// 云母带类型
                if (ecuOffer.getEcbMicatape() != null) {
                    micaTypeNameStr = ecuOffer.getEcbMicatape().getAbbreviation();// 云母带类型
                } else {
                    micaTypeNameStr = "";// 云母带类型
                }
                String micatapeThicknessStr = ecuOffer.getMicatapeThickness().multiply(new BigDecimal("1000")).stripTrailingZeros().toPlainString();// 云母带厚度厚度
                if (ecuOffer.getMicatapeThickness().compareTo(BigDecimal.ZERO) == 0) {
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
                    cableStrandStr = ecuOffer.getCableStrand().stripTrailingZeros().toPlainString();
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
                dataRow.createCell(24).setCellValue(micaTypeNameStr);// 云母带类型
                dataRow.createCell(25).setCellValue(micatapeThicknessStr);// 云母带厚度
                dataRow.createCell(26).setCellValue(infillingNameStr);// 填充物
                dataRow.createCell(27).setCellValue(cableStrandStr);// 成缆系数
                dataRow.setHeight((short) 400);
            }
            sortId++;
        }
        // 设置下载时客户端Excel的名称   （上面注释的改进版本，上面的中文不支持）
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((excelName).getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ".xlsx");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
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
        String[] listCore = coreStr.split(",");
        // log.info("listCore + " + CommonFunction.getGson().toJson(listCore));
        String areaStr = ecuoProgramme.getAreaStr();
        String[] listArea = areaStr.split(",");
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
    @Transactional(rollbackFor = Exception.class)
    public List<Integer> dealAddPercentProgramme(ProgrammeBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcuOffer record = new EcuOffer();
        record.setEcqulId(ecqulId);
        List<EcuOffer> listOffer = ecuOfferService.getList(record);
        Integer ecuopId = bo.getEcuopId();
        EcuoProgramme ecuoProgramme = ecuoProgrammeModel.getObjectPassEcuopId(ecuopId);
        String coreStr = ecuoProgramme.getCoreStr();
        String[] listCore = coreStr.split(",");
        String areaStr = ecuoProgramme.getAreaStr();
        String[] listArea = areaStr.split(",");
        List<Integer> idArr = new ArrayList<>();

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
                //金额的范围
                BigDecimal defaultMoney = ecuOffer.getDefaultMoney();
                BigDecimal maxPrice = ecuoProgramme.getMaxPrice();
                BigDecimal minPrice = ecuoProgramme.getMinPrice();
                //
                // log.info("flagArea + " + floatArea);
                if (floatArea && defaultMoney.compareTo(maxPrice) <= 0 && defaultMoney.compareTo(minPrice) >= 0) {
                    record.setEcuoId(ecuOffer.getEcuoId());
                    record.setAddPercent(ecuoProgramme.getAddPercent());
                    ecuOfferService.update(record);
                    idArr.add(ecuOffer.getEcuoId());
                }
            }
        }
        return idArr;
    }

    // getStructureData
    public ProgrammeVo getStructureData(EcuOfferStructBo bo) {
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
        BigDecimal fireMicatapeRadius = BigDecimal.ZERO;
        BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;
        BigDecimal micatapeWeight = BigDecimal.ZERO;// 云母带重量
        BigDecimal micatapeMoney = BigDecimal.ZERO;// 云母带金额
        if (silkName.contains("N") || silkName.contains("NH")) {
            MicaTapeComputeBo micaTapeData = ecableEcuOfferFunction.getMicaTapeData(ecuOffer, fireDiameter, zeroDiameter);
            fireMicatapeRadius = micaTapeData.getFireMicaTapeRadius();
            zeroMicatapeRadius = micaTapeData.getZeroMicaTapeRadius();
            micatapeWeight = micaTapeData.getMicaTapeWeight();// 云母带重量
            micatapeMoney = micaTapeData.getMicaTapeMoney();// 云母带金额
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
        BigDecimal steelbandWeight = BigDecimal.ZERO;// 钢带重量
        BigDecimal steelbandMoney = BigDecimal.ZERO;// 钢带金额
        if (silkName.contains("22") || silkName.contains("23")) {
            // 钢带数据
            SteelBandComputeBo mapSteelband = ecableEcuOfferFunction.getSteelBandData(ecuOffer, externalDiameter);
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


    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(EcuOffer record) {
        EcuOffer recordEcuOffer = new EcuOffer();
        recordEcuOffer.setEcCompanyId(record.getEcCompanyId());
        recordEcuOffer.setEcqulId(record.getEcqulId());
        recordEcuOffer.setAreaStr(record.getAreaStr());
        EcuOffer object = ecuOfferService.getObject(recordEcuOffer);
        if (object != null) {
            ecuOfferService.update(record);
        } else {
            int sortId = 1;
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


    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcuOffer record = new EcuOffer();
        record.setEcCompanyId(ecCompanyId);
        ecuOfferService.delete(record);
    }

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
        MicaTapeComputeBo mapMicaTape = ecableEcuOfferFunction.getMicaTapeData(ecuOffer, fireDiameter, zeroDiameter);
        BigDecimal fireMicaTapeRadius = mapMicaTape.getFireMicaTapeRadius();
        BigDecimal zeroMicaTapeRadius = mapMicaTape.getZeroMicaTapeRadius();
        BigDecimal micaTapeWeight = mapMicaTape.getMicaTapeWeight();// 云母带重量
        BigDecimal micaTapeMoney = mapMicaTape.getMicaTapeMoney();// 云母带金额
        // 绝缘数据
        InsulationComputeBo mapInsulation = ecableEcuOfferFunction.getInsulationData(ecuOffer, fireDiameter,
                zeroDiameter,
                fireMicaTapeRadius,
                zeroMicaTapeRadius);
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
        SteelBandComputeBo mapSteelBand = ecableEcuOfferFunction.getSteelBandData(ecuOffer, externalDiameter);
        BigDecimal steelBandWeight = mapSteelBand.getSteelbandWeight();// 钢带重量
        BigDecimal steelBandMoney = mapSteelBand.getSteelbandMoney();// 钢带金额
        // 护套数据
        SheathComputeBo mapSheath = ecableEcuOfferFunction.getSheathData(ecuOffer, externalDiameter);
        BigDecimal sheathWeight = mapSheath.getSheathWeight();// 护套重量
        BigDecimal sheathMoney = mapSheath.getSheathMoney();// 护套金额
        BigDecimal defaultWeight = conductorWeight
                .add(micaTapeWeight)
                .add(bagWeight)
                .add(sheathWeight)
                .add(insulationWeight)
                .add(infillingWeight)
                .add(steelBandWeight)
                .add(sheathWeight);
        BigDecimal defaultMoney = conductorMoney
                .add(micaTapeMoney)
                .add(bagMoney)
                .add(sheathMoney)
                .add(insulationMoney)
                .add(infillingMoney)
                .add(steelBandMoney)
                .add(sheathMoney);
        record.setEcuoId(ecuOffer.getEcuoId());
        record.setDefaultWeight(defaultWeight);
        record.setDefaultMoney(defaultMoney);
        ecuOfferService.update(record);
    }
}
