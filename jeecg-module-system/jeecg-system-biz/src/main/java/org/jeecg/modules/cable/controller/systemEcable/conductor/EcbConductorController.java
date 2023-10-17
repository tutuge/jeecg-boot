package org.jeecg.modules.cable.controller.systemEcable.conductor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorStartBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.vo.ConductorVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.model.systemEcable.EcbConductorModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "系统导体")
@RestController
public class EcbConductorController {
    @Resource
    EcbConductorModel ecbConductorModel;//系统导体

    @Operation(summary = "编辑获取回显数据")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbConductor/getList"})
    public Result<ConductorVo> getList(@RequestBody EcbConductorBo bo) {
        return Result.ok(ecbConductorModel.getListAndCount(bo));
    }

    @Operation(summary = "编辑获取回显数据")
    //根据EcbConductor获取EcbConductor
    @PostMapping({"/ecableErpPc/ecbConductor/getObject"})
    public Result<EcbConductor> getObject(@RequestBody EcbConductorStartBo bo) {
        return Result.ok(ecbConductorModel.getObject(bo));
    }
}
