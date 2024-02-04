package org.jeecg.modules.cable.model.systemOffer;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.systemOffer.offer.vo.EcOfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.model.systemEcable.*;
import org.jeecg.modules.cable.service.price.EcOfferService;
import org.jeecg.modules.cable.service.systemEcable.EcbMicaTapeService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.EcableFunction;
import org.jeecg.modules.cable.tools.ExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.jeecg.modules.cable.tools.EcableFunction.getExternalDiameter;

@Service
@Slf4j
public class EcOfferModel {
    final ExcelUtils excelUtils = new ExcelUtils();
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
    private EcbMicaTapeService ecbMicaTapeService;
    @Resource
    private EcbInfillingModel ecbInfillingModel;
    @Resource
    private EcqLevelService ecqLevelService;
    @Resource
    EcbConductorModel ecbConductorModel;//导体
    @Resource
    EcbMicaTapeModel ecbMicatapeModel;//云母带

    //导入导出的标题头
    private final String[] str = {"截面积", "成本加点", "粗芯丝号(mm)", "粗芯丝数", "粗芯丝绞合系数", "细芯丝号(mm)", "细芯丝数", "细芯丝绞合系数", "绝缘类型",
            "粗芯绝缘厚度(mm)", "细芯绝缘厚度(mm)", "非铠装绕包带类型", "非铠装绕包带厚度(mm)", "铠装绕包带类型", "铠装绕包带厚度(mm)", "屏蔽类型", "屏蔽厚度(mm)",
            "屏蔽系数(%)", "钢带类型", "钢带厚度(mm)", "钢带层数", "护套类型", "护套厚度(mm)", "铠装护套厚度(mm)", "云母带类型", "云母带厚度(mm)", "填充物类型", "成缆系数"};


    public List<EcOffer> getList(Integer ecqlId) {
        EcOffer record = new EcOffer();
        record.setEcqlId(ecqlId);
        return ecOfferService.getList(record);
    }

