package org.jeecg.modules.cable.controller.userDelivery;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudModelModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcbudModelController {
    @Resource
    EcbudModelModel ecbudModelModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //deal
    @PostMapping({"/ecableErpPc/ecbudModel/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudModelModel.deal(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecbudModel/getObject"})
    public Map<String, Object> getObjectPassEcbudId(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudModelModel.getObject(request);
        }
        return map;
    }
}
