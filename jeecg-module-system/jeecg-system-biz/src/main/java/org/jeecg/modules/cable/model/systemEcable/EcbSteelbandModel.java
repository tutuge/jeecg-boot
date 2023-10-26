package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandDealBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandListBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandSortBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.vo.SteelbandVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.mapper.dao.systemEcable.sys.EcbSteelBandSysDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbSteelbandModel {
    @Resource
    EcbSteelBandSysDao bandSysDao;

    //getList
    public SteelbandVo getList(EcbSteelBandListBo bo) {
        EcbSteelBand record = new EcbSteelBand();
        record.setStartType(bo.getStartType());
        List<EcbSteelBand> list = bandSysDao.getList(record);
        long count = bandSysDao.getCount(record);
        return new SteelbandVo(list, count);
    }

    //getObject
    public EcbSteelBand getObject(EcbSteelBandBaseBo bo) {
        int ecbsbId = bo.getEcbsbId();
        return getObjectPassEcbsbId(ecbsbId);
    }

    //deal
    public String deal(EcbSteelBandDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbsbId = bo.getEcbsbId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();


        EcbSteelBand record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        //log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbSteelBand ecbSteelband = bandSysDao.getObject(record);
        String msg;
        if (ecbSteelband != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ObjectUtil.isNull(ecbsbId)) {//插入
                int sortId = 1;
                ecbSteelband = bandSysDao.getObject(null);
                if (ecbSteelband != null) {
                    sortId = ecbSteelband.getSortId() + 1;
                }
                record = new EcbSteelBand();
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
                bandSysDao.insert(record);
                msg = "数据新增成功";
            } else {//修改
                record.setEcbsbId(ecbsbId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                bandSysDao.update(record);
                msg = "数据更新成功";
            }
        }
        return msg;
    }

    //sort
    public void sort(List<EcbSteelBandSortBo> bos) {
        for (EcbSteelBandSortBo bo : bos) {
            int ecbsbId = bo.getEcbsbId();
            int sortId = bo.getSortId();
            EcbSteelBand record = new EcbSteelBand();
            record.setEcbsbId(ecbsbId);
            record.setSortId(sortId);
            bandSysDao.update(record);
        }
    }

    //start
    public String start(EcbSteelBandBaseBo bo) {
        int ecbsbId = bo.getEcbsbId();
        EcbSteelBand record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        EcbSteelBand ecbBag = bandSysDao.getObject(record);
        boolean startType = ecbBag.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbSteelBand();
        record.setEcbsbId(ecbBag.getEcbsbId());
        record.setStartType(startType);
        bandSysDao.update(record);
        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbSteelBandBaseBo bo) {
        int ecbsbId = bo.getEcbsbId();
        EcbSteelBand record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        EcbSteelBand ecbSteelband = bandSysDao.getObject(record);
        int sortId = ecbSteelband.getSortId();
        record = new EcbSteelBand();
        record.setSortId(sortId);
        List<EcbSteelBand> list = bandSysDao.getList(record);
        int ecbsb_id;
        for (EcbSteelBand ecb_steelband : list) {
            ecbsb_id = ecb_steelband.getEcbsbId();
            sortId = ecb_steelband.getSortId() - 1;
            record.setEcbsbId(ecbsb_id);
            record.setSortId(sortId);
            bandSysDao.update(record);
        }
        record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        bandSysDao.delete(record);
    }

    /***===数据模型===***/
    //getObjectPassAbbreviation
    public EcbSteelBand getObjectPassAbbreviation(String abbreviation) {
        EcbSteelBand record = new EcbSteelBand();
        record.setAbbreviation(abbreviation);
        return bandSysDao.getObject(record);
    }

    //getObjectPassEcbsbId
    public EcbSteelBand getObjectPassEcbsbId(int ecbsbId) {
        EcbSteelBand record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        return bandSysDao.getObject(record);
    }
}