    // importDeal
    @Transactional(rollbackFor = Exception.class)
    public Result<?> importDeal(MultipartFile file, Integer ecqlId) throws Exception {
        // 信息
        assert file != null;
        int successNum = 0, failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        EcqLevel recordEcquLevel = new EcqLevel();
        recordEcquLevel.setEcqlId(ecqlId);
        EcqLevel ecqLevel = ecqLevelService.getObject(recordEcquLevel);
        if (ObjUtil.isNull(ecqLevel)) {
            throw new RuntimeException("质量等级不存在！");
        }
        Integer ecbcId = ecqLevel.getEcbcId();
        try {
            InputStream in = file.getInputStream();
            List<List<Object>> listob = excelUtils.getListByExcel(in, file.getOriginalFilename(), true);
            if (!listob.isEmpty()) {
                //查询第一行，匹配下标题头
                List<Object> objects1 = listob.get(0);
                if (objects1.size() != 28) {
                    throw new RuntimeException("当前导入数据列数不正确，无法执行导入");
                }
                for (int i = 0; i < 28; i++) {
                    Object o = objects1.get(i);
                    if (!String.valueOf(o).equals(str[i])) {
                        throw new RuntimeException("当前导入每列名称不正确，请下载模板后核对标每列名称");
                    }
                }
                listob.remove(0);
                //绝缘
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuInsulation = ecbInsulationModel.getInsulationPassInsulationStr();
                //包带
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuBag = ecbBagModel.getObjectPassBagStr();
                //屏蔽
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuShield = ecbShieldModel.getObjectPassShieldStr();
                //钢带
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuSteelband = ecbSteelbandModel.getObjectPassSteelBandStr();
                // 护套
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuSheath = ecbSheathModel.getObjectPassSheathStr();
                // 云母带
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuMicaTape = ecbMicaTapeService.getObjectPassMicaTape();
                // 填充物
                Pair<Map<String, Integer>, Map<String, Integer>> ecbuInfilling = ecbInfillingModel.getObjectPassInfillingStr();
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
                        BigDecimal fireSilkNumber = new BigDecimal(fireSilkNumberStr);//粗芯丝号
                        Integer fireRootNumber = Integer.parseInt(fireRootNumberStr);// 粗芯根数
                        BigDecimal fireStrand = BigDecimal.ZERO;// 粗芯丝绞合系数
                        if (!"0".equals(fireStrandStr) && !"".equals(fireStrandStr)) {
                            fireStrand = new BigDecimal(fireStrandStr);
                        }
                        BigDecimal zeroSilkNumber = BigDecimal.ZERO;
                        if (!"".equals(zeroSilkNumberStr)) {
                            zeroSilkNumber = new BigDecimal(zeroSilkNumberStr);
                        }
                        int zeroRootNumber = 0;
                        if (!"".equals(zeroRootNumberStr)) {
                            zeroRootNumber = Integer.parseInt(zeroRootNumberStr);
                        }
                        BigDecimal zeroStrand = BigDecimal.ZERO;// 细芯丝绞合系数
                        if (!"0".equals(zeroStrandStr) && !"".equals(zeroStrandStr)) {
                            zeroStrand = new BigDecimal(zeroStrandStr);
                        }
                        Integer ecbuiId = 0;// 绝缘
                        BigDecimal insulationFireThickness = BigDecimal.ZERO;
                        if (!"0".equals(insulationFireThicknessStr) && !"".equals(insulationFireThicknessStr)) {
                            insulationFireThickness = new BigDecimal(insulationFireThicknessStr);
                        }
                        BigDecimal insulationZeroThickness = BigDecimal.ZERO;
                        if (!"0".equals(insulationZeroThicknessStr) && !"".equals(insulationZeroThicknessStr)) {
                            insulationZeroThickness = new BigDecimal(insulationZeroThicknessStr);
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
                            bagThickness = new BigDecimal(bagThicknessStr);
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
                            bag22Thickness = new BigDecimal(bag22ThicknessStr);
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
                            shieldThickness = new BigDecimal(shieldThicknessStr);
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
                            steelbandThickness = new BigDecimal(steelBandThicknessStr);
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
                            sheathThickness = new BigDecimal(sheathThicknessStr);
                        }
                        if (!"".equals(sheath22ThicknessStr)) {
                            sheath22Thickness = new BigDecimal(sheath22ThicknessStr);
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
                            micatapeThickness = new BigDecimal(micaTapeThicknessStr);
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
                        EcOffer record = new EcOffer();
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
                        //根据规格获取成本库表
                        record = new EcOffer();
                        record.setEcqlId(ecqlId);
                        record.setAreaStr(areaStr);
                        ecOffer = ecOfferService.getObject(record);
                        record.setEcqlId(ecqlId);// 电缆质量等级ID
                        //record.setEcbc(ecbcId);// 导体ID

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
                        record.setEcbiId(ecbuiId);// 绝缘类型
                        record.setInsulationFireThickness(insulationFireThickness);// 粗芯绝缘厚度
                        record.setInsulationZeroThickness(insulationZeroThickness);// 细芯绝缘厚度
                        record.setEcbbId(ecbubId);// 包带类型
                        record.setBagThickness(bagThickness);// 包带厚度
                        record.setEcbb22Id(ecbub22Id);// 铠装包带类型
                        record.setBag22Thickness(bag22Thickness);// 铠装包带厚度
                        record.setEcbShieldId(ecbusId);// 屏蔽类型
                        record.setShieldThickness(shieldThickness);// 屏蔽厚度
                        record.setShieldPercent(shieldPercent);// 屏蔽编织系数
                        record.setEcbsbId(ecbusbId);// 钢带类型
                        record.setSteelbandThickness(steelbandThickness);// 钢带厚度
                        record.setSteelbandStorey(steelbandStorey);// 钢带层数
                        record.setEcbSheathId(ecbuSheathId);// 护套类型
                        record.setSheathThickness(sheathThickness);// 护套厚度
                        record.setSheath22Thickness(sheath22Thickness);// 铠装护套厚度
                        record.setEcbmId(ecbumId);// 云母带类型
                        record.setMicatapeThickness(micatapeThickness);// 云母带厚度
                        record.setFireStrand(fireStrand);// 粗芯绞合系数
                        record.setZeroStrand(zeroStrand);// 细芯绞合系数
                        record.setEcbinId(ecbuinId);// 填充物类型
                        record.setEcbswId(ecbuswId);// 钢丝类型
                        record.setSteelwireMembrance(steelwireMembrance);// 钢丝过膜
                        record.setSteelwirePress(steelwirePress);// 钢丝压型
                        record.setCableStrand(cableStrand);// 成缆绞合系数
                        record.setDefaultWeight(BigDecimal.ZERO);// 默认重量
                        record.setDefaultMoney(BigDecimal.ZERO);// 默认金额
                        // log.info("record + " + CommonFunction.getGson().toJson(record));
                        if (ecOffer != null) {
                            record.setEcoId(ecOffer.getEcoId());
                            ecOfferService.update(record);
                        } else {
                            //插入的写入排序
                            record.setSortId(sortId);
                            ecOfferService.insert(record);
                        }
                        dealDefaultWeightAndDefaultMoney(ecqlId, areaStr);// 修改默认重量和金额
                        //ecuoCoreModel.deal(ecqulId, areaStr);// 添加芯数表
                        //ecuoAreaModel.load(ecqulId, areaStr);// 添加平方数表
                        successMsg.append("<br/>成本库表 " + "第" + i + "行" + "导入成功");
                        successNum++;
                    } catch (Exception e) {
                        failureMsg.append("<br/>成本库表 " + "第" + i + "行" + "导入出错");
                        failureNum++;
                    }
                }
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
        //loadArea(ecCompanyId, ecqulId);// 加载质量等级对应的截面库ecuArea
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return Result.ok(successMsg.toString());
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
        //创建电缆对象
        Cable cable = new Cable(ecOffer.getAreaStr());
        // 导体数据
        EcqLevel level = new EcqLevel();
        level.setEcqlId(ecOffer.getEcqlId());
        EcqLevel ecqLevel = ecqLevelService.getObject(level);
        EcbConductor ecbConductor = ecbConductorModel.getObjectPassEcbcId(ecqLevel.getEcbcId());
        cable.setConductorMaterial(
                ecbConductor.getDensity(), ecbConductor.getUnitPrice(),
                ecOffer.getFireRootNumber(), ecOffer.getZeroRootNumber(),
                ecOffer.getFireSilkNumber(), ecOffer.getZeroSilkNumber(),
                ecOffer.getFireStrand(), ecOffer.getZeroStrand(),
                BigDecimal.ONE
        );
        ConductorMaterial conductorMaterial = cable.getConductorMaterial();
        BigDecimal conductorWeight = conductorMaterial.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = conductorMaterial.getConductorMoney();// 导体金额
        // 云母带数据
        BigDecimal micatapeWeight = BigDecimal.ZERO;// 云母带重量
        BigDecimal micatapeMoney = BigDecimal.ZERO;// 云母带金额
        if (ecOffer.getEcbmId() != 0) {
            EcbMicaTape ecbuMicaTape = ecbMicatapeModel.getObjectPassEcbmId(ecOffer.getEcbmId());
            cable.addInternalMaterial(ecbuMicaTape.getDensity(),
                    ecbuMicaTape.getUnitPrice(), BigDecimal.ONE,
                    ecOffer.getMicatapeThickness(), ecOffer.getMicatapeThickness());
            List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
            InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);
            micatapeWeight = internalMaterial1.getMaterialWeight();// 云母带重量
            micatapeMoney = internalMaterial1.getMaterialMoney();// 云母带金额
        }
        // 绝缘数据
        //InternalComputeBo mapInsulation = ecableEcOfferFunction
        //        .getInsulationData(ecOffer, fireDiameter, zeroDiameter, fireMicatapeRadius, zeroMicaTapeRadius);
        BigDecimal insulationWeight = BigDecimal.ZERO;// 绝缘重量
        BigDecimal insulationMoney = BigDecimal.ZERO;// 绝缘金额
        if (ecOffer.getEcbiId() != 0) {
            EcbInsulation ecbuInsulation = ecbInsulationModel.getObjectPassEcbiId(ecOffer.getEcbiId());
            BigDecimal insulationFireThickness = ecOffer.getInsulationFireThickness();//粗芯绝缘厚度
            BigDecimal insulationZeroThickness = ecOffer.getInsulationZeroThickness();//细芯绝缘厚度
            cable.addInternalMaterial(ecbuInsulation.getDensity(),
                    ecbuInsulation.getUnitPrice(), BigDecimal.ONE,
                    insulationFireThickness, insulationZeroThickness);
            List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
            InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);

            insulationWeight = internalMaterial1.getMaterialWeight();// 绝缘重量
            insulationMoney = internalMaterial1.getMaterialMoney();// 绝缘金额
        }
        // 填充物数据
        //InfillingComputeBo mapInfilling = ecableEcOfferFunction.getInfillingData(ecOffer, fireDiameter, zeroDiameter);
        //BigDecimal externalDiameter = mapInfilling.getExternalDiameter();
        BigDecimal infillingWeight = BigDecimal.ZERO;// 填充物重量
        BigDecimal infillingMoney = BigDecimal.ZERO;// 填充物金额
        if (ecOffer.getEcbinId() != 0) {
            EcbInfilling ecbInfilling = ecbInfillingModel.getObjectPassEcbinId(ecOffer.getEcbinId());
            cable.setInfillingMaterial(ecbInfilling.getDensity(), ecbInfilling.getUnitPrice());
            InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
            infillingWeight = infillingMaterial.getInfillingWeight();// 填充物重量
            infillingMoney = infillingMaterial.getInfillingMoney();// 填充物金额
        }
        // 包带数据
        BigDecimal bagWeight = BigDecimal.ZERO;// 包带重量
        BigDecimal bagMoney = BigDecimal.ZERO;// 包带金额
        if (ecOffer.getEcbbId() != 0) {
            EcbBag ecbBag = ecbBagModel.getObjectPassEcbbId(ecOffer.getEcbbId());
            cable.addExternalMaterials(ecbBag.getDensity(), ecbBag.getUnitPrice(), BigDecimal.valueOf(1), ecOffer.getBagThickness());
            List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
            ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
            bagWeight = externalMaterial.getMaterialWeight();// 包带重量
            bagMoney = externalMaterial.getMaterialMoney();// 包带金额
        }
        // 钢带数据
        //ExternalComputeBo mapSteelBand = ecableEcOfferFunction.getSteelBandData(ecOffer, externalDiameter);
        BigDecimal steelBandWeight = BigDecimal.ZERO;// 钢带重量
        BigDecimal steelBandMoney = BigDecimal.ZERO;// 钢带金额
        if (ecOffer.getEcbsbId() != 0) {
            EcbSteelBand ecbuSteelband = ecbSteelbandModel.getObjectPassEcbsbId(ecOffer.getEcbsbId());
            cable.addExternalMaterials(ecbuSteelband.getDensity(), ecbuSteelband.getUnitPrice(), BigDecimal.ONE, ecOffer.getSteelbandThickness());
            List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
            ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
            steelBandWeight = externalMaterial.getMaterialWeight();// 钢带重量
            steelBandMoney = externalMaterial.getMaterialMoney();// 钢带金额
        }

