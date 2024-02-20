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
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialTypeMapper;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EcbMaterialTypeModel {
    @Resource
    private EcbMaterialTypeMapper ecbMaterialTypeMapper;
    @Resource
    private EcbMaterialsMapper ecbMaterialsMapper;


    public MaterialTypeVo getList(EcbMaterialListBo bo) {
        EcbMaterialType record = new EcbMaterialType();
        record.setStartType(bo.getStartType());
        List<EcbMaterialType> list = ecbMaterialTypeMapper.getList(record);
        long count = ecbMaterialTypeMapper.getSysCount(record);
        return new MaterialTypeVo(list, count);
    }


    public EcbMaterialType getObject(EcbMaterialBaseBo bo) {
        return getObjectPassId(bo.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(EcbMaterialDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer id = bo.getId();
        String fullName = bo.getFullName();
        String description = bo.getDescription();
        Integer materialType = bo.getMaterialType();

        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        record.setFullName(fullName);
        EcbMaterialType ecbMaterialType = ecbMaterialTypeMapper.getSysObject(record);
        String msg;
        if (ecbMaterialType != null) {
            throw new RuntimeException("全称已占用");
        }

        if (materialType != 0) {
            EcbMaterialType record0 = new EcbMaterialType();
            record0.setMaterialType(materialType);
            ecbMaterialType = ecbMaterialTypeMapper.getSysObject(record0);
            if (ecbMaterialType != null) {
                if (materialType == 1) {
                    throw new RuntimeException("当前已经创建导体材料");
                }
                if (materialType == 2) {
                    throw new RuntimeException("当前已经创建填充物材料");
                }
            }
        }
        if (ObjectUtil.isNull(id)) {// 插入
            int sortId = 1;
            ecbMaterialType = ecbMaterialTypeMapper.getSysObject(null);
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
            ecbMaterialTypeMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setId(id);
            record.setFullName(fullName);
            record.setDescription(description);
            record.setMaterialType(materialType);
            record.setUpdateTime(new Date());
            ecbMaterialTypeMapper.updateById(record);
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
            ecbMaterialTypeMapper.updateById(record);
        }
    }


    public String start(EcbMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        EcbMaterialType ecbMaterialType = ecbMaterialTypeMapper.getSysObject(record);
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
        ecbMaterialTypeMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbMaterials ecbMaterials = new EcbMaterials();
        ecbMaterials.setMaterialId(id);
        long sysCount = ecbMaterialsMapper.getSysCount(ecbMaterials);
        if (sysCount > 0) {
            throw new RuntimeException("当前材料类型还在被使用，无法删除");
        }
        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        EcbMaterialType sysObject = ecbMaterialTypeMapper.getSysObject(record);
        Integer sortId = sysObject.getSortId();
        ecbMaterialTypeMapper.reduceSort(sortId);
        ecbMaterialTypeMapper.deleteById(id);
    }


    public EcbMaterialType getObjectPassId(Integer id) {
        EcbMaterialType record = new EcbMaterialType();
        record.setId(id);
        return ecbMaterialTypeMapper.getSysObject(record);
    }
}
