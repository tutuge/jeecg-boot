package org.jeecg.modules.cable.controller.efficiency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "获取省份对应信息")
@RestController
public class EcduPccController {
    @Resource
    EcduPccModel ecduPccModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取省份对应信息")
    //getObject
    @PostMapping({"/ecableErpPc/ecduPcc/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduPccModel.getObject(request);
        }
        return map;
    }
}
