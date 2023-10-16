package org.jeecg.modules.cable.controller.efficiency;

import org.jeecg.modules.cable.model.efficiency.EcdAreaModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcdAreaController {
    @Resource
    EcdAreaModel ecdAreaModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //getObject
    @PostMapping({"/ecableErpPc/ecdArea/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecdAreaModel.getObject(request);
        }
        return map;
    }
}
