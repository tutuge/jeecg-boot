package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationDealBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationListBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationSortBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.vo.InsulationVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbInsulationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbInsulationModel {
    @Resource
    EcbInsulationMapper insulationSysMapper;

    // getList
    public InsulationVo getList(EcbInsulationListBo bo) {
        EcbInsulation record = new EcbInsulation();
        record.setStartType(bo.getStartType());
        List<EcbInsulation> list = insulationSysMapper.getSysList(record);
        long count = insulationSysMapper.getSysCount(record);
        return new InsulationVo(list, count);
    }

    // getObject
    public EcbInsulation getObject(EcbInsulationBaseBo bo) {
        return getObjectPassEcbiId(bo.getEcbiId());
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbInsulationDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbiId = bo.getEcbiId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        EcbInsulation ecbInsulation = insulationSysMapper.getSysObject(record);
        String msg;
        if (ecbInsulation != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ObjectUtil.isNull(ecbiId)) {// 插入
                Integer sortId = 1;
                ecbInsulation = insulationSysMapper.getSysObject(null);
                if (ecbInsulation != null) {
                    sortId = ecbInsulation.getSortId() + 1;
                }
                record = new EcbInsulation();
                record.setEcaId(sysUser.getUserId());
                record.setEcaName(sysUser.getUsername());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                insulationSysMapper.insert(record);
                msg = "数据新增成功";
            } else {// 修改
                record.setEcbiId(ecbiId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                insulationSysMapper.updateById(record);
                msg = "数据更新成功";
            }
        }
        return msg;
    }

    // sort
    public void sort(List<EcbInsulationSortBo> bos) {
        for (EcbInsulationSortBo bo : bos) {
            Integer ecbiId = bo.getEcbiId();
            Integer sortId = bo.getSortId();
            EcbInsulation record = new EcbInsulation();
            record.setEcbiId(ecbiId);
            record.setSortId(sortId);
            insulationSysMapper.updateById(record);
        }
    }

    // start
    public String start(EcbInsulationBaseBo bo) {

        Integer ecbiId = bo.getEcbiId();
        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        EcbInsulation ecbInsulation = insulationSysMapper.getSysObject(record);
        Boolean startType = ecbInsulation.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbInsulation();
        record.setEcbiId(ecbInsulation.getEcbiId());
        record.setStartType(startType);
        insulationSysMapper.updateById(record);
        return msg;
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbInsulationBaseBo bo) {

        Integer ecbiId = bo.getEcbiId();
        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        EcbInsulation ecbInsulation = insulationSysMapper.getSysObject(record);
        Integer sortId = ecbInsulation.getSortId();
        record = new EcbInsulation();
        record.setSortId(sortId);
        List<EcbInsulation> list = insulationSysMapper.getSysList(record);
        Integer ecbi_id;
        for (EcbInsulation ecb_insulation : list) {
            ecbi_id = ecb_insulation.getEcbiId();
            sortId = ecb_insulation.getSortId() - 1;
            record.setEcbiId(ecbi_id);
            record.setSortId(sortId);
            insulationSysMapper.updateById(record);
        }
        insulationSysMapper.deleteById(ecbiId);
    }

    /***===以下是数据模型===***/
    // getObjectPassAbbreviation
    public EcbInsulation getObjectPassAbbreviation(String abbreviation) {
        EcbInsulation record = new EcbInsulation();
        record.setAbbreviation(abbreviation);
        return insulationSysMapper.getSysObject(record);
    }

    // getObjectPassEcbiId
    public EcbInsulation getObjectPassEcbiId(Integer ecbiId) {
        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        return insulationSysMapper.getSysObject(record);
    }


}
