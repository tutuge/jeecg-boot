package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathStartBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.vo.SheathVo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathListBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbuSheathModel {
    @Resource
    EcbuSheathService ecbuSheathService;
    @Resource
    EcbSheathService ecbSheathService;
    @Resource
    EcUserService ecUserService;

    //deal
    public void deal(EcbuSheathBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbsId = bo.getEcbsId();
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSheathService.insert(record);
        } else {
            record.setEcbusId(ecbuSheath.getEcbusId());
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSheathService.update(record);
        }
    }

    //start
    public String start(EcbuSheathStartBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuSheath == null) {//插入数据
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecbsId);
            EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
            record.setEcbsId(ecbsId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbSheath.getUnitPrice());
            record.setDensity(ecbSheath.getDensity());
            record.setDescription("");
            ecbuSheathService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuSheath.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbusId(ecbuSheath.getEcbusId());
            record.setStartType(startType);
            ecbuSheathService.update(record);
        }

        return msg;
    }

    //getList
    public List<EcbuSheath> getList(EcbuSheathListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(ecUser.getEcCompanyId());

        record.setStartType(bo.getStartType());

        return ecbuSheathService.getList(record);

    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuSheath record) {
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath == null) {
            ecbuSheathService.insert(record);
        } else {
            ecbuSheathService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbsId
    public EcbuSheath getObjectPassEcCompanyIdAndEcbsId(Integer ecCompanyId, Integer ecbsId) {
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbsId(ecbsId);
        return ecbuSheathService.getObject(record);
    }

    //getObjectPassSheathStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuSheath getObjectPassSheathStr(Integer ecuId, String objectStr) {
        EcbuSheath object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuSheath record = new EcbuSheath();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuSheath> list = ecbuSheathService.getList(record);
        for (EcbuSheath ecbu_sheath : list) {
            Integer ecbsid = ecbu_sheath.getEcbsId();
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecbsid);
            EcbSheath sheath = ecbSheathService.getObject(recordEcbSheath);
            if ("聚氯乙烯（一代）".equals(objectStr)) {
                objectStr = "D1";
            }
            if (sheath.getAbbreviation().equals(objectStr)) {
                object = ecbu_sheath;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(ecCompanyId);
        ecbuSheathService.delete(record);
    }

    //getObjectPassEcbusid
    public EcbuSheath getObjectPassEcbusid(Integer ecbusid) {
        EcbuSheath record = new EcbuSheath();
        record.setEcbusId(ecbusid);
        return ecbuSheathService.getObject(record);
    }

    //getListAndCount
    public SheathVo getListAndCount(EcbSheathBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbSheath record = new EcbSheath();

        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbSheath> list = ecbSheathService.getList(record);
        long count = ecbSheathService.getCount();
        return new SheathVo(list, count);
    }

    //getObject
    public EcbSheath getObject(EcbSheathStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecbsId = bo.getEcbsId();
        EcbSheath recordEcbSheath = new EcbSheath();
        recordEcbSheath.setEcbsId(ecbsId);
        EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath != null) {
            ecbSheath.setEcbuSheath(ecbuSheath);
        }
        return ecbSheath;
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbSheath> getListStart() {
        EcbSheath record = new EcbSheath();
        record.setStartType(true);
        return ecbSheathService.getListStart(record);
    }

    // getListSilkName 获取丝型号名称 为报价页面提供数据
    public List<EcbSheath> getListSilkName(Integer ecuId) {
        List<EcbSheath> list;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        Boolean startType = true;
        EcbSheath record = new EcbSheath();
        record.setStartType(startType);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        list = ecbSheathService.getList(record);
        for (Integer i = 0; i < list.size(); i++) {
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbsId(list.get(i).getEcbsId());
            recordEcbuSheath.setEcCompanyId(ecUser.getEcCompanyId());
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            if (ecbuSheath != null) {
                if (list.get(i).getAbbreviation().contains("D2")) {
                    list.remove(i);
                    i--;
                } else if (list.get(i).getAbbreviation().contains("D1")) {
                    list.get(i).setAbbreviation("");
                }
            } else {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
}
