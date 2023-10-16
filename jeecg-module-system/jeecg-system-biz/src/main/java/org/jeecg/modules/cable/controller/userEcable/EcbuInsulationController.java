package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuInsulationModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "绝缘")
@RestController
public class EcbuInsulationController {
    @Resource
    EcbuInsulationModel ecbuInsulationModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "提交编辑绝缘")
    //deal
    @PostMapping({"/ecableErpPc/ecbuInsulation/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuInsulationModel.deal(request);
        }
        return map;
    }


    @Operation(summary = "开启禁用绝缘")
    //start
    @PostMapping({"/ecableErpPc/ecbuInsulation/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuInsulationModel.start(request);
        }
        return map;
    }


    @Operation(summary = "绝缘列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuInsulation/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuInsulationModel.getList(request);
        }
        return map;
    }
}
