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
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbSteelBandMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbSteelbandModel {
    @Resource
    EcbSteelBandMapper steelBandSysMapper;

    public SteelbandVo getList(EcbSteelBandListBo bo) {
        EcbSteelBand record = new EcbSteelBand();
        record.setStartType(bo.getStartType());
        List<EcbSteelBand> list = steelBandSysMapper.getSysList(record);
        long count = steelBandSysMapper.getSysCount(record);
        return new SteelbandVo(list, count);
    }


    public EcbSteelBand getObject(EcbSteelBandBaseBo bo) {
        Integer ecbsbId = bo.getEcbsbId();
        return getObjectPassEcbsbId(ecbsbId);
    }


    @Transactional(rollbackFor = Exception.class)
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
        // log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbSteelBand ecbSteelband = steelBandSysMapper.getSysObject(record);
        String msg;
        if (ecbSteelband != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbsbId)) {// 插入
            Integer sortId = 1;
            ecbSteelband = steelBandSysMapper.getSysObject(null);
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
            steelBandSysMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbsbId(ecbsbId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(System.currentTimeMillis());
            steelBandSysMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    public void sort(List<EcbSteelBandSortBo> bos) {
        for (EcbSteelBandSortBo bo : bos) {
            Integer ecbsbId = bo.getEcbsbId();
            Integer sortId = bo.getSortId();
            EcbSteelBand record = new EcbSteelBand();
            record.setEcbsbId(ecbsbId);
            record.setSortId(sortId);
            steelBandSysMapper.updateById(record);
        }
    }


    public String start(EcbSteelBandBaseBo bo) {
        Integer ecbsbId = bo.getEcbsbId();
        EcbSteelBand record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        EcbSteelBand ecbBag = steelBandSysMapper.getSysObject(record);
        Boolean startType = ecbBag.getStartType();
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
        steelBandSysMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbSteelBandBaseBo bo) {
        Integer ecbsbId = bo.getEcbsbId();
        EcbSteelBand record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        EcbSteelBand ecbSteelband = steelBandSysMapper.getSysObject(record);
        Integer sortId = ecbSteelband.getSortId();
        record = new EcbSteelBand();
        record.setSortId(sortId);
        List<EcbSteelBand> list = steelBandSysMapper.getSysList(record);
        Integer ecbsb_id;
        for (EcbSteelBand ecb_steelband : list) {
            ecbsb_id = ecb_steelband.getEcbsbId();
            sortId = ecb_steelband.getSortId() - 1;
            record.setEcbsbId(ecbsb_id);
            record.setSortId(sortId);
            steelBandSysMapper.updateById(record);
        }
        steelBandSysMapper.deleteById(record);
    }

    
    // getObjectPassAbbreviation
    public EcbSteelBand getObjectPassAbbreviation(String abbreviation) {
        EcbSteelBand record = new EcbSteelBand();
        record.setAbbreviation(abbreviation);
        return steelBandSysMapper.getSysObject(record);
    }

    // getObjectPassEcbsbId
    public EcbSteelBand getObjectPassEcbsbId(Integer ecbsbId) {
        EcbSteelBand record = new EcbSteelBand();
        record.setEcbsbId(ecbsbId);
        return steelBandSysMapper.getSysObject(record);
    }
}
