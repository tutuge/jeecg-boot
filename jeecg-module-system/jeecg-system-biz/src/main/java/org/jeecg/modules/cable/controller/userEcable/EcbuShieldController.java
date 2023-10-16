package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuShieldModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "屏蔽")
@RestController
public class EcbuShieldController {
    @Resource
    EcbuShieldModel ecbuShieldModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "编辑屏蔽")
    //deal
    @PostMapping({"/ecableErpPc/ecbuShield/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuShieldModel.deal(request);
        }
        return map;
    }

    @Operation(summary = "开启屏蔽")
    //start
    @PostMapping({"/ecableErpPc/ecbuShield/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuShieldModel.start(request);
        }
        return map;
    }


    @Operation(summary = "屏蔽列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuShield/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuShieldModel.getList(request);
        }
        return map;
    }
}
