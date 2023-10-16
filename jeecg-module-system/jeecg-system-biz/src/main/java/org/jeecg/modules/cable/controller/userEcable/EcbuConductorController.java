package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.bo.EcbuConductorBo;
import org.jeecg.modules.cable.controller.userEcable.bo.EcbuConductorListBo;
import org.jeecg.modules.cable.controller.userEcable.bo.EcbuConductorStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuConductorModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Result<?> start(@RequestBody EcbuConductorStartBo bo) {
//        Map<String, Object> map;
//        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
//        String token = request.getHeader("token");
//        map = ecuLoginModel.isExistsToken(request, ecuId, token);
//        if ("3".equals(map.get("status").toString())) {
        String start = ecbuConductorModel.start(bo);
//        }
        return Result.OK(start);
    }

    @Operation(summary = "导体数据列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuConductor/getList"})
    public Result<List<EcbuConductor>> getList(@RequestBody EcbuConductorListBo bo) {
//        Map<String, Object> map;
//        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
//        String token = request.getHeader("token");
//        map = ecuLoginModel.isExistsToken(request, ecuId, token);
//        if ("3".equals(map.get("status").toString())) {
        List<EcbuConductor> list = ecbuConductorModel.getList(bo);
//        }
        return Result.OK(list);
    }
}
