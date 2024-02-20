package org.jeecg.modules.cable.model.userEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbMaterialDealBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialBaseBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialListBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialSortBo;
import org.jeecg.modules.cable.controller.userEcable.material.vo.MaterialTypeVo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMaterialTypeMapper;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMaterialsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EcbuMaterialTypeModel {
    @Resource
    private EcbuMaterialTypeMapper ecbuMaterialTypeMapper;
    @Resource
    private EcbuMaterialsMapper ecbuMaterialsMapper;


    public MaterialTypeVo getList(EcbuMaterialListBo bo) {
        EcbuMaterialType record = new EcbuMaterialType();
        record.setStartType(bo.getStartType());
        List<EcbuMaterialType> list = ecbuMaterialTypeMapper.getList(record);
        long count = ecbuMaterialTypeMapper.getSysCount(record);
        return new MaterialTypeVo(list, count);
    }


    public EcbuMaterialType getObject(EcbuMaterialBaseBo bo) {
        return getObjectPassId(bo.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(EcbMaterialDealBo bo) {
        Integer id = bo.getId();
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        String fullName = bo.getFullName();
        String description = bo.getDescription();
        Integer materialType = bo.getMaterialType();
        EcbuMaterialType record = new EcbuMaterialType();
        record.setId(id);
        record.setFullName(fullName);
        record.setEcCompanyId(ecCompanyId);
        EcbuMaterialType ecbMaterialType = ecbuMaterialTypeMapper.getObject(record);
        String msg;
        if (ecbMaterialType != null) {
            throw new RuntimeException("全称已占用");
        }
        if (materialType != 0) {
            EcbuMaterialType record0 = new EcbuMaterialType();
            record0.setMaterialType(materialType);
            record0.setEcCompanyId(ecCompanyId);
            ecbMaterialType = ecbuMaterialTypeMapper.getObject(record0);
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
            EcbuMaterialType record1 = new EcbuMaterialType();
            record1.setEcCompanyId(ecCompanyId);
            ecbMaterialType = ecbuMaterialTypeMapper.getObject(record1);
            if (ecbMaterialType != null) {
                sortId = ecbMaterialType.getSortId() + 1;
            }
            record = new EcbuMaterialType();
            record.setEcCompanyId(ecCompanyId);
            record.setStartType(true);
            record.setSortId(sortId);
            record.setFullName(fullName);
            record.setDescription(description);
            record.setMaterialType(materialType);
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            ecbuMaterialTypeMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setId(id);
            record.setFullName(fullName);
            record.setDescription(description);
            record.setMaterialType(materialType);
            record.setUpdateTime(new Date());
            ecbuMaterialTypeMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbuMaterialSortBo> bos) {
        for (EcbuMaterialSortBo bo : bos) {
            Integer id = bo.getId();
            Integer sortId = bo.getSortId();
            EcbuMaterialType record = new EcbuMaterialType();
            record.setId(id);
            record.setSortId(sortId);
            ecbuMaterialTypeMapper.updateById(record);
        }
    }


    public String start(EcbuMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbuMaterialType record = new EcbuMaterialType();
        record.setId(id);
        EcbuMaterialType ecbMaterialType = ecbuMaterialTypeMapper.getObject(record);
        Boolean startType = ecbMaterialType.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbuMaterialType();
        record.setId(ecbMaterialType.getId());
        record.setStartType(startType);
        ecbuMaterialTypeMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuMaterialBaseBo bo) {
        Integer id = bo.getId();
        EcbuMaterials ecbuMaterials = new EcbuMaterials();
        ecbuMaterials.setMaterialTypeId(id);
        long sysCount = ecbuMaterialsMapper.getSysCount(ecbuMaterials);
        if (sysCount > 0) {
            throw new RuntimeException("当前材料类型还在被使用，无法删除");
        }
        EcbuMaterialType object = ecbuMaterialTypeMapper.selectById(id);
        if (object.getMaterialType() == 1) {
            throw new RuntimeException("不允许删除导体材料");
        }
        if (object.getMaterialType() == 2) {
            throw new RuntimeException("不允许删除填充物材料");
        }
        Integer sortId = object.getSortId();
        ecbuMaterialTypeMapper.reduceSort(sortId);
        ecbuMaterialTypeMapper.deleteById(id);
    }


    // getObjectPassEcbbId
    public EcbuMaterialType getObjectPassId(Integer id) {
        EcbuMaterialType record = new EcbuMaterialType();
        record.setId(id);
        return ecbuMaterialTypeMapper.getObject(record);
    }
}
