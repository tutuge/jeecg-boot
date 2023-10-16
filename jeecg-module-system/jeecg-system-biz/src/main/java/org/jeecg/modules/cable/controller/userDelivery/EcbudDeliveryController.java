package org.jeecg.modules.cable.controller.userDelivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudDeliveryModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "快递")
@RestController
public class EcbudDeliveryController {
    @Resource
    EcbudDeliveryModel ecbudDeliveryModel;
    @Resource
    EcuLoginModel ecuLoginModel;


    @Operation(summary = "获取公司或者个人默认快递")
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

    @Operation(summary = "默认快递提交")
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
