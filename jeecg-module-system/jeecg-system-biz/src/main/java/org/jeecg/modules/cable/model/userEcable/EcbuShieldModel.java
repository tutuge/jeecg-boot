package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldBo;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldListBo;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.model.systemEcable.EcbShieldModel;
import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbuShieldModel {
    @Resource
    EcbuShieldService ecbuShieldService;
    @Resource
    EcbShieldService ecbShieldService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbShieldModel ecbShieldModel;

    //deal
    public void deal(EcbuShieldBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecbsId = bo.getEcbsId();

        EcbuShield record = new EcbuShield();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
        if (ecbuShield == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuShieldService.insert(record);
        } else {
            record.setEcbusId(ecbuShield.getEcbusId());
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuShieldService.update(record);
        }
    }

    //start
    public String start(EcbuShieldStartBo bo) {

        Integer ecbsId = bo.getEcbsId();
        EcbuShield record = new EcbuShield();
        record.setEcbsId(ecbsId);

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
        boolean startType;

        String msg = "";
        if (ecbuShield == null) {//插入数据
            EcbShield recordEcbShield = new EcbShield();
            recordEcbShield.setEcbsId(ecbsId);
            EcbShield ecbShield = ecbShieldService.getObject(recordEcbShield);
            record.setEcbsId(ecbsId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbShield.getUnitPrice());
            record.setDensity(ecbShield.getDensity());
            record.setDescription("");
            ecbuShieldService.insert(record);

            msg = "数据启用成功";
        } else {
            startType = ecbuShield.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbusId(ecbuShield.getEcbusId());
            record.setStartType(startType);
            //log.info(CommonFunction.getGson().toJson(record));
            ecbuShieldService.update(record);
        }
        ecbShieldModel.loadData();//txt文档
        return msg;
    }

    //getList
    public List<EcbuShield> getList(EcbuShieldListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuShield record = new EcbuShield();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuShieldService.getList(record);
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuShield record) {
        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
        if (ecbuShield == null) {
            ecbuShieldService.insert(record);
        } else {
            ecbuShieldService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbsId
    public EcbuShield getObjectPassEcCompanyIdAndEcbsId(int ecCompanyId, int ecbsId) {
        EcbuShield record = new EcbuShield();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbsId(ecbsId);
        return ecbuShieldService.getObject(record);
    }

    //getObjectPassShieldStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuShield getObjectPassShieldStr(int ecuId, String objectStr) {
        EcbuShield object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuShield record = new EcbuShield();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuShield> list = ecbuShieldService.getList(record);
        for (EcbuShield ecbu_shield : list) {
            int ecbsId = ecbu_shield.getEcbsId();
            EcbShield recordEcbShield = new EcbShield();
            recordEcbShield.setEcbsId(ecbsId);
            EcbShield shield = ecbShieldService.getObject(recordEcbShield);
            if (shield.getAbbreviation().equals(objectStr)) {
                object = ecbu_shield;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuShield record = new EcbuShield();
        record.setEcCompanyId(ecCompanyId);
        ecbuShieldService.delete(record);
    }
}
