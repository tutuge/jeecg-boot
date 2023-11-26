package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathDealBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldListBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldSortBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.vo.ShieldVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbShieldMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbShieldModel {
    @Resource
    EcbShieldMapper shieldSysMapper;

    public ShieldVo getList(EcbShieldListBo bo) {
        EcbShield record = new EcbShield();
        record.setStartType(bo.getStartType());
        List<EcbShield> list = shieldSysMapper.getSysList(record);
        long count = shieldSysMapper.getSysCount(record);
        return new ShieldVo(list, count);
    }


    public EcbShield getObject(EcbShieldBaseBo bo) {
        return getObjectPassEcbsId(bo.getEcbsId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbSheathDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbsId = bo.getEcbsId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbShield record = new EcbShield();
        record.setEcbsId(ecbsId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        // log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbShield ecbBag = shieldSysMapper.getObject(record);
        String msg;
        if (ecbBag != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbsId)) {// 插入
            Integer sortId = 1;
            ecbBag = shieldSysMapper.getObject(null);
            if (ecbBag != null) {
                sortId = ecbBag.getSortId() + 1;
            }
            record = new EcbShield();
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
            shieldSysMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbsId(ecbsId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(System.currentTimeMillis());
            shieldSysMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    public void sort(List<EcbShieldSortBo> bos) {
        for (EcbShieldSortBo bo : bos) {
            Integer ecbsId = bo.getEcbsId();
            Integer sortId = bo.getSortId();
            EcbShield record = new EcbShield();
            record.setEcbsId(ecbsId);
            record.setSortId(sortId);
            shieldSysMapper.updateById(record);
        }
    }


    public String start(EcbShieldBaseBo bo) {

        Integer ecbsId = bo.getEcbsId();
        EcbShield record = new EcbShield();
        record.setEcbsId(ecbsId);
        EcbShield ecbShield = shieldSysMapper.getObject(record);
        Boolean startType = ecbShield.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbShield();
        record.setEcbsId(ecbShield.getEcbsId());
        record.setStartType(startType);
        shieldSysMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbShieldBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbShield record = new EcbShield();
        record.setEcbsId(ecbsId);
        EcbShield ecbShield = shieldSysMapper.getObject(record);
        Integer sortId = ecbShield.getSortId();
        record = new EcbShield();
        record.setSortId(sortId);
        List<EcbShield> list = shieldSysMapper.getSysList(record);
        Integer ecbs_id;
        for (EcbShield ecb_shield : list) {
            ecbs_id = ecb_shield.getEcbsId();
            sortId = ecb_shield.getSortId() - 1;
            record.setEcbsId(ecbs_id);
            record.setSortId(sortId);
            shieldSysMapper.updateById(record);
        }
        shieldSysMapper.deleteById(record);
    }

    
    // getObjectPassAbbreviation
    public EcbShield getObjectPassAbbreviation(String abbreviation) {
        EcbShield record = new EcbShield();
        record.setAbbreviation(abbreviation);
        return shieldSysMapper.getObject(record);
    }

    // getObjectPassEcbcId
    public EcbShield getObjectPassEcbsId(Integer ecbsId) {
        EcbShield record = new EcbShield();
        record.setEcbsId(ecbsId);
        return shieldSysMapper.getObject(record);
    }
}
