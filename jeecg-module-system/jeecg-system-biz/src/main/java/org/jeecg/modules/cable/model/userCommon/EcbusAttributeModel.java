package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.service.userCommon.EcbusAttributeService;
import org.springframework.stereotype.Service;

@Service
public class EcbusAttributeModel {

    @Resource
    EcbusAttributeService ecbusAttributeService;
    @Resource
    EcUserModel ecUserModel;

    //deal
    public void deal(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbusAttribute record = new EcbusAttribute();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if (request.getParameter("pcShowOrHide") != null) {//铜利润
            boolean pcShowOrHide = Boolean.parseBoolean(request.getParameter("pcShowOrHide"));
            record.setPcShowOrHide(pcShowOrHide);
        }
        if (request.getParameter("paShowOrHide") != null) {//铝利润
            boolean paShowOrHide = Boolean.parseBoolean(request.getParameter("paShowOrHide"));
            record.setPaShowOrHide(paShowOrHide);
        }
        if (request.getParameter("dmShowOrHide") != null) {//运费加点
            boolean dmShowOrHide = Boolean.parseBoolean(request.getParameter("dmShowOrHide"));
            record.setDmShowOrHide(dmShowOrHide);
        }
        deal(record);
    }

    //getObject
    public EcbusAttribute getObject() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbusAttribute record = new EcbusAttribute();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        return ecbusAttributeService.getObject(record);
    }

    /***===数据模型===***/
//deal
    public void deal(EcbusAttribute record) {
        EcbusAttribute recordEcbusAttribute = new EcbusAttribute();
        recordEcbusAttribute.setEcCompanyId(record.getEcCompanyId());
        EcbusAttribute ecbusAttribute = ecbusAttributeService.getObject(record);
        if (ecbusAttribute == null) {
            ecbusAttributeService.insert(record);
        } else {
            ecbusAttributeService.update(record);
        }
    }

}
