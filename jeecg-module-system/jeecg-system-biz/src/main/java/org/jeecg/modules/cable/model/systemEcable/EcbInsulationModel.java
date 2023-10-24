package org.jeecg.modules.cable.model.systemEcable;

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
import org.jeecg.modules.cable.mapper.dao.systemEcable.sys.EcbInsulationSysDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbInsulationModel {
    @Resource
    EcbInsulationSysDao insulationSysDao;

    //getList
    public InsulationVo getList(EcbInsulationListBo bo) {
        EcbInsulation record = new EcbInsulation();
        record.setStartType(bo.getStartType());
        List<EcbInsulation> list = insulationSysDao.getList(record);
        long count = insulationSysDao.getCount(record);
        return new InsulationVo(list, count);
    }

    //getObject
    public EcbInsulation getObject(EcbInsulationBaseBo bo) {
        return getObjectPassEcbiId(bo.getEcbiId());
    }

    //deal
    public String deal(EcbInsulationDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        int ecbiId = bo.getEcaId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        EcbInsulation ecbInsulation = insulationSysDao.getObject(record);
        String msg;
        if (ecbInsulation != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ecbiId == 0) {//插入
                int sortId = 1;
                ecbInsulation = insulationSysDao.getObject(null);
                if (ecbInsulation != null) {
                    sortId = ecbInsulation.getSortId() + 1;
                }
                record = new EcbInsulation();
//                    record.setEcaId(ecaId);
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
                insulationSysDao.insert(record);
                msg = "数据新增成功";
            } else {//修改
                record.setEcbiId(ecbiId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                insulationSysDao.update(record);
                msg = "数据更新成功";
            }
        }
        return msg;
    }

    //sort
    public void sort(List<EcbInsulationSortBo> bos) {
        for (EcbInsulationSortBo bo : bos) {
            int ecbiId = bo.getEcbiId();
            int sortId = bo.getSortId();
            EcbInsulation record = new EcbInsulation();
            record.setEcbiId(ecbiId);
            record.setSortId(sortId);
            insulationSysDao.update(record);
        }
    }

    //start
    public String start(EcbInsulationBaseBo bo) {

        int ecbiId = bo.getEcbiId();
        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        EcbInsulation ecbInsulation = insulationSysDao.getObject(record);
        boolean startType = ecbInsulation.getStartType();
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
        insulationSysDao.update(record);
        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbInsulationBaseBo bo) {

        int ecbiId = bo.getEcbiId();
        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        EcbInsulation ecbInsulation = insulationSysDao.getObject(record);
        int sortId = ecbInsulation.getSortId();
        record = new EcbInsulation();
        record.setSortId(sortId);
        List<EcbInsulation> list = insulationSysDao.getList(record);
        int ecbi_id;
        for (EcbInsulation ecb_insulation : list) {
            ecbi_id = ecb_insulation.getEcbiId();
            sortId = ecb_insulation.getSortId() - 1;
            record.setEcbiId(ecbi_id);
            record.setSortId(sortId);
            insulationSysDao.update(record);
        }
        record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        insulationSysDao.delete(record);
    }

    /***===以下是数据模型===***/
    //getObjectPassAbbreviation
    public EcbInsulation getObjectPassAbbreviation(String abbreviation) {
        EcbInsulation record = new EcbInsulation();
        record.setAbbreviation(abbreviation);
        return insulationSysDao.getObject(record);
    }

    //getObjectPassEcbiId
    public EcbInsulation getObjectPassEcbiId(int ecbiId) {
        EcbInsulation record = new EcbInsulation();
        record.setEcbiId(ecbiId);
        return insulationSysDao.getObject(record);
    }


}
