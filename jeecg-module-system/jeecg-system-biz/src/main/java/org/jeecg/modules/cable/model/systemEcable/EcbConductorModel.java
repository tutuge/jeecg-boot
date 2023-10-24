package org.jeecg.modules.cable.model.systemEcable;

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
import org.jeecg.modules.cable.mapper.dao.systemEcable.sys.EcbConductorSysDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbConductorModel {

    @Resource
    EcbConductorSysDao ecbConductorSysDao;

    //getList
    public ConductorVo getList(EcbConductorListBo bo) {

        EcbConductor record = new EcbConductor();
        record.setStartType(bo.getStartType());

        List<EcbConductor> list = ecbConductorSysDao.getList(record);
        long count = ecbConductorSysDao.getCount(record);
        return new ConductorVo(list, count);
    }

    //getObject
    public EcbConductor getObject(EcbConductorBaseBo bo) {
        return getObjectPassEcbcId(bo.getEcbcId());
    }

    //deal
    public String deal(EcbConductorDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        int ecbcId = bo.getEcbcId();
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
        EcbConductor ecbConductor = ecbConductorSysDao.getObject(record);
        String msg;
        if (ecbConductor != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ecbcId == 0) {//插入
                int sortId = 1;
                ecbConductor = ecbConductorSysDao.getObject(null);
                if (ecbConductor != null) {
                    sortId = ecbConductor.getSortId() + 1;
                }
                record = new EcbConductor();
//                    record.setEcaId(ecaId);
                record.setEcaName(sysUser.getUsername());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setResistivity(resistivity);
                record.setDescription(description);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                ecbConductorSysDao.insert(record);
                msg = "数据新增成功";
            } else {//修改
                record.setEcbcId(ecbcId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setResistivity(resistivity);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                ecbConductorSysDao.update(record);
                msg = "数据更新成功";
            }
        }

        return msg;
    }

    //sort
    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbConductorSortBo> bos) {
        for (EcbConductorSortBo bo : bos) {
            int ecbcId = bo.getEcbcId();
            int sortId = bo.getSortId();
            EcbConductor record = new EcbConductor();
            record.setEcbcId(ecbcId);
            record.setSortId(sortId);
            ecbConductorSysDao.update(record);
        }
    }

    //start
    public String start(EcbConductorBaseBo bo) {

        int ecbcId = bo.getEcbcId();
        EcbConductor record = new EcbConductor();
        record.setEcbcId(ecbcId);
        EcbConductor ecbConductor = ecbConductorSysDao.getObject(record);
        boolean startType = ecbConductor.getStartType();
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
        ecbConductorSysDao.update(record);
        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbConductorBaseBo bo) {

        int ecbcId = bo.getEcbcId();
        EcbConductor record = new EcbConductor();
        record.setEcbcId(ecbcId);
        EcbConductor ecbConductor = ecbConductorSysDao.getObject(record);
        int sortId = ecbConductor.getSortId();
        record = new EcbConductor();
        record.setSortId(sortId);
        List<EcbConductor> list = ecbConductorSysDao.getList(record);
        int ecbc_id;
        for (EcbConductor ecb_conductor : list) {
            ecbc_id = ecb_conductor.getEcbcId();
            sortId = ecb_conductor.getSortId() - 1;
            record.setEcbcId(ecbc_id);
            record.setSortId(sortId);
            ecbConductorSysDao.update(record);
        }
        record = new EcbConductor();
        record.setEcbcId(ecbcId);
        ecbConductorSysDao.delete(record);
    }


    /***===以下是数据模型===***/
    //getObjectPassEcbcId
    public EcbConductor getObjectPassEcbcId(int ecbcId) {
        EcbConductor record = new EcbConductor();
        record.setEcbcId(ecbcId);
        return ecbConductorSysDao.getObject(record);
    }
}
