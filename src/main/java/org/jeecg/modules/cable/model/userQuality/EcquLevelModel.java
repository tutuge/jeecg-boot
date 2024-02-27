package org.jeecg.modules.cable.model.userQuality;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelBaseBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelDealBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelListBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelSortBo;
import org.jeecg.modules.cable.controller.userQuality.level.vo.LevelVo;
import org.jeecg.modules.cable.domain.materialType.ConductorBatch;
import org.jeecg.modules.cable.domain.materialType.ExternalBatch;
import org.jeecg.modules.cable.domain.materialType.InternalBatch;
import org.jeecg.modules.cable.domain.materialType.MaterialTypeBatch;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.jeecg.modules.cable.service.userQuality.EcquLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcquLevelModel {
    @Resource
    EcquLevelService ecquLevelService;
    @Resource
    private EcuSilkService ecuSilkService;
    @Resource
    private EcuqInputService ecuqInputService;


    public List<String> getTitle(EcquLevelBaseBo bo) {
        List<EcbuMaterialType> materialTypesList = getMaterialTypeList(bo);
        List<String> titles = new ArrayList<>();
        boolean infill = false;
        for (EcbuMaterialType ecbuMaterialType : materialTypesList) {
            //导体
            if (ecbuMaterialType.getMaterialType() == 1) {
                titles.add(ecbuMaterialType.getFullName());
                titles.add("粗芯丝号");
                titles.add("粗芯绞合系数");
                titles.add("粗芯根数");
                titles.add("细芯丝号");
                titles.add("细芯绞合系数");
                titles.add("细芯根数");
            } else if (ecbuMaterialType.getMaterialType() == 2) {
                infill = true;
                titles.add(ecbuMaterialType.getFullName());
            } else if (ecbuMaterialType.getMaterialType() == 0) {
                //还没有到填充物的时候的普通材料
                if (!infill) {
                    String fullName = ecbuMaterialType.getFullName();
                    titles.add(fullName);
                    titles.add("粗芯" + fullName + "厚度");
                    titles.add("细芯" + fullName + "厚度");
                    titles.add(fullName + "系数");
                } else {
                    String fullName = ecbuMaterialType.getFullName();
                    titles.add(fullName);
                    titles.add(fullName + "厚度");
                    titles.add(fullName + "系数");
                }
            }
        }
        return titles;
    }

    private List<EcbuMaterialType> getMaterialTypeList(EcquLevelBaseBo bo) {
        EcquLevel record = new EcquLevel();
        Integer ecqulId = bo.getEcqulId();
        record.setEcqulId(ecqulId);
        EcquLevel object = ecquLevelService.getObject(record);
        Integer ecusId = object.getEcusId();
        //查询型号系列
        EcuSilk silk = new EcuSilk();
        silk.setEcusId(ecusId);
        EcuSilk ecuSilk = ecuSilkService.getObject(silk);
        List<EcbuMaterialType> materialTypesList = ecuSilk.getMaterialTypesList();
        return materialTypesList;
    }


    public List<MaterialTypeBatch> getBatch(EcquLevelBaseBo bo) {
        List<EcbuMaterialType> materialTypesList = getMaterialTypeList(bo);
        List<MaterialTypeBatch> batches = new ArrayList<>();
        boolean infill = false;
        for (EcbuMaterialType materialType : materialTypesList) {
            //导体
            if (materialType.getMaterialType() == 1) {
                ConductorBatch conductorBatch = new ConductorBatch();
                conductorBatch.setMaterialTypeId(materialType.getId());
                conductorBatch.setMaterialType(materialType.getMaterialType());
                conductorBatch.setFullName(materialType.getFullName());
                List<JSONObject> list = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", "粗芯丝号");
                jsonObject.put("key", "fireSilkNumber");
                list.add(jsonObject);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("title", "细芯丝号");
                jsonObject1.put("key", "zeroSilkNumber");
                list.add(jsonObject1);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("title", "导体");
                jsonObject2.put("key", "materialId");
                jsonObject2.put("materialType", 1);
                jsonObject2.put("materialTypeId", materialType.getId());
                list.add(jsonObject2);
                conductorBatch.setList(list);
                batches.add(conductorBatch);
            } else if (materialType.getMaterialType() == 2) {
                infill = true;
            } else if (materialType.getMaterialType() == 0) {
                //还没有到填充物的时候的普通材料
                if (!infill) {
                    InternalBatch internalBatch = new InternalBatch();
                    internalBatch.setMaterialTypeId(materialType.getId());
                    internalBatch.setMaterialType(materialType.getMaterialType());
                    internalBatch.setFullName(materialType.getFullName());
                    List<JSONObject> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("title", "粗芯" + materialType.getFullName() + "厚度");
                    jsonObject.put("key", "fireThickness");
                    list.add(jsonObject);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("title", "细芯" + materialType.getFullName() + "厚度");
                    jsonObject1.put("key", "zeroThickness");
                    list.add(jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("title", "系数");
                    jsonObject2.put("key", "factor");
                    list.add(jsonObject2);
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("title", materialType.getFullName());
                    jsonObject3.put("key", "materialId");
                    jsonObject3.put("materialType", 0);
                    jsonObject3.put("materialTypeId", materialType.getId());
                    list.add(jsonObject3);
                    internalBatch.setList(list);
                    batches.add(internalBatch);
                } else {
                    ExternalBatch externalBatch = new ExternalBatch();
                    externalBatch.setMaterialTypeId(materialType.getId());
                    externalBatch.setMaterialType(materialType.getMaterialType());
                    externalBatch.setFullName(materialType.getFullName());
                    List<JSONObject> list = new ArrayList<>();
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("title", materialType.getFullName()+"厚度");
                    jsonObject1.put("key", "thickness");
                    list.add(jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("title", "系数");
                    jsonObject2.put("key", "factor");
                    list.add(jsonObject2);

                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("title", materialType.getFullName());
                    jsonObject3.put("key", "materialId");
                    jsonObject3.put("materialType", 0);
                    jsonObject3.put("materialTypeId", materialType.getId());
                    list.add(jsonObject3);
                    externalBatch.setList(list);
                    batches.add(externalBatch);
                }
            }
        }
        return batches;
    }

    public LevelVo getList(EcquLevelListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcquLevel record = new EcquLevel();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcquLevel> list = ecquLevelService.getList(record);
        long count = ecquLevelService.getCount(record);
        return new LevelVo(list, count);
    }


    public EcquLevel getObject(EcquLevelBaseBo bo) {
        EcquLevel record = new EcquLevel();
        Integer ecqulId = bo.getEcqulId();
        record.setEcqulId(ecqulId);
        EcquLevel object = ecquLevelService.getObject(record);
        EcuSilk silk = new EcuSilk();
        silk.setEcusId(object.getEcusId());
        EcuSilk ecuSilk = ecuSilkService.getObject(silk);
        object.setEcuSilk(ecuSilk);
        return object;
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcquLevelDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecqulId = bo.getEcqulId();
        Integer ecusId = bo.getEcusId();
        Integer ecbucId = bo.getEcbucId();
        String name = bo.getName();
        String description = bo.getDescription();

        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setName(name);
        String msg;
        if (ObjectUtil.isNull(ecqulId)) {// 插入
            int sortId = 1;
            EcquLevel ecquLevel = ecquLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record = new EcquLevel();
            record.setEcusId(ecusId);
            record.setEcbucId(ecbucId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPowerId(2);// 自有库
            record.setName(name);
            record.setDefaultType(false);
            record.setDescription(description);
            ecquLevelService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcqulId(ecqulId);
            record.setEcusId(ecusId);
            record.setEcbucId(ecbucId);
            record.setName(name);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecquLevelService.update(record);
            msg = "正常更新数据";
        }
        //deal(sysUser.getEcCompanyId());
        return msg;
    }


    public void sort(List<EcquLevelSortBo> bos) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        for (EcquLevelSortBo bo : bos) {
            Integer ecqulId = bo.getEcqulId();
            Integer sortId = bo.getSortId();
            EcquLevel record = new EcquLevel();
            record.setEcqulId(ecqulId);
            record.setSortId(sortId);
            ecquLevelService.update(record);
            //deal(sysUser.getEcCompanyId());// 加载load为集成数据
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(EcquLevelBaseBo bo) {
        Integer ecqulId = bo.getEcqulId();
        //判断下报价单中是否使用此质量等级
        EcuqInput input = new EcuqInput();
        List<EcuqInput> list1 = ecuqInputService.getList(input);
        if (CollUtil.isNotEmpty(list1)) {
            throw new RuntimeException("当前数据绑定了报价单，无法删除");
        }

        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        Integer sortId = ecquLevel.getSortId();
        record = new EcquLevel();
        record.setSortId(sortId);
        record.setEcCompanyId(ecquLevel.getEcCompanyId());
        List<EcquLevel> list = ecquLevelService.getList(record);
        Integer ecqul_id;
        for (EcquLevel ecqu_level : list) {
            ecqul_id = ecqu_level.getEcqulId();
            sortId = ecqu_level.getSortId() - 1;
            record.setEcqulId(ecqul_id);
            record.setSortId(sortId);
            ecquLevelService.update(record);
        }
        ecquLevelService.delete(ecqulId);
        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //deal(sysUser.getEcCompanyId());// 加载load为集成数据
    }


    public String start(EcquLevelBaseBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        Boolean startType = ecquLevel.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcquLevel();
        record.setEcqulId(ecquLevel.getEcqulId());
        record.setStartType(startType);
        ecquLevelService.update(record);
        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //deal(sysUser.getEcCompanyId());// 加载load为集成数据
        return msg;
    }


    public void defaultType(EcquLevelBaseBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcquLevel ecquLevel = getObjectPassEcqulId(ecqulId);
        //先将同型号类型的全部设为非默认
        EcquLevel record = new EcquLevel();
        record.setEcusId(ecquLevel.getEcusId());
        record.setDefaultType(false);
        ecquLevelService.update(record);
        //再将对应的主键的设置为默认
        record = new EcquLevel();
        record.setEcqulId(ecqulId);
        record.setDefaultType(true);
        ecquLevelService.update(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcquLevel record) {
        EcquLevel recordEcquLevel = new EcquLevel();
        recordEcquLevel.setName(record.getName());
        recordEcquLevel.setEcCompanyId(record.getEcCompanyId());
        EcquLevel object = ecquLevelService.getObject(recordEcquLevel);
        if (object == null) {
            int sortId = 1;
            EcquLevel ecquLevel = ecquLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record.setSortId(sortId);
            // log.info("record + " + CommonFunction.getGson().toJson(record));
            ecquLevelService.insert(record);
        } else {
            record.setEcqulId(object.getEcqulId());
            ecquLevelService.update(record);
        }
    }


    //@Transactional(rollbackFor = Exception.class)
    //public void deal(Integer ecCompanyId) {
    //    EcquLevel record = new EcquLevel();
    //    record.setStartType(true);
    //    record.setEcCompanyId(ecCompanyId);
    //    List<EcquLevel> list = ecquLevelService.getList(record);
    //    List<String> txtList = new ArrayList<>();
    //    txtList.add(CommonFunction.getGson().toJson(list));
    //    ecdCollectModel.deal(ecCompanyId, 2, txtList);
    //}

    // getObjectPassEcqulId
    public EcquLevel getObjectPassEcqulId(Integer ecqulId) {
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        return ecquLevelService.getObject(record);
    }

    //  根据丝系列获取默认的质量等级，如果没有默认等级，随机获取一个
    public EcquLevel getObjectPassEcsIdAndDefaultType(Integer ecCompanyId, Integer ecsId) {
        EcquLevel record = new EcquLevel();
        record.setEcCompanyId(ecCompanyId);
        record.setEcusId(ecsId);
        record.setDefaultType(true);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        if (ecquLevel == null) {
            record = new EcquLevel();
            record.setEcCompanyId(ecCompanyId);
            record.setEcusId(ecsId);
            ecquLevel = ecquLevelService.getObject(record);
        }
        return ecquLevel;
    }

    //  根据公司ID和质量等级名称获取数据
    public EcquLevel getObjectPassEcCompanyIdAndName(Integer ecCompanyId, String name) {
        EcquLevel record = new EcquLevel();
        record.setEcCompanyId(ecCompanyId);
        record.setName(name);
        return ecquLevelService.getObject(record);
    }
}
