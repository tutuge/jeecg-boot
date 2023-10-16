package org.jeecg.modules.cable.controller.userDelivery;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudDeliveryModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcbudDeliveryController {
    @Resource
    EcbudDeliveryModel ecbudDeliveryModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //getObject
    @PostMapping({"/ecableErpPc/ecbudDelivery/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudDeliveryModel.getObject(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecbudDelivery/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudDeliveryModel.deal(request);
        }
        return map;
    }
}
