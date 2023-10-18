package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandListBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.model.systemEcable.EcbSteelbandModel;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class EcbuSteelbandModel {
    @Resource
    EcbuSteelbandService ecbuSteelbandService;
    @Resource
    EcbSteelbandService ecbSteelbandService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbSteelbandModel ecbSteelbandModel;

    //deal
    public void deal(EcbuSteelBandBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecbsbId = bo.getEcbsbId();

        EcbuSteelband record = new EcbuSteelband();
        record.setEcbsbId(ecbsbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSteelbandService.insert(record);
        } else {
            record.setEcbusId(ecbuSteelband.getEcbusId());
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSteelbandService.update(record);
        }
        ecbSteelbandModel.loadData();//txt文档
    }

    //start
    public String start(EcbuSteelBandStartBo bo) {
        Integer ecbsbId = bo.getEcbsbId();
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbsbId(ecbsbId);
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        boolean startType;
        String msg = "";
        if (ecbuSteelband == null) {//插入数据
            EcbSteelband recordEcbSteelband = new EcbSteelband();
            recordEcbSteelband.setEcbsbId(ecbsbId);
            EcbSteelband ecbSteelband = ecbSteelbandService.getObject(recordEcbSteelband);
            record.setEcbsbId(ecbsbId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbSteelband.getUnitPrice());
            record.setDensity(ecbSteelband.getDensity());
            record.setDescription("");
            ecbuSteelbandService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuSteelband.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbusId(ecbuSteelband.getEcbusId());
            record.setStartType(startType);
            ecbuSteelbandService.update(record);
        }
        ecbSteelbandModel.loadData();//txt文档
        return msg;
    }

    //getList
    public List<EcbuSteelband> getList(EcbuSteelBandListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuSteelbandService.getList(record);
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuSteelband record) {
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband == null) {
            ecbuSteelbandService.insert(record);
        } else {
            ecbuSteelbandService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbsbId
    public EcbuSteelband getObjectPassEcCompanyIdAndEcbsbId(int ecCompanyId, int ecbsbId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbsbId(ecbsbId);
        return ecbuSteelbandService.getObject(record);
    }

    //getObjectPassSteelbandStr 通过钢带类型类型获取钢带 为计算成本提供数据
    public EcbuSteelband getObjectPassSteelbandStr(int ecuId, String objectStr) {
        EcbuSteelband object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuSteelband record = new EcbuSteelband();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuSteelband> list = ecbuSteelbandService.getList(record);
        for (EcbuSteelband ecbuSteelband : list) {
            int ecbsbId = ecbuSteelband.getEcbsbId();
            EcbSteelband recordEcbSteelband = new EcbSteelband();
            recordEcbSteelband.setEcbsbId(ecbsbId);
            EcbSteelband steelband = ecbSteelbandService.getObject(recordEcbSteelband);
            if (steelband.getAbbreviation().equals(objectStr)) {
                object = ecbuSteelband;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecCompanyId);
        ecbuSteelbandService.delete(record);
    }

    //getObjectPassEcbusbId
    public EcbuSteelband getObjectPassEcbusbId(int ecbusbId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbusId(ecbusbId);
        return ecbuSteelbandService.getObject(record);
    }
}
