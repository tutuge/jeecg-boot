package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialDealBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialListBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialSortBo;
import org.jeecg.modules.cable.controller.systemEcable.material.vo.MaterialTypeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialTypeMapper;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EcbMaterialTypeModel {
    @Resource
    EcbMaterialTypeMapper materialMapper;


    public MaterialTypeVo getList(EcbMaterialListBo bo) {
        EcbMaterialType record = new EcbMaterialType();
        record.setStartType(bo.getStartType());
        List<EcbMaterialType> list = materialMapper.getList(record);
        long count = materialMapper.getSysCount(record);
        return new MaterialTypeVo(list, count);
    }


    public EcbMaterialType getObject(EcbMaterialBaseBo bo) {
        return getObjectPassEcbbId(bo.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbMaterialDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer id = bo.getId();
        String fullName = bo.getFullName();
        String description = bo.getDescription();
        Integer materialType = bo.getMaterialType();

        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        record.setFullName(fullName);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbMaterialType ecbMaterialType = materialMapper.getSysObject(record);
        String msg;
        if (ecbMaterialType != null) {
            throw new RuntimeException("全称已占用");
        }
        if (ObjectUtil.isNull(id)) {// 插入
            int sortId = 1;
            ecbMaterialType = materialMapper.getSysObject(null);
            if (ecbMaterialType != null) {
                sortId = ecbMaterialType.getSortId() + 1;
            }
            record = new EcbMaterialType();
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
            EcbMaterialType record = new EcbMaterialType();
            record.setId(id);
            record.setSortId(sortId);
            materialMapper.updateById(record);
        }
    }


    public String start(EcbMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        EcbMaterialType ecbMaterialType = materialMapper.getSysObject(record);
        Boolean startType = ecbMaterialType.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbMaterialType();
        record.setId(ecbMaterialType.getId());
        record.setStartType(startType);
        materialMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        EcbMaterialType ecbBag = materialMapper.getSysObject(record);
        Integer sortId = ecbBag.getSortId();
        materialMapper.reduceSort(sortId);
        materialMapper.deleteById(id);
    }


    // getObjectPassEcbbId
    public EcbMaterialType getObjectPassEcbbId(Integer id) {
        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        return materialMapper.getSysObject(record);
    }
}
