package org.jeecg.modules.cable.controller.systemEcable;

import org.jeecg.modules.cable.model.systemEcable.EcbShieldModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcbShieldController {
    @Resource
    EcbShieldModel ecbShieldModel;
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息

    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbShield/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbShieldModel.getListAndCount(request);
        }
        return map;
    }

    //根据EcbShield获取EcbShield
    @PostMapping({"/ecableErpPc/ecbShield/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbShieldModel.getObject(request);
        }
        return map;
    }
}
