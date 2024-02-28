package org.jeecg.modules.cable.model.userOffer;

import cn.hutool.core.collection.CollUtil;
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
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsBaseBo;
import org.jeecg.modules.cable.controller.userEcable.offer.bo.*;
import org.jeecg.modules.cable.controller.userEcable.offer.vo.EcuOfferVo;
import org.jeecg.modules.cable.controller.userEcable.programme.bo.ProgrammeBo;
import org.jeecg.modules.cable.controller.userEcable.programme.vo.MaterialVo;
import org.jeecg.modules.cable.controller.userEcable.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelBaseBo;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.domain.computeBo.*;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.entity.userQuality.EcuArea;
import org.jeecg.modules.cable.model.systemEcable.EcSilkServiceModel;
import org.jeecg.modules.cable.model.userQuality.EcquLevelModel;
import org.jeecg.modules.cable.service.userEcable.EcbuMaterialTypeService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.jeecg.modules.cable.service.userEcable.Impl.EcbuMaterialsSerivce;
import org.jeecg.modules.cable.service.userOffer.EcuOfferService;
import org.jeecg.modules.cable.service.userQuality.EcquLevelService;
import org.jeecg.modules.cable.service.userQuality.EcuAreaService;
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
    EcuAreaService ecuAreaService;
    final ExcelUtils excelUtils = new ExcelUtils();
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
    @Resource
    private EcbuMaterialsSerivce ecbuMaterialsSerivce;
    @Resource
    private EcbuMaterialTypeService ecbuMaterialTypeService;
    @Resource
    private EcuSilkService ecuSilkService;

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
        try {
            InputStream in = file.getInputStream();
            //包含第一行标题头数据
            List<List<Object>> listob = excelUtils.getListByExcel(in, file.getOriginalFilename(), true);
            //获取标题头
            List<String> title = Lists.newArrayList("截面积", "成本加点");
            List<String> title1 = ecquLevelModel.getTitle(new EcquLevelBaseBo(ecqulId));
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
                EcuSilk ecuSilk = new EcuSilk();
                ecuSilk.setEcusId(ecquLevel.getEcusId());
                EcuSilk object = ecuSilkService.getObject(ecuSilk);
                //对应质量等级的型号系列
                // -----注意：-------
                // 因为材料这里是动态的，但是是个半动态的样子，所以是按照下面顺序来的（注意这个需要和上面的getTitle配合上）
                // 第三个字段是导体的材料名，之后第十个字段开始是内部材料，内部材料每隔三个是材料名，然后是填充物字段名，
                // 然后是外部材料，然后每隔两个字段是外部材料名
                List<EcbuMaterialType> materialTypesList = object.getMaterialTypesList();
                List<EcbuMaterialType> internal = new ArrayList<>();//内部材料
                List<EcbuMaterialType> external = new ArrayList<>();//外部材料
                boolean infill = false;
                EcbuMaterialType conduct = new EcbuMaterialType();
                EcbuMaterialType ecbinfilling = new EcbuMaterialType();
                for (EcbuMaterialType type : materialTypesList) {
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
                Map<String, Integer> mapStr = ecbuMaterialsSerivce.getMapStr(ecCompanyId);
                //整个成本库表用到的导体都是质量等级指定的导体
                Integer ecbucId = ecquLevel.getEcbucId();//导体id
                EcbuMaterials conductorName = ecbuMaterialsSerivce.getObject(new EcbuMaterialsBaseBo(ecbucId));//查询导体名称
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
                        conductor.setId(ecbucId);
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
                            EcbuMaterialType internalType = internal.get(it);
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
                            EcbuMaterialType externalType = external.get(it);
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
                        EcuOffer record = new EcuOffer();
                        record.setEcqulId(ecqulId);
                        EcuOffer ecuOffer = ecuOfferService.getObject(record);
                        if (ecuOffer != null) {
                            sortId = ecuOffer.getSortId() + 1;
                        }
                        //根据规格获取成本库表
                        record = new EcuOffer();
                        record.setEcqulId(ecqulId);
                        record.setAreaStr(areaStr);
                        ecuOffer = ecuOfferService.getObject(record);
                        record.setEcqulId(ecqulId);// 电缆质量等级ID
                        //record.setEcbc(ecbcId);// 导体ID
                        record.setStartType(startType);// 是否开启
                        record.setAddPercent(addPercent);
                        record.setAreaStr(areaStr);// 截面str
                        record.setStructure(structure);
                        record.setDefaultWeight(BigDecimal.ZERO);// 默认重量
                        record.setDefaultMoney(BigDecimal.ZERO);// 默认金额
                        record.convert(); //转换为json字符串
                        // log.info("record + " + CommonFunction.getGson().toJson(record));
                        if (ecuOffer != null) {
                            record.setEcuoId(ecuOffer.getEcuoId());
                            ecuOfferService.update(record);
                        } else {
                            //插入的写入排序
                            record.setSortId(sortId);
                            record.setEcCompanyId(ecCompanyId);
                            ecuOfferService.insert(record);
                        }
                        dealDefaultWeightAndDefaultMoney(ecqulId, areaStr);// 修改默认重量和金额
                        ecuoCoreModel.deal(ecqulId, areaStr);// 添加芯数表
                        ecuoAreaModel.load(ecqulId, areaStr);// 添加平方数表
                        successMsg.append("<br/>成本库表 " + "第" + i + "行" + "导入成功");
                        successNum++;
                    } catch (Exception e) {
                        log.error("导入失败-->", e);
                        failureMsg.append("<br/>成本库表 " + "第" + i + "行" + "导入出错");
                        failureNum++;
                    }
                }
                loadArea(ecCompanyId, ecqulId);// 加载质量等级对应的截面库ecuArea
            }
        } catch (
                Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("文件导入失败:" + e.getMessage());
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException e) {
                log.error("导入失败-->{}", ecqulId, e);
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
        Boolean startType = bo.getStartType();
        Integer ecqulId = bo.getEcqulId();
        //根据型号系列的材料类型排序，进行排序处理
        EcquLevel level = new EcquLevel();
        level.setEcqulId(ecqulId);
        EcquLevel object = ecquLevelService.getObject(level);
        Integer ecusId = object.getEcusId();
        //查询型号系列
        EcuSilk silk = new EcuSilk();
        silk.setEcusId(ecusId);
        EcuSilk ecuSilk = ecuSilkService.getObject(silk);
        List<EcbuMaterialType> materialTypesList = ecuSilk.getMaterialTypesList();
        if (CollUtil.isEmpty(materialTypesList)) {
            throw new RuntimeException("当前型号系列未设置材料的顺序");
        }
        EcuOffer record = new EcuOffer();
        record.setStartType(startType);
        record.setEcqulId(ecqulId);
        List<EcuOffer> list = ecuOfferService.getList(record);
        //todo 是否进行材料顺序的排序
        //for (EcuOffer offer : list) {
        //    //按理说导体肯定是不会动的。如果导体的id不对，则是有问题
        //    Conductor conductor = offer.getConductor();
        //
        //}
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
        String material = convertOffer(bo);
        if (ObjectUtil.isNull(ecuoId)) {// 插入
            record.setEcuoId(ecuoId);
            record.setEcqulId(ecqulId);
            record.setAreaStr(areaStr);
            EcuOffer ecuOffer = ecuOfferService.getObject(record);
            if (ecuOffer != null) {
                throw new RuntimeException("截面积已占用");
            }
            Boolean startType = false;
            int sortId = 1;
            ecuOffer = ecuOfferService.getObject(record);
            if (ecuOffer != null) {
                sortId = ecuOffer.getSortId() + 1;
            }
            record.setEcqulId(ecqulId);// 电缆质量等级ID
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(startType);// 是否开启
            record.setSortId(sortId);// 排序
            record.setAreaStr(areaStr);// 截面积
            record.setAddPercent(BigDecimal.ZERO);// 成本加点
            record.setMaterial(material);// 材料结构
            ecuOfferService.insert(record);
            msg = "插入数据成功";
        } else {
            record.setEcuoId(ecuoId);
            record.setAddPercent(bo.getAddPercent());
            record.setAreaStr(areaStr);// 截面str
            record.setMaterial(material);// 材料结构
            ecuOfferService.update(record);
            msg = "数据更新成功";
        }
        loadArea(sysUser.getEcCompanyId(), ecqulId);// 加载质量等级对应的截面库ecuArea
        dealDefaultWeightAndDefaultMoney(ecqulId, areaStr);
        return msg;
    }

    private String convertOffer(EcuOfferInsertBo bo) {
        //后台查询判断材料名称
        //导体
        Conductor conductor = bo.getConductor();
        EcbuMaterials objectPassId = ecbuMaterialsSerivce.getObjectPassId(conductor.getId());
        conductor.setFullName(objectPassId.getFullName());
        //填充物
        Infilling infilling = bo.getInfilling();
        EcbuMaterials objectPassId0 = ecbuMaterialsSerivce.getObjectPassId(infilling.getId());
        infilling.setFullName(objectPassId0.getFullName());
        //内部材料
        List<Internal> internals = bo.getInternals();
        for (Internal internal : internals) {
            EcbuMaterials objectPassId1 = ecbuMaterialsSerivce.getObjectPassId(internal.getId());
            internal.setFullName(objectPassId1.getFullName());
        }
        //外部材料
        List<External> externals = bo.getExternals();
        for (External external : externals) {
            EcbuMaterials objectPassId1 = ecbuMaterialsSerivce.getObjectPassId(external.getId());
            external.setFullName(objectPassId1.getFullName());
        }
        String material = bo.getMaterial();
        return material;
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

        //生成表头
        List<String> title = Lists.newArrayList("截面积", "成本加点");
        List<String> title1 = ecquLevelModel.getTitle(new EcquLevelBaseBo(ecqulId));
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
        EcuOffer recordEcuOffer = new EcuOffer();
        recordEcuOffer.setEcqulId(ecqulId);
        List<EcuOffer> list = ecuOfferService.getList(recordEcuOffer);

        int colNum = 1;
        for (EcuOffer ecuOffer : list) {
            //if (sortId < list.size()) {
            String addPercentStr = (ecuOffer.getAddPercent().multiply(new BigDecimal("100")))
                    .setScale(0, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();// 成本加点
            if (ecuOffer.getAddPercent().compareTo(BigDecimal.ZERO) == 0) {
                addPercentStr = "0%";
            } else {
                addPercentStr = addPercentStr + "%";
            }
            String areaStr = ecuOffer.getAreaStr();// 截面积

            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.setRowStyle(cellStyle);
            dataRow.createCell(0).setCellValue(areaStr);// 截面积
            dataRow.createCell(colNum++).setCellValue(addPercentStr);// 成本加点
            //导体
            Conductor conductor = ecuOffer.getConductor();
            dataRow.createCell(colNum++).setCellValue(conductor.getFullName());
            dataRow.createCell(colNum++).setCellValue(conductor.getFireSilkNumber().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getFireStrand().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getFireRootNumber());
            dataRow.createCell(colNum++).setCellValue(conductor.getZeroSilkNumber().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getZeroStrand().stripTrailingZeros().toPlainString());
            dataRow.createCell(colNum++).setCellValue(conductor.getZeroRootNumber());
            //内部材料
            List<Internal> internals = ecuOffer.getInternals();
            for (Internal internal : internals) {
                dataRow.createCell(colNum++).setCellValue(internal.getFullName());
                dataRow.createCell(colNum++).setCellValue(internal.getFireThickness().stripTrailingZeros().toPlainString());
                dataRow.createCell(colNum++).setCellValue(internal.getZeroThickness().stripTrailingZeros().toPlainString());
                dataRow.createCell(colNum++).setCellValue(internal.getFactor().stripTrailingZeros().toPlainString());
            }
            //填充物
            Infilling infilling = ecuOffer.getInfilling();
            dataRow.createCell(colNum++).setCellValue(infilling.getFullName());
            //外部材料
            List<External> externals = ecuOffer.getExternals();
            for (External external : externals) {
                dataRow.createCell(colNum++).setCellValue(external.getFullName());
                dataRow.createCell(colNum++).setCellValue(external.getThickness().stripTrailingZeros().toPlainString());
                dataRow.createCell(colNum++).setCellValue(external.getFactor().stripTrailingZeros().toPlainString());
            }
            dataRow.setHeight((short) 400);
            //}
            //sortId++;
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
        EcuOffer ecuOffer = ecuOfferService.getById(ecuoId);
        //材料信息
        List<MaterialVo> list = new ArrayList<>();
        //创建电缆对象
        Cable cable = new Cable(ecuOffer.getAreaStr());
        // 导体数据
        Conductor conductor = ecuOffer.getConductor();
        EcbuMaterials ecbuConductor = ecbuMaterialsSerivce.getObjectPassId(conductor.getId());
        Integer materialId = ecbuConductor.getMaterialTypeId();
        EcbuMaterialType materialType = ecbuMaterialTypeService.getObjectPassId(materialId);
        cable.setConductorMaterial(
                ecbuConductor.getDensity(), ecbuConductor.getUnitPrice(),
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
        List<Internal> internals = ecuOffer.getInternals();
        for (Internal internal : internals) {
            if (internal.getId() != null && internal.getId() != 0) {
                EcbuMaterials internalMaterial = ecbuMaterialsSerivce.getObjectPassId(internal.getId());
                Integer internalMaterialId = internalMaterial.getMaterialTypeId();
                EcbuMaterialType internalMaterialType = ecbuMaterialTypeService.getObjectPassId(internalMaterialId);
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
        Infilling infilling = ecuOffer.getInfilling();
        if (infilling.getId() != null && infilling.getId() != 0) {
            EcbuMaterials infillMaterial = ecbuMaterialsSerivce.getObjectPassId(infilling.getId());
            Integer internalMaterialId = infillMaterial.getMaterialTypeId();
            EcbuMaterialType infillMaterialType = ecbuMaterialTypeService.getObjectPassId(internalMaterialId);
            cable.setInfillingMaterial(infillMaterial.getDensity(), infillMaterial.getUnitPrice());
            InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
            String infillFullName = infillMaterialType.getFullName();// 名称
            BigDecimal infillingWeight = infillingMaterial.getInfillingWeight();// 填充物重量
            BigDecimal infillingMoney = infillingMaterial.getInfillingMoney();// 填充物金额
            MaterialVo internalVo = new MaterialVo(infillFullName, infillingWeight, infillingMoney);
            list.add(internalVo);
        }
        // 外部材料数据
        List<External> externals = ecuOffer.getExternals();
        for (External external : externals) {
            if (external.getId() != null && external.getId() != 0) {
                EcbuMaterials externalMaterial = ecbuMaterialsSerivce.getObjectPassId(external.getId());
                Integer internalMaterialId = externalMaterial.getMaterialTypeId();
                EcbuMaterialType externalMaterialType = ecbuMaterialTypeService.getObjectPassId(internalMaterialId);
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
        BigDecimal defaultWeight = BigDecimal.ZERO; // 重量
        BigDecimal defaultMoney = BigDecimal.ZERO; // 金额
        //创建电缆对象
        Cable cable = new Cable(ecuOffer.getAreaStr());
        // 导体数据
        Conductor conductor = ecuOffer.getConductor();
        EcbuMaterials ecbuConductor = ecbuMaterialsSerivce.getObjectPassId(conductor.getId());
        cable.setConductorMaterial(
                ecbuConductor.getDensity(), ecbuConductor.getUnitPrice(),
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
        List<Internal> internals = ecuOffer.getInternals();
        for (Internal internal : internals) {
            if (internal.getId() != null && internal.getId() != 0) {
                EcbuMaterials internalMaterial = ecbuMaterialsSerivce.getObjectPassId(internal.getId());
                //EcbuMicaTape ecbuMicaTape = ecbuMicatapeModel.getObjectPassEcbumId(ecuOffer.getEcbumId());
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
        Infilling infilling = ecuOffer.getInfilling();
        if (infilling.getId() != null && infilling.getId() != 0) {
            EcbuMaterials infillMaterial = ecbuMaterialsSerivce.getObjectPassId(infilling.getId());
            cable.setInfillingMaterial(infillMaterial.getDensity(), infillMaterial.getUnitPrice());
            InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
            BigDecimal infillingWeight = infillingMaterial.getInfillingWeight();// 填充物重量
            BigDecimal infillingMoney = infillingMaterial.getInfillingMoney();// 填充物金额
            defaultWeight = defaultWeight.add(infillingWeight);
            defaultMoney = defaultMoney.add(infillingMoney);
        }

        // 外部材料数据
        List<External> externals = ecuOffer.getExternals();
        for (External external : externals) {
            if (external.getId() != null && external.getId() != 0) {
                EcbuMaterials externalMaterial = ecbuMaterialsSerivce.getObjectPassId(external.getId());
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
        //// 导体数据
        //EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcbucId(ecuOffer.getEcbucId());
        //cable.setConductorMaterial(
        //        ecbuConductor.getDensity(), ecbuConductor.getUnitPrice(),
        //        ecuOffer.getFireRootNumber(), ecuOffer.getZeroRootNumber(),
        //        ecuOffer.getFireSilkNumber(), ecuOffer.getZeroSilkNumber(),
        //        ecuOffer.getFireStrand(), ecuOffer.getZeroStrand(),
        //        BigDecimal.ONE
        //);
        //ConductorMaterial conductorMaterial = cable.getConductorMaterial();
        //BigDecimal conductorWeight = conductorMaterial.getConductorWeight();// 导体重量
        //BigDecimal conductorMoney = conductorMaterial.getConductorMoney();// 导体金额
        //// 云母带数据
        //BigDecimal micaTapeWeight = BigDecimal.ZERO;// 云母带重量
        //BigDecimal micaTapeMoney = BigDecimal.ZERO;// 云母带金额
        //if (ecuOffer.getEcbumId() != 0) {
        //    EcbuMicaTape ecbuMicaTape = ecbuMicatapeModel.getObjectPassEcbumId(ecuOffer.getEcbumId());
        //    cable.addInternalMaterial(ecbuMicaTape.getDensity(),
        //            ecbuMicaTape.getUnitPrice(), BigDecimal.ONE,
        //            ecuOffer.getMicatapeThickness(), ecuOffer.getMicatapeThickness());
        //    List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
        //    InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);
        //    micaTapeWeight = internalMaterial1.getMaterialWeight();// 云母带重量
        //    micaTapeMoney = internalMaterial1.getMaterialMoney();// 云母带金额
        //}
        //// 绝缘数据
        //BigDecimal insulationWeight = BigDecimal.ZERO;
        //BigDecimal insulationMoney = BigDecimal.ZERO;
        //if (ecuOffer.getEcbuiId() != 0) {
        //    EcbuInsulation ecbuInsulation = ecbuInsulationModel.getObjectPassEcbuiId(ecuOffer.getEcbuiId());
        //    BigDecimal insulationFireThickness = ecuOffer.getInsulationFireThickness();// 粗芯绝缘厚度
        //    BigDecimal insulationZeroThickness = ecuOffer.getInsulationZeroThickness();// 细芯绝缘厚度
        //    cable.addInternalMaterial(ecbuInsulation.getDensity(),
        //            ecbuInsulation.getUnitPrice(), BigDecimal.ONE,
        //            insulationFireThickness, insulationZeroThickness);
        //    List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
        //    InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);
        //    insulationWeight = internalMaterial1.getMaterialWeight();// 绝缘重量
        //    insulationMoney = internalMaterial1.getMaterialMoney();// 绝缘金额
        //}
        //// 填充物数据
        //BigDecimal infillingWeight = BigDecimal.ZERO;// 填充物重量
        //BigDecimal infillingMoney = BigDecimal.ZERO;// 填充物金额
        //if (ecuOffer.getEcbuinId() != 0) {
        //    EcbuInfilling ecbuInfilling = ecbuInfillingModel.getObjectAndEcbuinId(ecuOffer.getEcbuinId());
        //    cable.setInfillingMaterial(ecbuInfilling.getDensity(), ecbuInfilling.getUnitPrice());
        //    InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
        //    infillingWeight = infillingMaterial.getInfillingWeight();// 填充物重量
        //    infillingMoney = infillingMaterial.getInfillingMoney();// 填充物金额
        //}
        //// 包带数据
        //BigDecimal bagWeight = BigDecimal.ZERO;// 包带重量
        //BigDecimal bagMoney = BigDecimal.ZERO;// 包带金额
        //if (ecuOffer.getEcbubId() != 0) {
        //    EcbuBag ecbuBag = ecbuBagModel.getObjectPassEcbubId(ecuOffer.getEcbubId());
        //    BigDecimal bagThickness = ecuOffer.getBagThickness();
        //    //此处的包带系数
        //    cable.addExternalMaterials(ecbuBag.getDensity(), ecbuBag.getUnitPrice(), BigDecimal.valueOf(1.1), bagThickness);
        //    List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
        //    ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
        //    bagWeight = externalMaterial.getMaterialWeight();// 包带重量
        //    bagMoney = externalMaterial.getMaterialMoney();// 包带金额
        //}
        //// 钢带数据
        //BigDecimal steelBandWeight = BigDecimal.ZERO;// 钢带重量
        //BigDecimal steelBandMoney = BigDecimal.ZERO;// 钢带金额
        //// 钢带数据
        //if (ecuOffer.getEcbusbId() != 0) {
        //    EcbuSteelBand ecbuSteelband = ecbuSteelbandModel.getObjectPassEcbusbId(ecuOffer.getEcbusbId());
        //    cable.addExternalMaterials(ecbuSteelband.getDensity(), ecbuSteelband.getUnitPrice(), BigDecimal.ONE, ecuOffer.getSteelbandThickness());
        //    List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
        //    ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
        //    steelBandWeight = externalMaterial.getMaterialWeight();// 钢带重量
        //    steelBandMoney = externalMaterial.getMaterialMoney();// 钢带金额
        //}
        //// 护套数据
        //BigDecimal sheathWeight = BigDecimal.ZERO;// 护套重量
        //BigDecimal sheathMoney = BigDecimal.ZERO;// 护套金额
        //BigDecimal sheathThickness = ecuOffer.getSheathThickness();
        //if (ecuOffer.getEcbuSheathId() != 0 && sheathThickness.compareTo(BigDecimal.ZERO) != 0) {
        //    EcbuSheath ecbuSheath = ecbuSheathModel.getObjectPassEcbusid(ecuOffer.getEcbuSheathId());
        //    BigDecimal density = ecbuSheath.getDensity();
        //    BigDecimal unitPrice = ecbuSheath.getUnitPrice();
        //    cable.addExternalMaterials(density, unitPrice, BigDecimal.ONE, sheathThickness);
        //    List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
        //    ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
        //    sheathWeight = externalMaterial.getMaterialWeight();// 护套重量
        //    sheathMoney = externalMaterial.getMaterialMoney();// 护套金额
        //}
        //BigDecimal defaultWeight = conductorWeight
        //        .add(micaTapeWeight)
        //        .add(bagWeight)
        //        .add(sheathWeight)
        //        .add(insulationWeight)
        //        .add(infillingWeight)
        //        .add(steelBandWeight)
        //        .add(sheathWeight);
        //BigDecimal defaultMoney = conductorMoney
        //        .add(micaTapeMoney)
        //        .add(bagMoney)
        //        .add(sheathMoney)
        //        .add(insulationMoney)
        //        .add(infillingMoney)
        //        .add(steelBandMoney)
        //        .add(sheathMoney);
        record.setEcuoId(ecuOffer.getEcuoId());
        record.setDefaultWeight(defaultWeight);
        record.setDefaultMoney(defaultMoney);
        ecuOfferService.update(record);
    }

    public void exportTemplate(EcquLevelBaseBo bo, HttpServletResponse response) {
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
            List<String> title1 = ecquLevelModel.getTitle(bo);
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
    public void batchSaveOrUpdate(Integer ecuoId, EcuOfferBatchBo batchBo) {
        try {
            EcuOffer ecuOffer = ecuOfferService.getById(ecuoId);
            Integer materialType = batchBo.getMaterialType();
            if (materialType == 1) {
                Conductor conductor = ecuOffer.getConductor();
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
                Infilling infilling = ecuOffer.getInfilling();
                if (ObjUtil.isNotNull(batchBo.getMaterialId())) {
                    infilling.setId(batchBo.getMaterialId());
                }
            } else {
                Integer materialTypeId = batchBo.getMaterialTypeId();
                List<Internal> internals = ecuOffer.getInternals();
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
                List<External> externals = ecuOffer.getExternals();
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
            ecuOffer.convert();
            ecuOfferService.updateById(ecuOffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
