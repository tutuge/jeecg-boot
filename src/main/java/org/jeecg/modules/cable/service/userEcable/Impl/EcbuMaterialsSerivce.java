package org.jeecg.modules.cable.service.userEcable.Impl;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsBaseBo;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsDealBo;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsListBo;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsSortBo;
import org.jeecg.modules.cable.controller.userEcable.materials.vo.MaterialsVo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMaterialsMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuMaterialTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EcbuMaterialsSerivce {

    @Resource
    private EcbuMaterialsMapper ecbuMaterialsMapper;
    @Resource
    private EcbuMaterialTypeService ecbuMaterialTypeService;


    public MaterialsVo getList(EcbuMaterialsListBo bo) {
        EcbuMaterials record = new EcbuMaterials();
        record.setId(bo.getId());
        record.setStartType(bo.getStartType());
        record.setMaterialTypeId(bo.getMaterialTypeId());
        List<EcbuMaterials> list = ecbuMaterialsMapper.getSysList(record);
        long count = ecbuMaterialsMapper.getSysCount(record);
        return new MaterialsVo(list, count);
    }

    public List<EcbuMaterials> getConductor(Integer ecCompanyId) {
        //查询导体的类型id
        EcbuMaterialType type = new EcbuMaterialType();
        type.setMaterialType(1);
        type.setStartType(true);
        type.setEcCompanyId(ecCompanyId);
        EcbuMaterialType object = ecbuMaterialTypeService.getObject(type);
        Integer id = object.getId();
        //根据类型id进行查询
        EcbuMaterials record = new EcbuMaterials();
        record.setEcCompanyId(ecCompanyId);
        record.setStartType(true);
        record.setMaterialTypeId(id);
        List<EcbuMaterials> list = ecbuMaterialsMapper.getSysList(record);
        return list;
    }


    public EcbuMaterials getObject(EcbuMaterialsBaseBo bo) {
        return getObjectPassId(bo.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(EcbuMaterialsDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        Integer id = bo.getId();
        Integer conductorType = bo.getConductorType();
        Integer materialTypeId = bo.getMaterialTypeId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        BigDecimal resistivity = bo.getResistivity();
        String description = bo.getDescription();

        EcbuMaterials record = new EcbuMaterials();
        record.setId(id);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        record.setMaterialTypeId(materialTypeId);
        record.setEcCompanyId(ecCompanyId);
        EcbuMaterials ecbMaterials = ecbuMaterialsMapper.getSysObject(record);
        String msg;
        if (ecbMaterials != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(id)) {// 插入
            int sortId = 1;
            // 此处getObject已经limit 1 了
            EcbuMaterials record0 = new EcbuMaterials();
            record0.setMaterialTypeId(materialTypeId);
            record0.setEcCompanyId(ecCompanyId);
            ecbMaterials = ecbuMaterialsMapper.getSysObject(record0);
            if (ecbMaterials != null) {
                sortId = ecbMaterials.getSortId() + 1;
            }
            record = new EcbuMaterials();
            record.setMaterialTypeId(materialTypeId);
            record.setEcCompanyId(ecCompanyId);
            record.setStartType(true);
            record.setConductorType(conductorType);
            record.setSortId(sortId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setResistivity(resistivity);
            record.setDescription(description);
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            ecbuMaterialsMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setId(id);
            record.setMaterialTypeId(materialTypeId);
            record.setConductorType(conductorType);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setResistivity(resistivity);
            record.setDescription(description);
            record.setUpdateTime(new Date());
            ecbuMaterialsMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbuMaterialsSortBo> bos) {
        for (EcbuMaterialsSortBo bo : bos) {
            Integer ecbcId = bo.getId();
            Integer sortId = bo.getSortId();
            EcbuMaterials record = new EcbuMaterials();
            record.setId(ecbcId);
            record.setSortId(sortId);
            ecbuMaterialsMapper.updateById(record);
        }
    }


    public String start(EcbuMaterialsBaseBo bo) {
        Integer ecbcId = bo.getId();
        EcbuMaterials record = new EcbuMaterials();
        record.setId(ecbcId);
        EcbuMaterials ecbMaterials = ecbuMaterialsMapper.getSysObject(record);
        Boolean startType = ecbMaterials.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbuMaterials();
        record.setId(ecbMaterials.getId());
        record.setStartType(startType);
        ecbuMaterialsMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuMaterialsBaseBo bo) {
        Integer id = bo.getId();
        EcbuMaterials record = new EcbuMaterials();
        record.setId(id);
        EcbuMaterials ecbMaterials = ecbuMaterialsMapper.getSysObject(record);
        Integer sortId = ecbMaterials.getSortId();
        record = new EcbuMaterials();
        record.setSortId(sortId);
        List<EcbuMaterials> list = ecbuMaterialsMapper.getSysList(record);
        Integer ecbc_id;
        for (EcbuMaterials ecbuMaterials : list) {
            ecbc_id = ecbuMaterials.getId();
            sortId = ecbuMaterials.getSortId() - 1;
            record.setId(ecbc_id);
            record.setSortId(sortId);
            ecbuMaterialsMapper.updateById(record);
        }
        record = new EcbuMaterials();
        record.setId(id);
        ecbuMaterialsMapper.deleteById(record);
    }


    /***===以下是数据模型===***/
    // getObjectPassEcbcId
    public EcbuMaterials getObjectPassId(Integer id) {
        EcbuMaterials record = new EcbuMaterials();
        record.setId(id);
        return ecbuMaterialsMapper.getSysObject(record);
    }

    public Map<String, Integer> getMapStr(Integer ecCompanyId) {
        EcbuMaterials record = new EcbuMaterials();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuMaterials> list = ecbuMaterialsMapper.getSysList(record);
        Map<String, Integer> collect = list.stream().collect(Collectors.toMap(EcbuMaterials::getFullName, EcbuMaterials::getId));
        return collect;
    }
}
