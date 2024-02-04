package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagSortBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialDealBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialListBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialSortBo;
import org.jeecg.modules.cable.controller.systemEcable.material.vo.MaterialVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterial;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialMapper;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EcbMaterialModel {
    @Resource
    EcbMaterialMapper materialMapper;


    public MaterialVo getList(EcbMaterialListBo bo) {
        EcbMaterial record = new EcbMaterial();
        record.setStartType(bo.getStartType());
        List<EcbMaterial> list = materialMapper.getList(record);
        long count = materialMapper.getSysCount(record);
        return new MaterialVo(list, count);
    }


    public EcbMaterial getObject(EcbMaterialBaseBo bo) {
        return getObjectPassEcbbId(bo.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbMaterialDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer id = bo.getId();
        String fullName = bo.getFullName();
        String description = bo.getDescription();
        Integer materialType = bo.getMaterialType();

        EcbMaterial record = new EcbMaterial();
        record.setId(id);
        record.setFullName(fullName);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbMaterial ecbMaterial = materialMapper.getSysObject(record);
        String msg;
        if (ecbMaterial != null) {
            throw new RuntimeException("全称已占用");
        }
        if (ObjectUtil.isNull(id)) {// 插入
            int sortId = 1;
            ecbMaterial = materialMapper.getSysObject(null);
            if (ecbMaterial != null) {
                sortId = ecbMaterial.getSortId() + 1;
            }
            record = new EcbMaterial();
            record.setEcaId(sysUser.getUserId());
            record.setEcaName(sysUser.getUsername());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setFullName(fullName);
            record.setDescription(description);
            record.setMaterialType(materialType);
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            materialMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setId(id);
            record.setFullName(fullName);
            record.setDescription(description);
            record.setMaterialType(materialType);
            record.setUpdateTime(new Date());
            materialMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbMaterialSortBo> bos) {
        for (EcbMaterialSortBo bo : bos) {
            Integer id = bo.getId();
            Integer sortId = bo.getSortId();
            EcbMaterial record = new EcbMaterial();
            record.setId(id);
            record.setSortId(sortId);
            materialMapper.updateById(record);
        }
    }


    public String start(EcbMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbMaterial record = new EcbMaterial();
        record.setId(id);
        EcbMaterial ecbMaterial = materialMapper.getSysObject(record);
        Boolean startType = ecbMaterial.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbMaterial();
        record.setId(ecbMaterial.getId());
        record.setStartType(startType);
        materialMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbMaterial record = new EcbMaterial();
        record.setId(id);
        EcbMaterial ecbBag = materialMapper.getSysObject(record);
        Integer sortId = ecbBag.getSortId();
        materialMapper.reduceSort(sortId);
        materialMapper.deleteById(id);
    }


    // getObjectPassEcbbId
    public EcbMaterial getObjectPassEcbbId(Integer id) {
        EcbMaterial record = new EcbMaterial();
        record.setId(id);
        return materialMapper.getSysObject(record);
    }
}
