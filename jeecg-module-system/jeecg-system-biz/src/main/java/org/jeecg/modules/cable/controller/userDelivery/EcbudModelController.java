package org.jeecg.modules.cable.controller.userDelivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudModelModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "表头")
@RestController
public class EcbudModelController {
    @Resource
    EcbudModelModel ecbudModelModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "表头编辑提交")
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


    @Operation(summary = "表头详情")
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