        // 护套数据
        //ExternalComputeBo mapSheath = ecableEcOfferFunction.getSheathData(ecOffer, externalDiameter);
        BigDecimal sheathWeight = BigDecimal.ZERO;// 护套重量
        BigDecimal sheathMoney = BigDecimal.ZERO;// 护套金额
        BigDecimal sheathThickness = ecOffer.getSheathThickness();
        if (ecOffer.getEcbSheathId() != 0 && sheathThickness.compareTo(BigDecimal.ZERO) != 0) {
            EcbSheath ecbSheath = ecbSheathModel.getObjectPassEcbsId(ecOffer.getEcbSheathId());
            BigDecimal density = ecbSheath.getDensity();
            BigDecimal unitPrice = ecbSheath.getUnitPrice();
            cable.addExternalMaterials(density, unitPrice, BigDecimal.ONE, sheathThickness);
            List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
            ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
            sheathWeight = externalMaterial.getMaterialWeight();// 护套重量
            sheathMoney = externalMaterial.getMaterialMoney();// 护套金额
        }

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
            record.setEcbSheathId(bo.getEcbsid());// 护套类型
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

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
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
        //执行具体的删除
        EcOffer record = new EcOffer();
        record.setEcoId(ecoId);
        ecOfferService.delete(record);
        //loadArea(sysUser.getEcCompanyId(), ecqlId);// 加载质量等级对应的截面库ecuArea
    }

    public ProgrammeVo getStructureData(EcOfferStructBo bo) {
        Integer ecoId = bo.getEcoId();
        String silkName = bo.getSilkName();
        EcOffer ecOffer = getObjectPassEcoId(ecoId);
        //// 导体数据
        //ConductorComputeExtendBo mapConductor = ecableEcOfferFunction.getConductorData(ecOffer);
        //BigDecimal fireDiameter = mapConductor.getFireDiameter();
        //BigDecimal zeroDiameter = mapConductor.getZeroDiameter();
        //BigDecimal conductorWeight = mapConductor.getConductorWeight();// 导体重量
        //BigDecimal conductorMoney = mapConductor.getConductorMoney();// 导体金额
        //// 云母带数据
        //BigDecimal fireMicatapeRadius = BigDecimal.ZERO;
        //BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;
        //BigDecimal micatapeWeight = BigDecimal.ZERO;// 云母带重量
        //BigDecimal micatapeMoney = BigDecimal.ZERO;// 云母带金额
        //if (silkName.contains("N") || silkName.contains("NH")) {
        //    InternalComputeBo micaTapeData = ecableEcOfferFunction.getMicaTapeData(ecOffer, fireDiameter, zeroDiameter);
        //    fireMicatapeRadius = micaTapeData.getFireRadius();
        //    zeroMicatapeRadius = micaTapeData.getZeroRadius();
        //    micatapeWeight = micaTapeData.getMaterialWeight();// 云母带重量
        //    micatapeMoney = micaTapeData.getMaterialMoney();// 云母带金额
        //}
        //// 绝缘数据
        //InternalComputeBo mapInsulation = ecableEcOfferFunction.getInsulationData(ecOffer, fireDiameter,
        //        zeroDiameter,
        //        fireMicatapeRadius,
        //        zeroMicatapeRadius);
        //BigDecimal insulationWeight = mapInsulation.getMaterialWeight();// 绝缘重量
        //BigDecimal insulationMoney = mapInsulation.getMaterialMoney();// 绝缘金额
        //// 填充物数据
        //InfillingComputeBo mapInfilling = ecableEcOfferFunction.getInfillingData(ecOffer, fireDiameter, zeroDiameter);
        //BigDecimal externalDiameter = mapInfilling.getExternalDiameter();
        //BigDecimal infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
        //BigDecimal infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额
        //// 包带数据
        //ExternalComputeBo mapBag = ecableEcOfferFunction.getBagData(ecOffer, externalDiameter);
        //BigDecimal bagWeight = mapBag.getMaterialWeight();// 包带重量
        //BigDecimal bagMoney = mapBag.getMaterialMoney();// 包带金额
        //// 钢带数据
        //BigDecimal steelbandWeight = BigDecimal.ZERO;// 钢带重量
        //BigDecimal steelbandMoney = BigDecimal.ZERO;// 钢带金额
        //if (silkName.contains("22") || silkName.contains("23")) {
        //    // 钢带数据
        //    ExternalComputeBo mapSteelband = ecableEcOfferFunction.getSteelBandData(ecOffer, externalDiameter);
        //    steelbandWeight = mapSteelband.getMaterialWeight();// 钢带重量
        //    steelbandMoney = mapSteelband.getMaterialMoney();// 钢带金额
        //}
        //
        //// 护套数据
        //ExternalComputeBo mapSheath = ecableEcOfferFunction.getSheathData(ecOffer, externalDiameter);
        //BigDecimal sheathWeight = mapSheath.getMaterialWeight();// 护套重量
        //BigDecimal sheathMoney = mapSheath.getMaterialMoney();// 护套金额
        //创建电缆对象
        Cable cable = new Cable(ecOffer.getAreaStr());
        // 导体数据
        EcqLevel level = new EcqLevel();
        level.setEcqlId(ecOffer.getEcqlId());
        EcqLevel ecqLevel = ecqLevelService.getObject(level);
        EcbConductor ecbConductor = ecbConductorModel.getObjectPassEcbcId(ecqLevel.getEcbcId());
        cable.setConductorMaterial(
                ecbConductor.getDensity(), ecbConductor.getUnitPrice(),
                ecOffer.getFireRootNumber(), ecOffer.getZeroRootNumber(),
                ecOffer.getFireSilkNumber(), ecOffer.getZeroSilkNumber(),
                ecOffer.getFireStrand(), ecOffer.getZeroStrand(),
                BigDecimal.ONE
        );
        ConductorMaterial conductorMaterial = cable.getConductorMaterial();
        BigDecimal conductorWeight = conductorMaterial.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = conductorMaterial.getConductorMoney();// 导体金额
        // 云母带数据
        BigDecimal micatapeWeight = BigDecimal.ZERO;// 云母带重量
        BigDecimal micatapeMoney = BigDecimal.ZERO;// 云母带金额
        if (ecOffer.getEcbmId() != 0) {
            EcbMicaTape ecbuMicaTape = ecbMicatapeModel.getObjectPassEcbmId(ecOffer.getEcbmId());
            cable.addInternalMaterial(ecbuMicaTape.getDensity(),
                    ecbuMicaTape.getUnitPrice(), BigDecimal.ONE,
                    ecOffer.getMicatapeThickness(), ecOffer.getMicatapeThickness());
            List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
            InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);
            micatapeWeight = internalMaterial1.getMaterialWeight();// 云母带重量
            micatapeMoney = internalMaterial1.getMaterialMoney();// 云母带金额
        }
        // 绝缘数据
        //InternalComputeBo mapInsulation = ecableEcOfferFunction
        //        .getInsulationData(ecOffer, fireDiameter, zeroDiameter, fireMicatapeRadius, zeroMicaTapeRadius);
        BigDecimal insulationWeight = BigDecimal.ZERO;// 绝缘重量
        BigDecimal insulationMoney = BigDecimal.ZERO;// 绝缘金额
        if (ecOffer.getEcbiId() != 0) {
            EcbInsulation ecbuInsulation = ecbInsulationModel.getObjectPassEcbiId(ecOffer.getEcbiId());
            BigDecimal insulationFireThickness = ecOffer.getInsulationFireThickness();//粗芯绝缘厚度
            BigDecimal insulationZeroThickness = ecOffer.getInsulationZeroThickness();//细芯绝缘厚度
            cable.addInternalMaterial(ecbuInsulation.getDensity(),
                    ecbuInsulation.getUnitPrice(), BigDecimal.ONE,
                    insulationFireThickness, insulationZeroThickness);
            List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
            InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);

            insulationWeight = internalMaterial1.getMaterialWeight();// 绝缘重量
            insulationMoney = internalMaterial1.getMaterialMoney();// 绝缘金额
        }
        // 填充物数据
        //InfillingComputeBo mapInfilling = ecableEcOfferFunction.getInfillingData(ecOffer, fireDiameter, zeroDiameter);
        //BigDecimal externalDiameter = mapInfilling.getExternalDiameter();
        BigDecimal infillingWeight = BigDecimal.ZERO;// 填充物重量
        BigDecimal infillingMoney = BigDecimal.ZERO;// 填充物金额
        if (ecOffer.getEcbinId() != 0) {
            EcbInfilling ecbInfilling = ecbInfillingModel.getObjectPassEcbinId(ecOffer.getEcbinId());
            cable.setInfillingMaterial(ecbInfilling.getDensity(), ecbInfilling.getUnitPrice());
            InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
            infillingWeight = infillingMaterial.getInfillingWeight();// 填充物重量
            infillingMoney = infillingMaterial.getInfillingMoney();// 填充物金额
        }
        // 包带数据
        BigDecimal bagWeight = BigDecimal.ZERO;// 包带重量
        BigDecimal bagMoney = BigDecimal.ZERO;// 包带金额
        if (ecOffer.getEcbbId() != 0) {
            EcbBag ecbBag = ecbBagModel.getObjectPassEcbbId(ecOffer.getEcbbId());
            cable.addExternalMaterials(ecbBag.getDensity(), ecbBag.getUnitPrice(), BigDecimal.valueOf(1), ecOffer.getBagThickness());
            List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
            ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
            bagWeight = externalMaterial.getMaterialWeight();// 包带重量
            bagMoney = externalMaterial.getMaterialMoney();// 包带金额
        }
        // 钢带数据
        //ExternalComputeBo mapSteelBand = ecableEcOfferFunction.getSteelBandData(ecOffer, externalDiameter);
        BigDecimal steelBandWeight = BigDecimal.ZERO;// 钢带重量
        BigDecimal steelBandMoney = BigDecimal.ZERO;// 钢带金额
        if (ecOffer.getEcbsbId() != 0) {
            EcbSteelBand ecbuSteelband = ecbSteelbandModel.getObjectPassEcbsbId(ecOffer.getEcbsbId());
            cable.addExternalMaterials(ecbuSteelband.getDensity(), ecbuSteelband.getUnitPrice(), BigDecimal.ONE, ecOffer.getSteelbandThickness());
            List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
            ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
            steelBandWeight = externalMaterial.getMaterialWeight();// 钢带重量
            steelBandMoney = externalMaterial.getMaterialMoney();// 钢带金额
        }

        // 护套数据
        //ExternalComputeBo mapSheath = ecableEcOfferFunction.getSheathData(ecOffer, externalDiameter);
        BigDecimal sheathWeight = BigDecimal.ZERO;// 护套重量
        BigDecimal sheathMoney = BigDecimal.ZERO;// 护套金额
        BigDecimal sheathThickness = ecOffer.getSheathThickness();
        if (ecOffer.getEcbSheathId() != 0 && sheathThickness.compareTo(BigDecimal.ZERO) != 0) {
            EcbSheath ecbSheath = ecbSheathModel.getObjectPassEcbsId(ecOffer.getEcbSheathId());
            BigDecimal density = ecbSheath.getDensity();
            BigDecimal unitPrice = ecbSheath.getUnitPrice();
            cable.addExternalMaterials(density, unitPrice, BigDecimal.ONE, sheathThickness);
            List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
            ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
            sheathWeight = externalMaterial.getMaterialWeight();// 护套重量
            sheathMoney = externalMaterial.getMaterialMoney();// 护套金额
        }

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
        programmeVo.setSteelBandWeight(steelBandWeight);
        programmeVo.setSteelBandMoney(steelBandWeight);
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

    public void exportData(HttpServletResponse response, Integer ecqlId) throws IOException {
        EcqLevel recordEcquLevel = new EcqLevel();
        recordEcquLevel.setEcqlId(ecqlId);
        EcqLevel ecqLevel = ecqLevelService.getObject(recordEcquLevel);
        String excelName = ecqLevel.getName();
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
        for (int i = 0; i < 28; i++) {
            titleRow.createCell(i).setCellValue(str[i]);
        }
        titleRow.setHeight((short) 400);
        EcOffer recordEcuOffer = new EcOffer();
        recordEcuOffer.setEcqlId(ecqlId);
        List<EcOffer> list = ecOfferService.getList(recordEcuOffer);
        int sortId = 1;
        for (EcOffer offer : list) {
            if (sortId < list.size()) {
                log.info("sortId + " + sortId);
                String addPercentStr = (offer.getAddPercent().multiply(new BigDecimal("100")))
                        .setScale(0, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();// 成本加点
                if (offer.getAddPercent().compareTo(BigDecimal.ZERO) == 0) {
                    addPercentStr = "0%";
                } else {
                    addPercentStr = addPercentStr + "%";
                }
                String areaStr = offer.getAreaStr();// 截面积
                String fireSilkNumberStr = offer.getFireSilkNumber().stripTrailingZeros().toPlainString();// 粗芯丝号
                String fireRootNumberStr = String.valueOf(offer.getFireRootNumber());// 粗芯丝数
                String fireStrandStr = offer.getFireStrand().stripTrailingZeros().toPlainString();// 粗芯丝数
                if (offer.getFireSilkNumber().compareTo(BigDecimal.ZERO) == 0) {
                    fireSilkNumberStr = "";
                    fireRootNumberStr = "";
                    fireStrandStr = "";
                }
                String zeroSilkNumberStr = offer.getZeroSilkNumber().stripTrailingZeros().toPlainString();// 细芯丝号
                String zeroRootNumberStr = String.valueOf(offer.getZeroRootNumber());// 细芯根数
                String zeroStrandStr = offer.getZeroStrand().stripTrailingZeros().toPlainString();// 细芯绞系数
                if (offer.getZeroSilkNumber().compareTo(BigDecimal.ZERO) == 0) {
                    zeroSilkNumberStr = "";
                    zeroRootNumberStr = "";
                    zeroStrandStr = "";
                }
                String insulationNameStr;// 绝缘类型
                if (offer.getEcbInsulation() != null) {
                    insulationNameStr = offer.getEcbInsulation().getAbbreviation();// 绝缘类型
                } else {
                    insulationNameStr = "";// 绝缘类型
                }
                String insulationFireThicknessStr = offer.getInsulationFireThickness().stripTrailingZeros().toPlainString();// 粗芯厚度
                if (offer.getInsulationFireThickness().compareTo(BigDecimal.ZERO) == 0) {
                    insulationFireThicknessStr = "0";
                }
                String insulationZeroThicknessStr = offer.getInsulationZeroThickness()
                        .stripTrailingZeros().toPlainString();// 细芯厚度
                if (offer.getInsulationZeroThickness().compareTo(BigDecimal.ZERO) == 0) {
                    insulationZeroThicknessStr = "0";
                }
                String bagNameStr;// 包带类型
                if (offer.getEcbBag() != null) {
                    bagNameStr = offer.getEcbBag().getAbbreviation();// 包带类型
                } else {
                    bagNameStr = "";// 包带类型
                }
                String bagThicknessStr = offer.getBagThickness().stripTrailingZeros().toPlainString();// 包带厚度
                if (offer.getBagThickness().compareTo(BigDecimal.ZERO) == 0) {
                    bagThicknessStr = "0";
                }
                String bag22NameStr;// 包带类型
                if (offer.getEcb22Bag() != null) {
                    bag22NameStr = offer.getEcb22Bag().getAbbreviation();// 包带类型
                } else {
                    bag22NameStr = "";// 包带类型
                }
                String bag22ThicknessStr = "0";
                if (offer.getBag22Thickness() != null && offer.getBag22Thickness().compareTo(BigDecimal.ZERO) != 0) {
                    bag22ThicknessStr = offer.getBag22Thickness().stripTrailingZeros().toPlainString();// 包带厚度
                }
                String shieldNameStr;// 屏蔽类型
                if (offer.getEcbShield() != null) {
                    shieldNameStr = offer.getEcbShield().getAbbreviation();// 屏蔽类型
                } else {
                    shieldNameStr = "";// 屏蔽类型
                }
                String shieldThicknessStr = offer.getShieldThickness().stripTrailingZeros().toPlainString();// 屏蔽厚度
                if (offer.getShieldThickness().compareTo(BigDecimal.ZERO) == 0) {
                    shieldThicknessStr = "0";
                }
                String shieldPercentStr = (offer.getShieldPercent()
                        .multiply(new BigDecimal("100"))).setScale(0, RoundingMode.HALF_UP)
                        .stripTrailingZeros().toPlainString();// 屏蔽编织密度(屏蔽系数(%))
                if (offer.getShieldPercent().compareTo(BigDecimal.ZERO) == 0) {
                    shieldPercentStr = "0%";
                } else {
                    shieldPercentStr = shieldPercentStr + "%";
                }
                String steelbandNameStr;// 钢带类型
                if (offer.getEcbSteelband() != null) {
                    steelbandNameStr = offer.getEcbSteelband().getAbbreviation();// 钢带类型
                } else {
                    steelbandNameStr = "";// 钢带类型
                }
                String steelbandThicknessStr = offer.getSteelbandThickness().stripTrailingZeros().toPlainString();// 钢带厚度
                if (offer.getSteelbandThickness().compareTo(BigDecimal.ZERO) == 0) {
                    steelbandThicknessStr = "0";
                }
                String steelbandStoreyStr = offer.getSteelbandStorey().toString();// 钢带层数
                if (offer.getSteelbandStorey() == 0) {
                    steelbandStoreyStr = "0";
                }
                String sheathNameStr;// 护套类型
                if (offer.getEcbSheath() != null) {
                    sheathNameStr = offer.getEcbSheath().getAbbreviation();// 护套类型
                } else {
                    sheathNameStr = "";// 护套类型
                }
                String sheathThicknessStr = offer.getSheathThickness().stripTrailingZeros().toPlainString();// 护套厚度
                if (offer.getSheathThickness().compareTo(BigDecimal.ZERO) == 0) {
                    sheathThicknessStr = "0";
                }
                String sheath22ThicknessStr = offer.getSheath22Thickness().stripTrailingZeros().toPlainString();// 护套厚度
                if (offer.getSheath22Thickness().compareTo(BigDecimal.ZERO) == 0) {
                    sheath22ThicknessStr = "0";
                }
                String micatapeThicknessStr = offer.getMicatapeThickness().stripTrailingZeros().toPlainString();// 云母带厚度厚度
                if (offer.getMicatapeThickness().compareTo(BigDecimal.ZERO) == 0) {
                    micatapeThicknessStr = "0";
                }

                String micaTypeNameStr;// 云母带类型
                if (offer.getEcbMicatape() != null) {
                    micaTypeNameStr = offer.getEcbMicatape().getAbbreviation();// 云母带类型
                } else {
                    micaTypeNameStr = "";// 云母带类型
                }

                String infillingNameStr;// 填充物类型
                if (offer.getEcbInfilling() != null) {
                    infillingNameStr = offer.getEcbInfilling().getAbbreviation();// 填充物类型
                } else {
                    infillingNameStr = "";// 填充物类型
                }
                String cableStrandStr = "";// 成缆系数
                if (offer.getCableStrand() != null) {
                    cableStrandStr = offer.getCableStrand().stripTrailingZeros().toPlainString();
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

    public void exportTemplate(HttpServletResponse response) {
        try {
            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();
            // 创建工作表
            Sheet sheet = workbook.createSheet("Sheet1");
            // 创建表头行
            Row headerRow0 = sheet.createRow(0);
            // 设置单元格样式
            CellStyle mergedCellStyle = workbook.createCellStyle();
            mergedCellStyle.setAlignment(HorizontalAlignment.CENTER);
            mergedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            for (int i = 0; i < str.length; i++) {
                Cell cell = headerRow0.createCell(i);
                cell.setCellValue(str[i]);
                cell.setCellStyle(mergedCellStyle);
                sheet.setColumnWidth(i, 25 * 256); // 256是POI中列宽的基本单位，乘以字符宽度
            }
            // 设置响应头
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    new String(("成本库表").getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ".xlsx");
            // 获取输出流
            OutputStream outputStream = response.getOutputStream();
            // 将工作簿写入输出流
            workbook.write(outputStream);
            // 刷新并关闭输出流
            outputStream.flush();
            outputStream.close();
            // 关闭工作簿
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("生成导入模板失败");
        }
    }
}
