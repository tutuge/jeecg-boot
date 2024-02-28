package org.jeecg.modules.cable.model.systemQuality;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelBaseBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelDealBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelListBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelSortBo;
import org.jeecg.modules.cable.controller.systemQuality.level.vo.SystemLevelVo;
import org.jeecg.modules.cable.domain.materialType.ConductorBatch;
import org.jeecg.modules.cable.domain.materialType.ExternalBatch;
import org.jeecg.modules.cable.domain.materialType.InternalBatch;
import org.jeecg.modules.cable.domain.materialType.MaterialTypeBatch;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcqLevelModel {
    @Resource
    EcqLevelService ecqLevelService;
    @Resource
    EcSilkService ecSilkService;


    public SystemLevelVo getList(EcqLevelListBo bo) {
        EcqLevel record = new EcqLevel();
        record.setStartType(bo.getStartType());
        List<EcqLevel> list = ecqLevelService.getList(record);
        long count = ecqLevelService.getCount(record);
        return new SystemLevelVo(list, count);
    }


    public EcqLevel getObject(EcqLevelBaseBo bo) {
        EcqLevel record = new EcqLevel();
        Integer ecqlId = bo.getEcqlId();
        record.setEcqlId(ecqlId);
        EcqLevel object = ecqLevelService.getObject(record);
        EcSilk silk = new EcSilk();
        silk.setEcsId(object.getEcsId());
        EcSilk ecSilk = ecSilkService.getObject(silk);
        object.setEcSilk(ecSilk);
        return object;
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcqLevelDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecqlId = bo.getEcqlId();
        Integer ecsId = bo.getEcsId();
        Integer ecbcId = bo.getEcbcId();
        String name = bo.getName();
        String description = bo.getDescription();

        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqlId);
        record.setName(name);
        String msg;
        if (ObjectUtil.isNull(ecqlId)) {// 插入
            int sortId = 1;
            EcqLevel ecquLevel = ecqLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record = new EcqLevel();
            record.setEcsId(ecsId);
            record.setEcbcId(ecbcId);
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPowerId(2);// 自有库
            record.setName(name);
            record.setDefaultType(false);
            record.setDescription(description);
            ecqLevelService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcqlId(ecqlId);
            record.setEcsId(ecsId);
            record.setEcbcId(ecbcId);
            record.setName(name);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecqLevelService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    public void sort(List<EcqLevelSortBo> bos) {
        for (EcqLevelSortBo bo : bos) {
            Integer ecqulId = bo.getEcqlId();
            Integer sortId = bo.getSortId();
            EcqLevel record = new EcqLevel();
            record.setEcqlId(ecqulId);
            record.setSortId(sortId);
            ecqLevelService.update(record);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(EcqLevelBaseBo bo) {
        Integer ecqlId = bo.getEcqlId();
        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqlId);
        EcqLevel ecquLevel = ecqLevelService.getObject(record);
        Integer sortId = ecquLevel.getSortId();
        record = new EcqLevel();
        record.setSortId(sortId);
        List<EcqLevel> list = ecqLevelService.getList(record);
        Integer ecql_id;
        for (EcqLevel ecqu_level : list) {
            ecql_id = ecqu_level.getEcqlId();
            sortId = ecqu_level.getSortId() - 1;
            record.setEcqlId(ecql_id);
            record.setSortId(sortId);
            ecqLevelService.update(record);
        }
        ecqLevelService.delete(ecqlId);
    }


    public String start(EcqLevelBaseBo bo) {
        Integer ecqlId = bo.getEcqlId();
        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqlId);
        EcqLevel ecquLevel = ecqLevelService.getObject(record);
        Boolean startType = ecquLevel.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcqLevel();
        record.setEcqlId(ecquLevel.getEcqlId());
        record.setStartType(startType);
        ecqLevelService.update(record);
        return msg;
    }


    public void defaultType(EcqLevelBaseBo bo) {
        Integer ecqlId = bo.getEcqlId();
        EcqLevel ecqLevel = getObjectPassEcqulId(ecqlId);
        //先将同型号类型的全部设为非默认
        EcqLevel record = new EcqLevel();
        record.setEcsId(ecqLevel.getEcsId());
        record.setDefaultType(false);
        ecqLevelService.update(record);
        //再将对应的主键的设置为默认
        record = new EcqLevel();
        record.setEcqlId(ecqlId);
        record.setDefaultType(true);
        ecqLevelService.update(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcqLevel record) {
        EcqLevel recordEcquLevel = new EcqLevel();
        recordEcquLevel.setName(record.getName());
        EcqLevel object = ecqLevelService.getObject(recordEcquLevel);
        if (object == null) {
            int sortId = 1;
            EcqLevel ecquLevel = ecqLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record.setSortId(sortId);
            ecqLevelService.insert(record);
        } else {
            ecqLevelService.update(record);
        }
    }

    // getObjectPassEcqulId
    public EcqLevel getObjectPassEcqulId(Integer ecqulId) {
        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqulId);
        return ecqLevelService.getObject(record);
    }

    public List<String> getTitle(EcqLevelBaseBo bo) {
        List<EcbMaterialType> materialTypesList = getMaterialTypeList(bo);
        List<String> titles = new ArrayList<>();
        boolean infill = false;
        for (EcbMaterialType ecbMaterialType : materialTypesList) {
            //导体
            if (ecbMaterialType.getMaterialType() == 1) {
                titles.add(ecbMaterialType.getFullName());
                titles.add("粗芯丝号");
                titles.add("粗芯绞合系数");
                titles.add("粗芯根数");
                titles.add("细芯丝号");
                titles.add("细芯绞合系数");
                titles.add("细芯根数");
            } else if (ecbMaterialType.getMaterialType() == 2) {
                infill = true;
                titles.add(ecbMaterialType.getFullName());
            } else if (ecbMaterialType.getMaterialType() == 0) {
                //还没有到填充物的时候的普通材料
                if (!infill) {
                    String fullName = ecbMaterialType.getFullName();
                    titles.add(fullName);
                    titles.add("粗芯" + fullName + "厚度");
                    titles.add("细芯" + fullName + "厚度");
                    titles.add(fullName + "系数");
                } else {
                    String fullName = ecbMaterialType.getFullName();
                    titles.add(fullName);
                    titles.add(fullName + "厚度");
                    titles.add(fullName + "系数");
                }
            }
        }
        return titles;
    }

    private List<EcbMaterialType> getMaterialTypeList(EcqLevelBaseBo bo) {
        EcqLevel record = new EcqLevel();
        Integer ecqulId = bo.getEcqlId();
        record.setEcqlId(ecqulId);
        EcqLevel object = ecqLevelService.getObject(record);
        Integer ecusId = object.getEcsId();
        //查询型号系列
        EcSilk silk = new EcSilk();
        silk.setEcsId(ecusId);
        EcSilk ecSilk = ecSilkService.getObject(silk);
        List<EcbMaterialType> materialTypesList = ecSilk.getMaterialTypesList();
        return materialTypesList;
    }

    public List<MaterialTypeBatch> getBatch(EcqLevelBaseBo bo) {
        List<EcbMaterialType> materialTypesList = getMaterialTypeList(bo);
        List<MaterialTypeBatch> batches = new ArrayList<>();
        boolean infill = false;
        for (EcbMaterialType ecbMaterialType : materialTypesList) {
            //导体
            if (ecbMaterialType.getMaterialType() == 1) {
                ConductorBatch conductorBatch = new ConductorBatch();
                conductorBatch.setMaterialTypeId(ecbMaterialType.getId());
                conductorBatch.setMaterialType(ecbMaterialType.getMaterialType());
                conductorBatch.setFullName(ecbMaterialType.getFullName());

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
                jsonObject2.put("materialTypeId", ecbMaterialType.getId());
                list.add(jsonObject2);
                conductorBatch.setList(list);
                batches.add(conductorBatch);
            } else if (ecbMaterialType.getMaterialType() == 2) {
                infill = true;
                //InfillBatch infillBatch = new InfillBatch();
                //infillBatch.setMaterialTypeId(ecbMaterialType.getId());
                //infillBatch.setMaterialType(ecbMaterialType.getMaterialType());
                //infillBatch.setFullName(ecbMaterialType.getFullName());
                //batches.add(infillBatch);
            } else if (ecbMaterialType.getMaterialType() == 0) {
                //还没有到填充物的时候的普通材料
                if (!infill) {
                    InternalBatch internalBatch = new InternalBatch();
                    internalBatch.setMaterialTypeId(ecbMaterialType.getId());
                    internalBatch.setMaterialType(ecbMaterialType.getMaterialType());
                    internalBatch.setFullName(ecbMaterialType.getFullName());

                    List<JSONObject> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("title", "粗芯材料厚度");
                    jsonObject.put("key", "fireThickness");
                    list.add(jsonObject);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("title", "细芯材料厚度");
                    jsonObject1.put("key", "zeroThickness");
                    list.add(jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("title", "系数");
                    jsonObject2.put("key", "factor");
                    list.add(jsonObject2);
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("title", ecbMaterialType.getFullName());
                    jsonObject3.put("key", "materialId");
                    jsonObject3.put("materialType", 0);
                    jsonObject3.put("materialTypeId", ecbMaterialType.getId());
                    list.add(jsonObject3);
                    internalBatch.setList(list);
                    batches.add(internalBatch);
                } else {
                    ExternalBatch externalBatch = new ExternalBatch();
                    externalBatch.setMaterialTypeId(ecbMaterialType.getId());
                    externalBatch.setMaterialType(ecbMaterialType.getMaterialType());
                    externalBatch.setFullName(ecbMaterialType.getFullName());

                    List<JSONObject> list = new ArrayList<>();
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("title", "材料厚度");
                    jsonObject1.put("key", "thickness");
                    list.add(jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("title", "系数");
                    jsonObject2.put("key", "factor");
                    list.add(jsonObject2);

                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("title", ecbMaterialType.getFullName());
                    jsonObject3.put("key", "materialId");
                    jsonObject3.put("materialType", 0);
                    jsonObject3.put("materialTypeId", ecbMaterialType.getId());
                    list.add(jsonObject3);
                    externalBatch.setList(list);
                    batches.add(externalBatch);
                }
            }
        }
        return batches;
    }
}
