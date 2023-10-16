package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuSteelbandModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "刚带")
@RestController
public class EcbuSteelbandController {
    @Resource
    EcbuSteelbandModel ecbuSteelbandModel;
    @Resource
    EcuLoginModel ecuLoginModel;


    @Operation(summary = "编辑刚带")
    //deal
    @PostMapping({"/ecableErpPc/ecbuSteelband/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuSteelbandModel.deal(request);
        }
        return map;
    }


    @Operation(summary = "开启刚带")
    //start
    @PostMapping({"/ecableErpPc/ecbuSteelband/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuSteelbandModel.start(request);
        }
        return map;
    }

    @Operation(summary = "刚带列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuSteelband/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuSteelbandModel.getList(request);
        }
        return map;
    }
}
