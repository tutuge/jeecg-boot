package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.systemEcable.EcbBagModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "包带")
@RestController
public class EcbBagController {
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息
    @Resource
    EcbBagModel ecbBagModel;

    @Operation(summary = "获取包带列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbBag/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbBagModel.getListAndCount(request);
        }
        return map;
    }

    @Operation(summary = "编辑回显信息")
    //根据EcbBag获取EcbBag
    @PostMapping({"/ecableErpPc/ecbBag/getObject"})
    public Map<String, Object> getObjectPassId(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbBagModel.getObject(request);
        }
        return map;
    }
}
