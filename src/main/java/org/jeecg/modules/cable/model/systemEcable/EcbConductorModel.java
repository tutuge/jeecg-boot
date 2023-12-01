package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorDealBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorListBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorSortBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.vo.ConductorVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbConductorMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EcbConductorModel {

    @Resource
    EcbConductorMapper ecbConductorMapper;
    @Resource
    private EcbuConductorService ecbuConductorService;


    public ConductorVo getList(EcbConductorListBo bo) {
        EcbConductor record = new EcbConductor();
        record.setStartType(bo.getStartType());
        List<EcbConductor> list = ecbConductorMapper.getSysList(record);
        long count = ecbConductorMapper.getSyCount(record);
        return new ConductorVo(list, count);
    }


    public EcbConductor getObject(EcbConductorBaseBo bo) {
        return getObjectPassEcbcId(bo.getEcbcId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbConductorDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbcId = bo.getEcbcId();
        Integer conductorType = bo.getConductorType();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        BigDecimal resistivity = bo.getResistivity();
        String description = bo.getDescription();

        EcbConductor record = new EcbConductor();
        record.setEcbcId(ecbcId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        EcbConductor ecbConductor = ecbConductorMapper.getSysObject(record);
        String msg;
        if (ecbConductor != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbcId)) {// 插入
            int sortId = 1;
            // 此处getObject已经limit 1 了
            ecbConductor = ecbConductorMapper.getSysObject(null);
            if (ecbConductor != null) {
                sortId = ecbConductor.getSortId() + 1;
            }
            record = new EcbConductor();
            record.setEcaId(sysUser.getUserId());
            record.setEcaName(sysUser.getUsername());
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
            ecbConductorMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbcId(ecbcId);
            record.setConductorType(conductorType);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setResistivity(resistivity);
            record.setDescription(description);
            record.setUpdateTime(new Date());
            ecbConductorMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbConductorSortBo> bos) {
        for (EcbConductorSortBo bo : bos) {
            Integer ecbcId = bo.getEcbcId();
            Integer sortId = bo.getSortId();
            EcbConductor record = new EcbConductor();
            record.setEcbcId(ecbcId);
            record.setSortId(sortId);
            ecbConductorMapper.updateById(record);
        }
    }


    public String start(EcbConductorBaseBo bo) {
        Integer ecbcId = bo.getEcbcId();
        EcbConductor record = new EcbConductor();
        record.setEcbcId(ecbcId);
        EcbConductor ecbConductor = ecbConductorMapper.getSysObject(record);
        Boolean startType = ecbConductor.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbConductor();
        record.setEcbcId(ecbConductor.getEcbcId());
        record.setStartType(startType);
        ecbConductorMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbConductorBaseBo bo) {
        Integer ecbcId = bo.getEcbcId();
        //判断下用户是否在使用这个导体
        EcbuConductor conductor = new EcbuConductor();
        conductor.setEcbcId(ecbcId);
        List<EcbuConductor> list1 = ecbuConductorService.getList(conductor);
        if (CollUtil.isNotEmpty(list1)) {
            throw new RuntimeException("此记录已被用户记录关联使用，无法删除！");
        }
        EcbConductor record = new EcbConductor();
        record.setEcbcId(ecbcId);
        EcbConductor ecbConductor = ecbConductorMapper.getSysObject(record);
        Integer sortId = ecbConductor.getSortId();
        record = new EcbConductor();
        record.setSortId(sortId);
        List<EcbConductor> list = ecbConductorMapper.getSysList(record);
        Integer ecbc_id;
        for (EcbConductor ecb_conductor : list) {
            ecbc_id = ecb_conductor.getEcbcId();
            sortId = ecb_conductor.getSortId() - 1;
            record.setEcbcId(ecbc_id);
            record.setSortId(sortId);
            ecbConductorMapper.updateById(record);
        }
        record = new EcbConductor();
        record.setEcbcId(ecbcId);
        ecbConductorMapper.deleteById(record);
    }


    /***===以下是数据模型===***/
    // getObjectPassEcbcId
    public EcbConductor getObjectPassEcbcId(Integer ecbcId) {
        EcbConductor record = new EcbConductor();
        record.setEcbcId(ecbcId);
        return ecbConductorMapper.getSysObject(record);
    }
}
