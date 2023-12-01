package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.collection.CollUtil;
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
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class EcbShieldModel {
    @Resource
    EcbShieldService ecbShieldService;
    @Resource
    private EcbuShieldService ecbuShieldService;

    public ShieldVo getList(EcbShieldListBo bo) {
        EcbShield record = new EcbShield();
        record.setStartType(bo.getStartType());
        List<EcbShield> list = ecbShieldService.getList(record);
        long count = ecbShieldService.getSysCount(record);
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
        EcbShield ecbBag = ecbShieldService.getObject(record);
        String msg;
        if (ecbBag != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbsId)) {// 插入
            Integer sortId = 1;
            ecbBag = ecbShieldService.getObject(null);
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
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            ecbShieldService.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbsId(ecbsId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(new Date());
            ecbShieldService.updateById(record);
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
            ecbShieldService.updateById(record);
        }
    }


    public String start(EcbShieldBaseBo bo) {

        Integer ecbsId = bo.getEcbsId();
        EcbShield record = new EcbShield();
        record.setEcbsId(ecbsId);
        EcbShield ecbShield = ecbShieldService.getObject(record);
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
        ecbShieldService.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbShieldBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbuShield shield = new EcbuShield();
        shield.setEcbsId(ecbsId);
        List<EcbuShield> list1 = ecbuShieldService.getList(shield);
        if (CollUtil.isNotEmpty(list1)) {
            throw new RuntimeException("此记录已被用户记录关联使用，无法删除！");
        }
        EcbShield record = new EcbShield();
        record.setEcbsId(ecbsId);
        EcbShield ecbShield = ecbShieldService.getObject(record);
        Integer sortId = ecbShield.getSortId();
        record = new EcbShield();
        record.setSortId(sortId);
        List<EcbShield> list = ecbShieldService.getSysList(record);
        Integer newEcbsId;
        for (EcbShield ecb_shield : list) {
            newEcbsId = ecb_shield.getEcbsId();
            sortId = ecb_shield.getSortId() - 1;
            record.setEcbsId(newEcbsId);
            record.setSortId(sortId);
            ecbShieldService.updateById(record);
        }
        ecbShieldService.deleteById(ecbsId);
    }


    // getObjectPassAbbreviation
    public EcbShield getObjectPassAbbreviation(String abbreviation) {
        EcbShield record = new EcbShield();
        record.setAbbreviation(abbreviation);
        return ecbShieldService.getObject(record);
    }

    // getObjectPassEcbcId
    public EcbShield getObjectPassEcbsId(Integer ecbsId) {
        EcbShield record = new EcbShield();
        record.setEcbsId(ecbsId);
        return ecbShieldService.getObject(record);
    }
}
