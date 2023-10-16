package org.jeecg.modules.cable.controller.userEcable;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuMicatapeModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcbuMicatapeController {
    @Resource
    EcbuMicatapeModel ecbuMicatapeModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //deal
    @PostMapping({"/ecableErpPc/ecbuMicatape/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuMicatapeModel.deal(request);
        }
        return map;
    }

    //start
    @PostMapping({"/ecableErpPc/ecbuMicatape/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuMicatapeModel.start(request);
        }
        return map;
    }

    //getList
    @PostMapping({"/ecableErpPc/ecbuMicatape/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuMicatapeModel.getList(request);
        }
        return map;
    }
}
