package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.jeecg.modules.cable.entity.user.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcdTaxpointModel {
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdTaxpointService ecdTaxpointService;//系统税点
    @Resource
    EcduTaxpointService ecduTaxpointService;//用户税点

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcdTaxpoint record = new EcdTaxpoint();
        if (request.getParameter("startType") != null) {
            boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcdTaxpoint> list = ecdTaxpointService.getList(record);
        long count = ecdTaxpointService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcdTaxpoint record = new EcdTaxpoint();
        if (request.getParameter("ecdtId") != null) {
            int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
            record.setEcdtId(ecdtId);
        }
        EcdTaxpoint object = ecdTaxpointService.getObject(record);
        if (object != null) {
            int ecdtId = object.getEcdtId();
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(ecdtId);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);
            object.setEcduTaxpoint(ecduTaxpoint);
        }
        map.put("object", object);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }
}
