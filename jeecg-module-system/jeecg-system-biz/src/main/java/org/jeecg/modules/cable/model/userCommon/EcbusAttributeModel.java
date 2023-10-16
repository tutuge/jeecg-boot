package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.userCommon.EcbusAttributeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EcbusAttributeModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcbusAttributeService ecbusAttributeService;
    @Resource
    EcUserModel ecUserModel;

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
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
            status = 3;//正常操作数据
            code = "200";
            msg = "正常操作数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            EcbusAttribute record = new EcbusAttribute();
            record.setEcCompanyId(ecUser.getEcCompanyId());
            EcbusAttribute ecbusAttribute = ecbusAttributeService.getObject(record);
            map.put("object", ecbusAttribute);
            status = 3;//正常操作数据
            code = "200";
            msg = "正常操作数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
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
