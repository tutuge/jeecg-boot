package org.jeecg.modules.cable.model.systemOffer;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsBaseBo;
import org.jeecg.modules.cable.controller.systemOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.systemOffer.offer.vo.EcOfferVo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelBaseBo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.MaterialVo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.domain.computeBo.*;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.model.systemEcable.EcbMaterialTypeModel;
import org.jeecg.modules.cable.model.systemEcable.EcbMaterialsModel;
import org.jeecg.modules.cable.model.systemQuality.EcqLevelModel;
import org.jeecg.modules.cable.service.price.EcOfferService;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class EcOfferModel {
    final ExcelUtils excelUtils = new ExcelUtils();
    @Resource
    EcOfferService ecOfferService;
    @Resource
    private EcqLevelService ecqLevelService;
    @Resource
    EcbMaterialsModel ecbMaterialsModel;//材料
    @Resource
    EcbMaterialTypeModel ecbMaterialTypeModel;//材料类型
    @Resource
    private EcqLevelModel ecqLevelModel;
    @Resource
    private EcSilkService ecSilkService;


    //导入导出的标题头
    private final String[] str = {"截面积", "成本加点", "粗芯丝号(mm)", "粗芯丝数", "粗芯丝绞合系数", "细芯丝号(mm)", "细芯丝数", "细芯丝绞合系数", "绝缘类型",
            "粗芯绝缘厚度(mm)", "细芯绝缘厚度(mm)", "非铠装绕包带类型", "非铠装绕包带厚度(mm)", "铠装绕包带类型", "铠装绕包带厚度(mm)", "屏蔽类型", "屏蔽厚度(mm)",
            "屏蔽系数(%)", "钢带类型", "钢带厚度(mm)", "钢带层数", "护套类型", "护套厚度(mm)", "铠装护套厚度(mm)", "云母带类型", "云母带厚度(mm)", "填充物类型", "成缆系数"};


    public List<EcOffer> getList(Integer ecqlId) {
        EcOffer record = new EcOffer();
        record.setEcqlId(ecqlId);
        return ecOfferService.getList(record);
    }

    // 导入数据
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
        try {
            InputStream in = file.getInputStream();
            List<List<Object>> listob = excelUtils.getListByExcel(in, file.getOriginalFilename(), true);
            //获取标题头
            List<String> title = Lists.newArrayList("截面积", "成本加点");
            List<String> title1 = ecqLevelModel.getTitle(new EcqLevelBaseBo(ecqlId));
            title1.remove(0);//移除导体名称
            title.addAll(title1);
            // 表头长度
            int size = title.size();
            if (!listob.isEmpty()) {
                //查询第一行，匹配下标题头
                List<Object> objects1 = listob.get(0);
                if (objects1.size() != size) {
                    throw new RuntimeException("当前导入数据列数不正确，无法执行导入");
                }
                for (int i = 0; i < size; i++) {
                    Object o = objects1.get(i);
                    String s1 = String.valueOf(o);
                    String s = title.get(i);
                    if (!Objects.equals(s1, s)) {
                        throw new RuntimeException("当前导入每列名称不正确，请下载模板后核对标每列名称");
                    }
                }
                listob.remove(0);
                // 查询系列中材料类型
                EcSilk ecSilk = new EcSilk();
                ecSilk.setEcsId(ecqLevel.getEcsId());
                EcSilk object = ecSilkService.getObject(ecSilk);
                //对应质量等级的型号系列
                // -----注意：-------
                // 因为材料这里是动态的，但是是个半动态的样子，所以是按照下面顺序来的（注意这个需要和上面的getTitle配合上）
                // 第三个字段是导体的材料名，之后第十个字段开始是内部材料，内部材料每隔三个是材料名，然后是填充物字段名，
                // 然后是外部材料，然后每隔两个字段是外部材料名
                List<EcbMaterialType> materialTypesList = object.getMaterialTypesList();
                List<EcbMaterialType> internal = new ArrayList<>();//内部材料
                List<EcbMaterialType> external = new ArrayList<>();//外部材料
                boolean infill = false;
                EcbMaterialType conduct = new EcbMaterialType();
                EcbMaterialType ecbinfilling = new EcbMaterialType();
                for (EcbMaterialType type : materialTypesList) {
                    Integer materialType = type.getMaterialType();
                    if (materialType == 1) {
                        conduct = type;
                    } else if (materialType == 2) {
                        infill = true;
                        ecbinfilling = type;
                    } else {
                        if (!infill) {
                            internal.add(type);//内部材料
                        } else {
                            external.add(type); //外部材料
                        }
                    }
                }
                //查询材料的名称与id的映射
                Map<String, Integer> mapStr = ecbMaterialsModel.getMapStr();
                //整个成本库表用到的导体都是质量等级指定的导体
                Integer ecbcId = ecqLevel.getEcbcId();//导体id
                EcbMaterials conductorName = ecbMaterialsModel.getObject(new EcbMaterialsBaseBo(ecbcId));//查询导体名称
                for (int i = 0; i < listob.size(); i++) {
                    try {
                        List<Object> objects = listob.get(i);
                        if (objects.size() != size) {
                            failureMsg.append("<br/>成本库表 " + "第").append(i).append("行").append("导入出错");
                            failureNum++;
                            continue;
                        }
                        String areaStr = objects.get(0).toString();// 截面积
                        String addPercentStr = objects.get(1).toString();// 成本加点
                        //导体
                        //String conductorName = objects.get(2).toString();// 导体名称
                        String fireSilkNumberStr = objects.get(2).toString();// 粗芯丝号
                        String fireStrandStr = objects.get(3).toString();// 粗芯丝绞合系数
                        String fireRootNumberStr = objects.get(4).toString();// 粗芯根数
                        String zeroSilkNumberStr = objects.get(5).toString();// 细芯丝号
                        String zeroStrandStr = objects.get(6).toString();// 细芯丝绞合系数
                        String zeroRootNumberStr = objects.get(7).toString();// 细芯根数
                        //材料
                        Structure structure = new Structure();
                        //创建导体对象
                        Conductor conductor = new Conductor();
                        conductor.setId(ecbcId);
                        conductor.setFullName(conductorName.getFullName());
                        conductor.setMaterialTypeId(conduct.getId());
                        conductor.setMaterialTypeName(conduct.getFullName());
                        conductor.setFireSilkNumber(new BigDecimal(fireSilkNumberStr));
                        conductor.setFireStrand(new BigDecimal(fireStrandStr));
                        conductor.setFireRootNumber(Integer.valueOf(fireRootNumberStr));
                        conductor.setZeroSilkNumber(new BigDecimal(zeroSilkNumberStr));
                        conductor.setZeroStrand(new BigDecimal(zeroStrandStr));
                        conductor.setZeroRootNumber(Integer.valueOf(zeroRootNumberStr));
                        structure.setConductor(conductor);
                        //创建内部材料
                        int inCount = 8;
                        List<Internal> internals = new ArrayList<>();
                        for (int it = 0; it < internal.size(); it++) {
                            EcbMaterialType internalType = internal.get(it);
                            Internal inter = new Internal();
                            String interStr = objects.get(inCount + it * 4).toString();// 内部材料类型
                            Integer interId = mapStr.get(interStr);
                            inter.setId(interId);
                            inter.setFullName(interStr);
                            inter.setMaterialTypeId(internalType.getId());
                            inter.setMaterialTypeName(internalType.getFullName());
                            String interFire = objects.get(inCount + 1 + it * 4).toString();// 内部材料粗芯厚度
                            String interZero = objects.get(inCount + 2 + it * 4).toString();// 内部材料细芯厚度
                            String interFactor = objects.get(inCount + 3 + it * 4).toString();// 内部材料系数
                            BigDecimal interFireThickness = new BigDecimal(interFire);
                            BigDecimal interZeroThickness = new BigDecimal(interZero);
                            BigDecimal interFactorThickness = new BigDecimal(interFactor);
                            inter.setFireThickness(interFireThickness);
                            inter.setZeroThickness(interZeroThickness);
                            inter.setFactor(interFactorThickness);
                            internals.add(inter);
                        }
                        inCount = inCount + 4 * internal.size();
                        structure.setInternals(internals);

                        //填充物
                        if (ObjUtil.isNotNull(ecbinfilling)) {
                            Infilling infilling = new Infilling();
                            String infillStr = objects.get(inCount).toString();// 内部材料类型
                            Integer infillId = mapStr.get(infillStr);
                            infilling.setId(infillId);
                            infilling.setFullName(infillStr);
                            infilling.setMaterialTypeId(ecbinfilling.getId());
                            infilling.setMaterialTypeName(ecbinfilling.getFullName());
                            inCount = inCount + 1;
                            structure.setInfilling(infilling);
                        }

                        List<External> externals = new ArrayList<>();
                        for (int it = 0; it < external.size(); it++) {
                            EcbMaterialType externalType = external.get(it);
                            External exter = new External();
                            String exterStr = objects.get(inCount + it * 3).toString();// 外部材料类型
                            Integer exterId = mapStr.get(exterStr);
                            exter.setId(exterId);
                            exter.setFullName(exterStr);
                            exter.setMaterialTypeId(externalType.getId());
                            exter.setMaterialTypeName(externalType.getFullName());
                            String exterFire = objects.get(inCount + 1 + it * 3).toString();// 外部材料厚度
                            String exterFactor = objects.get(inCount + 2 + it * 3).toString();// 外部材料系数
                            BigDecimal exterThickness = new BigDecimal(exterFire);
                            BigDecimal exterFactorNumber = new BigDecimal(exterFactor);
                            exter.setThickness(exterThickness);
                            exter.setFactor(exterFactorNumber);
                            externals.add(exter);
                            structure.setExternals(externals);
                        }
                        // 成本加点
                        BigDecimal addPercent = BigDecimal.ZERO;
                        if (StrUtil.isNotBlank(addPercentStr)) {
                            String replace = addPercentStr.replace("%", "");
                            addPercent = new BigDecimal(replace).divide(BigDecimal.valueOf(100D), 2, RoundingMode.HALF_UP);
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
                        record.setStructure(structure);
                        record.setDefaultWeight(BigDecimal.ZERO);// 默认重量
                        record.setDefaultMoney(BigDecimal.ZERO);// 默认金额
                        record.convert(); //转换为json字符串
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
                        successMsg.append("<br/>成本库表 第").append(i).append("行").append("导入成功");
                        successNum++;
                    } catch (Exception e) {
                        log.error("导入失败-->", e);
                        failureMsg.append("<br/>成本库表 第").append(i).append("行").append("导入出错");
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
                log.error("文件导入失败-->", e);
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
    //public void loadSteelBandThicknessAndSheathThickness() {
    //    EcOffer record = new EcOffer();
    //    record.setEcsId(2);// yjv
    //    List<EcOffer> list = ecOfferService.getList(record);
    //    ConductorComputeBo conductorObj;
    //    BigDecimal steelbandThickness = BigDecimal.ZERO;
    //    BigDecimal sheathThickness;
    //    for (EcOffer ecOffer : list) {
    //        conductorObj = EcableFunction.getConductorData(ecOffer);
    //        BigDecimal fireDiameter = conductorObj.getFireDiameter();
    //        BigDecimal zeroDiameter = conductorObj.getZeroDiameter();
    //        BigDecimal wideDiameter = fireDiameter// 粗芯直径
    //                .add(ecOffer.getMicatapeThickness().multiply(new BigDecimal("2")))
    //                .add(ecOffer.getInsulationFireThickness().multiply(new BigDecimal("2")));
    //        BigDecimal fineDiameter = zeroDiameter// 细芯直径
    //                .add(ecOffer.getMicatapeThickness().multiply(new BigDecimal("2")))
    //                .add(ecOffer.getInsulationZeroThickness().multiply(new BigDecimal("2")));
    //        String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
    //        BigDecimal externalDiameter = getExternalDiameter(areaArr, wideDiameter, fineDiameter);// 外径
    //        // log.info("externalDiameter + " + externalDiameter);
    //        if (externalDiameter.compareTo(new BigDecimal("0.03")) < 1) {
    //            steelbandThickness = new BigDecimal("0.0002");
    //        } else if (externalDiameter.compareTo(new BigDecimal("0.03")) > 0
    //                && externalDiameter.compareTo(new BigDecimal("0.07")) < 1) {
    //            steelbandThickness = new BigDecimal("0.0005");
    //        } else if (externalDiameter.compareTo(new BigDecimal("0.07")) > 0) {
    //            steelbandThickness = new BigDecimal("0.0008");
    //        }
    //        // log.info("steelbandThickness + " + steelbandThickness);
    //        sheathThickness = externalDiameter
    //                .add(steelbandThickness.multiply(new BigDecimal("2")))
    //                .multiply(new BigDecimal("0.035"))
    //                .add(new BigDecimal("0.001"));
    //        record.setEcoId(ecOffer.getEcoId());
    //        record.setSteelbandThickness(steelbandThickness);
    //        record.setSheathThickness(sheathThickness);
    //        record.setSheath22Thickness(sheathThickness);
    //        // log.info(CommonFunction.getGson().toJson(record));
    //        ecOfferService.update(record);
    //    }
    //}


    //计算金额与重量
    public void dealDefaultWeightAndDefaultMoney(Integer ecqlId, String areaStr) {
        EcOffer record = new EcOffer();
        record.setEcqlId(ecqlId);
        record.setAreaStr(areaStr);
        EcOffer ecOffer = ecOfferService.getObject(record);
        BigDecimal defaultWeight = BigDecimal.ZERO; // 重量
        BigDecimal defaultMoney = BigDecimal.ZERO; // 金额
        //创建电缆对象
        Cable cable = new Cable(ecOffer.getAreaStr());
        // 导体数据
        EcqLevel level = new EcqLevel();
        level.setEcqlId(ecOffer.getEcqlId());
        EcqLevel ecqLevel = ecqLevelService.getObject(level);
        EcbMaterials ecbMaterials = ecbMaterialsModel.getObjectPassId(ecqLevel.getEcbcId());
        // 导体数据
        Conductor conductor = ecOffer.getConductor();
        cable.setConductorMaterial(
                ecbMaterials.getDensity(), ecbMaterials.getUnitPrice(),
                conductor.getFireRootNumber(), conductor.getZeroRootNumber(),
                conductor.getFireSilkNumber(), conductor.getZeroSilkNumber(),
                conductor.getFireStrand(), conductor.getZeroStrand(),
                BigDecimal.ONE
        );
        ConductorMaterial conductorMaterial = cable.getConductorMaterial();
        BigDecimal conductorWeight = conductorMaterial.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = conductorMaterial.getConductorMoney();// 导体金额
        defaultWeight = defaultWeight.add(conductorWeight);
        defaultMoney = defaultMoney.add(conductorMoney);
        //内部材料
        List<Internal> internals = ecOffer.getInternals();
        for (Internal internal : internals) {
            if (internal.getId() != null && internal.getId() != 0) {
                EcbMaterials internalMaterial = ecbMaterialsModel.getObjectPassId(internal.getId());
                cable.addInternalMaterial(internalMaterial.getDensity(), internalMaterial.getUnitPrice(),
                        internal.getFactor(), internal.getFireThickness(), internal.getZeroThickness());
                List<InternalMaterial> internalMaterialValue = cable.getInternalMaterial();
                InternalMaterial internalMaterial1 = internalMaterialValue.get(internalMaterialValue.size() - 1);
                BigDecimal internalWeight = internalMaterial1.getMaterialWeight();// 重量
                BigDecimal internalMoney = internalMaterial1.getMaterialMoney();// 金额
                defaultWeight = defaultWeight.add(internalWeight);
                defaultMoney = defaultMoney.add(internalMoney);
            }
        }
        // 填充物数据
        Infilling infilling = ecOffer.getInfilling();
        if (infilling.getId() != null && infilling.getId() != 0) {
            EcbMaterials infillMaterial = ecbMaterialsModel.getObjectPassId(infilling.getId());
            cable.setInfillingMaterial(infillMaterial.getDensity(), infillMaterial.getUnitPrice());
            InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
            BigDecimal infillingWeight = infillingMaterial.getInfillingWeight();// 填充物重量
            BigDecimal infillingMoney = infillingMaterial.getInfillingMoney();// 填充物金额
            defaultWeight = defaultWeight.add(infillingWeight);
            defaultMoney = defaultMoney.add(infillingMoney);
        }

        // 外部材料数据
        List<External> externals = ecOffer.getExternals();
        for (External external : externals) {
            if (external.getId() != null && external.getId() != 0) {
                EcbMaterials externalMaterial = ecbMaterialsModel.getObjectPassId(external.getId());
                cable.addExternalMaterials(externalMaterial.getDensity(), externalMaterial.getUnitPrice(),
                        external.getFactor(), external.getThickness());
                List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
                ExternalMaterial externalMaterialValue = externalMaterials.get(externalMaterials.size() - 1);
                BigDecimal externalWeight = externalMaterialValue.getMaterialWeight();// 重量
                BigDecimal externalMoney = externalMaterialValue.getMaterialMoney();// 金额
                defaultWeight = defaultWeight.add(externalWeight);
                defaultMoney = defaultMoney.add(externalMoney);
            }
        }

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
            //record.setFireSilkNumber(BigDecimal.ZERO);// 火丝丝号
            //record.setFireRootNumber(0);// 粗芯根数
            //record.setFireMembrance(0);// 粗芯过膜
            //record.setFirePress(BigDecimal.ZERO);// 粗芯压型
            //record.setZeroSilkNumber(BigDecimal.ZERO);// 细芯丝号
            //record.setZeroRootNumber(0);// 细芯根数
            //record.setZeroMembrance(0);// 细芯过膜
            //record.setZeroPress(BigDecimal.ZERO);// 细芯过型
            //record.setEcbiId(0);// 绝缘类型
            //record.setInsulationFireThickness(BigDecimal.ZERO);// 粗芯绝缘厚度
            //record.setInsulationZeroThickness(BigDecimal.ZERO);// 细芯绝缘厚度
            //record.setEcbbId(0);// 包带类型
            //record.setBagThickness(BigDecimal.ZERO);// 包带厚度
            //record.setEcbShieldId(0);// 屏蔽类型
            //record.setShieldThickness(BigDecimal.ZERO);// 屏蔽厚度
            //record.setShieldPercent(BigDecimal.ZERO);// 屏蔽编织系数
            //record.setEcbsbId(0);// 钢带类型
            //record.setSteelbandThickness(BigDecimal.ZERO);// 钢带厚度
            //record.setSteelbandStorey(0);// 钢带层数
            //// record.setEcbusid(0);//护套类型
            //record.setSheathThickness(BigDecimal.ZERO);// 护套厚度
            //record.setSheath22Thickness(BigDecimal.ZERO);// 铠装护套厚度
            //record.setEcbmId(0);// 云母带类型
            //record.setMicatapeThickness(BigDecimal.ZERO);// 云母带厚度
            //record.setFireStrand(BigDecimal.ZERO);// 粗芯绞合系数
            //record.setZeroStrand(BigDecimal.ZERO);// 细芯绞合系数
            //record.setEcbinId(0);// 填充物类型
            //record.setEcbswId(0);// 钢丝类型
            //record.setSteelwireMembrance(BigDecimal.ZERO);// 钢丝过膜
            //record.setSteelwirePress(BigDecimal.ZERO);// 钢丝压型
            ecOfferService.insert(record);
            //loadArea(sysUser.getEcCompanyId(), ecqulId);// 加载质量等级对应的截面库ecuArea
            dealDefaultWeightAndDefaultMoney(record.getEcqlId(), record.getAreaStr());
            msg = "插入数据成功";
        } else {
            record.setEcoId(ecoId);
            record.setAddPercent(bo.getAddPercent());
            record.setAreaStr(bo.getAreaStr());// 截面str
            //record.setFireSilkNumber(bo.getFireSilkNumber());// 粗芯丝号
            //record.setFireRootNumber(bo.getFireRootNumber());// 粗芯根数
            //record.setFireStrand(bo.getFireStrand());// 粗芯绞合系数
            //record.setZeroSilkNumber(bo.getZeroSilkNumber());// 细芯丝号
            //record.setZeroRootNumber(bo.getZeroRootNumber());// 细芯丝号
            //record.setZeroStrand(bo.getZeroStrand());// 细芯绞合系数
            //record.setEcbiId(bo.getEcbuiId());// 绝缘类型
            //record.setInsulationFireThickness(bo.getInsulationFireThickness());// 粗芯绝缘厚度
            //record.setInsulationZeroThickness(bo.getInsulationZeroThickness());// 细芯绝缘厚度
            //record.setEcbbId(bo.getEcbbId());// 包带类型
            //record.setBagThickness(bo.getBagThickness());// 包带厚度
            //record.setEcbShieldId(bo.getEcbsid());// 屏蔽类型
            //record.setShieldThickness(bo.getShieldThickness());// 屏蔽厚度
            //record.setShieldPercent(bo.getShieldPercent());// 屏蔽编织系数
            //record.setEcbsbId(bo.getEcbsbId());// 钢带类型
            //record.setSteelbandThickness(bo.getSteelbandThickness());// 钢带厚度
            //record.setSteelbandStorey(bo.getSteelbandStorey());// 钢带层数
            //record.setEcbSheathId(bo.getEcbsid());// 护套类型
            //record.setSheathThickness(bo.getSheathThickness());// 护套厚度
            //record.setSheath22Thickness(bo.getSheath22Thickness());// 护套厚度
            //record.setEcbmId(bo.getEcbmId());// 云母带类型
            //record.setMicatapeThickness(bo.getMicatapeThickness());// 云母带厚度
            //record.setEcbinId(bo.getEcbinId());// 填充物类型
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
        //材料信息
        List<MaterialVo> list = new ArrayList<>();
        //创建电缆对象
        Cable cable = new Cable(ecOffer.getAreaStr());
        // 导体数据
        EcqLevel level = new EcqLevel();
        level.setEcqlId(ecOffer.getEcqlId());
        EcqLevel ecqLevel = ecqLevelService.getObject(level);
        EcbMaterials ecbMaterials = ecbMaterialsModel.getObjectPassId(ecqLevel.getEcbcId());
        Integer materialId = ecbMaterials.getMaterialTypeId();
        EcbMaterialType materialType = ecbMaterialTypeModel.getObjectPassId(materialId);

        Conductor conductor = ecOffer.getConductor();
        cable.setConductorMaterial(
                ecbMaterials.getDensity(), ecbMaterials.getUnitPrice(),
                conductor.getFireRootNumber(), conductor.getZeroRootNumber(),
                conductor.getFireSilkNumber(), conductor.getZeroSilkNumber(),
                conductor.getFireStrand(), conductor.getZeroStrand(),
                BigDecimal.ONE
        );
        ConductorMaterial conductorMaterial = cable.getConductorMaterial();
        BigDecimal conductorWeight = conductorMaterial.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = conductorMaterial.getConductorMoney();// 导体金额
        String conductorFullName = materialType.getFullName();
        MaterialVo vo = new MaterialVo(conductorFullName, conductorWeight, conductorMoney);
        list.add(vo);

        //内部材料
        List<Internal> internals = ecOffer.getInternals();
        for (Internal internal : internals) {
            if (internal.getId() != null && internal.getId() != 0) {
                EcbMaterials internalMaterial = ecbMaterialsModel.getObjectPassId(internal.getId());
                Integer internalMaterialId = internalMaterial.getMaterialTypeId();
                EcbMaterialType internalMaterialType = ecbMaterialTypeModel.getObjectPassId(internalMaterialId);
                cable.addInternalMaterial(internalMaterial.getDensity(), internalMaterial.getUnitPrice(),
                        internal.getFactor(), internal.getFireThickness(), internal.getZeroThickness());
                List<InternalMaterial> internalMaterialValue = cable.getInternalMaterial();
                InternalMaterial internalMaterial1 = internalMaterialValue.get(internalMaterialValue.size() - 1);
                String internalFullName = internalMaterialType.getFullName();// 名称
                BigDecimal internalWeight = internalMaterial1.getMaterialWeight();// 重量
                BigDecimal internalMoney = internalMaterial1.getMaterialMoney();// 金额
                MaterialVo internalVo = new MaterialVo(internalFullName, internalWeight, internalMoney);
                list.add(internalVo);
            }
        }

        // 填充物数据
        Infilling infilling = ecOffer.getInfilling();
        if (infilling.getId() != null && infilling.getId() != 0) {
            EcbMaterials infillMaterial = ecbMaterialsModel.getObjectPassId(infilling.getId());
            Integer internalMaterialId = infillMaterial.getMaterialTypeId();
            EcbMaterialType infillMaterialType = ecbMaterialTypeModel.getObjectPassId(internalMaterialId);
            cable.setInfillingMaterial(infillMaterial.getDensity(), infillMaterial.getUnitPrice());
            InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
            String infillFullName = infillMaterialType.getFullName();// 名称
            BigDecimal infillingWeight = infillingMaterial.getInfillingWeight();// 填充物重量
            BigDecimal infillingMoney = infillingMaterial.getInfillingMoney();// 填充物金额
            MaterialVo internalVo = new MaterialVo(infillFullName, infillingWeight, infillingMoney);
            list.add(internalVo);
        }

        // 外部材料数据
        List<External> externals = ecOffer.getExternals();
        for (External external : externals) {
            if (external.getId() != null && external.getId() != 0) {
                EcbMaterials externalMaterial = ecbMaterialsModel.getObjectPassId(external.getId());
                Integer internalMaterialId = externalMaterial.getMaterialTypeId();
                EcbMaterialType externalMaterialType = ecbMaterialTypeModel.getObjectPassId(internalMaterialId);
                cable.addExternalMaterials(externalMaterial.getDensity(), externalMaterial.getUnitPrice(),
                        external.getFactor(), external.getThickness());
                List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
                ExternalMaterial externalMaterialValue = externalMaterials.get(externalMaterials.size() - 1);
                String externalFullName = externalMaterialType.getFullName();// 名称
                BigDecimal externalWeight = externalMaterialValue.getMaterialWeight();// 重量
                BigDecimal externalMoney = externalMaterialValue.getMaterialMoney();// 金额
                MaterialVo internalVo = new MaterialVo(externalFullName, externalWeight, externalMoney);
                list.add(internalVo);
            }
        }
        ProgrammeVo programmeVo = new ProgrammeVo();
        programmeVo.setMaterialVos(list);
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
        //生成表头
        List<String> title = Lists.newArrayList("截面积", "成本加点");
        List<String> title1 = ecqLevelModel.getTitle(new EcqLevelBaseBo(ecqlId));
        title.addAll(title1);
        for (int i = 0; i < title.size(); i++) {
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
        for (int i = 0; i < title.size(); i++) {
            titleRow.createCell(i).setCellValue(title.get(i));
        }
        titleRow.setHeight((short) 400);
        EcOffer recordEcuOffer = new EcOffer();
        recordEcuOffer.setEcqlId(ecqlId);
        List<EcOffer> list = ecOfferService.getList(recordEcuOffer);
        //int sortId = 1;
        int colNum = 1;
        for (EcOffer offer : list) {
            String addPercentStr = (offer.getAddPercent().multiply(new BigDecimal("100")))
                    .setScale(0, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();// 成本加点
            if (offer.getAddPercent().compareTo(BigDecimal.ZERO) == 0) {
                addPercentStr = "0%";
            } else {
                addPercentStr = addPercentStr + "%";
            }
            String areaStr = offer.getAreaStr();// 截面积

            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.setRowStyle(cellStyle);
            dataRow.createCell(0).setCellValue(areaStr);// 截面积
            dataRow.createCell(colNum++).setCellValue(addPercentStr);// 成本加点
            //导体
            Conductor conductor = offer.getConductor();
            dataRow.createCell(colNum++).setCellValue(conductor.getFullName());
            dataRow.createCell(colNum++).setCellValue(conductor.getFireSilkNumber().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getFireStrand().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getFireRootNumber());
            dataRow.createCell(colNum++).setCellValue(conductor.getZeroSilkNumber().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getZeroStrand().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getZeroRootNumber());
            //内部材料
            List<Internal> internals = offer.getInternals();
            for (Internal internal : internals) {
                dataRow.createCell(colNum++).setCellValue(internal.getFullName());
                dataRow.createCell(colNum++).setCellValue(internal.getFireThickness().stripTrailingZeros().toPlainString());
                dataRow.createCell(colNum++).setCellValue(internal.getZeroThickness().stripTrailingZeros().toPlainString());
                dataRow.createCell(colNum++).setCellValue(internal.getFactor().stripTrailingZeros().toPlainString());
            }
            //填充物
            Infilling infilling = offer.getInfilling();
            dataRow.createCell(colNum++).setCellValue(infilling.getFullName());
            //外部材料
            List<External> externals = offer.getExternals();
            for (External external : externals) {
                dataRow.createCell(colNum++).setCellValue(external.getFullName());
                dataRow.createCell(colNum++).setCellValue(external.getThickness().stripTrailingZeros().toPlainString());
                dataRow.createCell(colNum++).setCellValue(external.getFactor().stripTrailingZeros().toPlainString());
            }
            dataRow.setHeight((short) 400);
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

    public void exportTemplate(Integer ecqlId, HttpServletResponse response) {
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
            //生成表头
            List<String> title = Lists.newArrayList("截面积", "成本加点");
            List<String> title1 = ecqLevelModel.getTitle(new EcqLevelBaseBo(ecqlId));
            title1.remove(0);//去掉导体名称
            title.addAll(title1);
            for (int i = 0; i < title.size(); i++) {
                Cell cell = headerRow0.createCell(i);
                cell.setCellValue(title.get(i));
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
            log.error("导出模板报错--->", e);
            throw new RuntimeException("生成导入模板失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveOrUpdate(Integer ecoId, EcOfferBatchBo batchBo) {
        try {
            EcOffer ecOffer = ecOfferService.getById(ecoId);
            Integer materialType = batchBo.getMaterialType();
            if (materialType == 1) {
                Conductor conductor = ecOffer.getConductor();
                if (ObjUtil.isNotNull(batchBo.getFireSilkNumber())) {
                    conductor.setFireSilkNumber(batchBo.getFireSilkNumber());
                }
                if (ObjUtil.isNotNull(batchBo.getZeroSilkNumber())) {
                    conductor.setZeroSilkNumber(batchBo.getZeroSilkNumber());
                }
                if (ObjUtil.isNotNull(batchBo.getMaterialId())) {
                    conductor.setId(batchBo.getMaterialId());
                }
            } else if (materialType == 2) {
                Infilling infilling = ecOffer.getInfilling();
                if (ObjUtil.isNotNull(batchBo.getMaterialId())) {
                    infilling.setId(batchBo.getMaterialId());
                }
            } else {
                Integer materialTypeId = batchBo.getMaterialTypeId();
                List<Internal> internals = ecOffer.getInternals();
                for (Internal internal : internals) {
                    if (materialTypeId.equals(internal.getMaterialTypeId())) {
                        if (ObjUtil.isNotNull(batchBo.getFireThickness())) {
                            internal.setFireThickness(batchBo.getFireThickness());
                        }
                        if (ObjUtil.isNotNull(batchBo.getZeroThickness())) {
                            internal.setZeroThickness(batchBo.getZeroThickness());
                        }
                        if (ObjUtil.isNotNull(batchBo.getFactor())) {
                            internal.setFactor(batchBo.getFactor());
                        }
                        if (ObjUtil.isNotNull(batchBo.getMaterialId())) {
                            internal.setId(batchBo.getMaterialId());
                        }
                    }
                }
                List<External> externals = ecOffer.getExternals();
                for (External external : externals) {
                    if (materialTypeId.equals(external.getMaterialTypeId())) {
                        if (ObjUtil.isNotNull(batchBo.getThickness())) {
                            external.setThickness(batchBo.getThickness());
                        }
                        if (ObjUtil.isNotNull(batchBo.getFactor())) {
                            external.setFactor(batchBo.getFactor());
                        }
                        if (ObjUtil.isNotNull(batchBo.getMaterialId())) {
                            external.setId(batchBo.getMaterialId());
                        }
                    }
                }
            }
            ecOffer.convert();
            ecOfferService.updateById(ecOffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
