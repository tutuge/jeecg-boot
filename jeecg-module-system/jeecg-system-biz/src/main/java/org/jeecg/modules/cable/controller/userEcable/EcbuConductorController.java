package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.bo.EcbuConductorBo;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuConductorModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "导体")
@RestController
public class EcbuConductorController {
    @Resource
    EcbuConductorModel ecbuConductorModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "修改导体数据")
    //deal
    @PostMapping({"/ecableErpPc/ecbuConductor/deal"})
    public Result<?> deal(@RequestBody EcbuConductorBo ecbuConductorBo) {
//        Map<String, Object> map;
//        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
//        String token = request.getHeader("token");
//        map = ecuLoginModel.isExistsToken(request, ecuId, token);
//        if ("3".equals(map.get("status").toString())) {
            ecbuConductorModel.deal(ecbuConductorBo);

        return Result.OK();
    }


    @Operation(summary = "禁用启用导体数据")
    //start
    @PostMapping({"/ecableErpPc/ecbuConductor/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuConductorModel.start(request);
        }
        return map;
    }

    @Operation(summary = "编辑导体数据")
    //getList
    @PostMapping({"/ecableErpPc/ecbuConductor/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuConductorModel.getList(request);
        }
        return map;
    }
}
