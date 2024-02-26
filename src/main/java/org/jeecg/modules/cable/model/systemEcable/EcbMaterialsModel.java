package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsDealBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsListBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsSortBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.vo.MaterialsVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EcbMaterialsModel {

    @Resource
    EcbMaterialsMapper ecbMaterialsMapper;
    //@Resource
    //private EcbuConductorService ecbuConductorService;


    public MaterialsVo getList(EcbMaterialsListBo bo) {
        EcbMaterials record = new EcbMaterials();
        record.setStartType(bo.getStartType());
        List<EcbMaterials> list = ecbMaterialsMapper.getSysList(record);
        long count = ecbMaterialsMapper.getSysCount(record);
        return new MaterialsVo(list, count);
    }


    public EcbMaterials getObject(EcbMaterialsBaseBo bo) {
        return getObjectPassId(bo.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(EcbMaterialsDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer id = bo.getId();
        Integer conductorType = bo.getConductorType();
        Integer materialId = bo.getMaterialId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        BigDecimal resistivity = bo.getResistivity();
        String description = bo.getDescription();

        EcbMaterials record = new EcbMaterials();
        record.setId(id);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        EcbMaterials ecbMaterials = ecbMaterialsMapper.getSysObject(record);
        String msg;
        if (ecbMaterials != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(id)) {// 插入
            int sortId = 1;
            // 此处getObject已经limit 1 了
            ecbMaterials = ecbMaterialsMapper.getSysObject(null);
            if (ecbMaterials != null) {
                sortId = ecbMaterials.getSortId() + 1;
            }
            record = new EcbMaterials();
            record.setEcaId(sysUser.getUserId());
            record.setEcaName(sysUser.getUsername());
            record.setMaterialTypeId(materialId);
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
            ecbMaterialsMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setId(id);
            record.setMaterialTypeId(materialId);
            record.setConductorType(conductorType);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setResistivity(resistivity);
            record.setDescription(description);
            record.setUpdateTime(new Date());
            ecbMaterialsMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbMaterialsSortBo> bos) {
        for (EcbMaterialsSortBo bo : bos) {
            Integer ecbcId = bo.getId();
            Integer sortId = bo.getSortId();
            EcbMaterials record = new EcbMaterials();
            record.setId(ecbcId);
            record.setSortId(sortId);
            ecbMaterialsMapper.updateById(record);
        }
    }


    public String start(EcbMaterialsBaseBo bo) {
        Integer ecbcId = bo.getId();
        EcbMaterials record = new EcbMaterials();
        record.setId(ecbcId);
        EcbMaterials ecbMaterials = ecbMaterialsMapper.getSysObject(record);
        Boolean startType = ecbMaterials.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbMaterials();
        record.setId(ecbMaterials.getId());
        record.setStartType(startType);
        ecbMaterialsMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbMaterialsBaseBo bo) {
        Integer ecbcId = bo.getId();
        //判断下用户是否在使用这个导体
        //EcbuConductor conductor = new EcbuConductor();
        //conductor.setEcbcId(ecbcId);
        //List<EcbuConductor> list1 = ecbuConductorService.getList(conductor);
        //if (CollUtil.isNotEmpty(list1)) {
        //    throw new RuntimeException("此记录已被用户记录关联使用，无法删除！");
        //}
        EcbMaterials record = new EcbMaterials();
        record.setId(ecbcId);
        EcbMaterials ecbMaterials = ecbMaterialsMapper.getSysObject(record);
        Integer sortId = ecbMaterials.getSortId();
        record = new EcbMaterials();
        record.setSortId(sortId);
        List<EcbMaterials> list = ecbMaterialsMapper.getSysList(record);
        Integer ecbc_id;
        for (EcbMaterials ecb_conductor : list) {
            ecbc_id = ecb_conductor.getId();
            sortId = ecb_conductor.getSortId() - 1;
            record.setId(ecbc_id);
            record.setSortId(sortId);
            ecbMaterialsMapper.updateById(record);
        }
        record = new EcbMaterials();
        record.setId(ecbcId);
        ecbMaterialsMapper.deleteById(record);
    }


    /***===以下是数据模型===***/
    // getObjectPassEcbcId
    public EcbMaterials getObjectPassId(Integer id) {
        EcbMaterials record = new EcbMaterials();
        record.setId(id);
        return ecbMaterialsMapper.getSysObject(record);
    }

    //获取名称与id的map
    public Map<String, Integer> getMapStr() {
        EcbMaterials record = new EcbMaterials();
        record.setStartType(true);
        List<EcbMaterials> list = ecbMaterialsMapper.getSysList(record);
        Map<String, Integer> collect = list.stream().collect(Collectors.toMap(EcbMaterials::getFullName, EcbMaterials::getId));
        return collect;
    }
}
