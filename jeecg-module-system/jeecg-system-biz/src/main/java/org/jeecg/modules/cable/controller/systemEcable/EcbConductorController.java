package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.systemEcable.EcbConductorModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "系统导体")
@RestController
public class EcbConductorController {
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息
    @Resource
    EcbConductorModel ecbConductorModel;//系统导体

    @Operation(summary = "编辑获取回显数据")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbConductor/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbConductorModel.getListAndCount(request);
        }
        return map;
    }

    @Operation(summary = "编辑获取回显数据")
    //根据EcbConductor获取EcbConductor
    @PostMapping({"/ecableErpPc/ecbConductor/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbConductorModel.getObject(request);

        }
        return map;
    }
}
