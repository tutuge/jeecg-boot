package org.jeecg.modules.cable.controller.userEcable.conductor;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorStartBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.vo.ConductorVo;
import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorBo;
import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorListBo;
import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.model.systemEcable.EcbConductorModel;
import org.jeecg.modules.cable.model.userEcable.EcbuConductorModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@ApiSupport(order =410)
@Tag(name = "导体--用户接口",description = "导体--用户接口")
@RestController
public class EcbuConductorController {
    @Resource
    EcbuConductorModel ecbuConductorModel;

    @Operation(summary = "修改导体数据")
    //deal
    @PostMapping({"/ecableErpPc/ecbuConductor/deal"})
    public Result<?> deal(@RequestBody EcbuConductorBo ecbuConductorBo) {
        ecbuConductorModel.deal(ecbuConductorBo);
        return Result.OK();
    }


    @Operation(summary = "禁用启用导体数据")
    //start
    @PostMapping({"/ecableErpPc/ecbuConductor/start"})
    public Result<?> start(@RequestBody EcbuConductorStartBo bo) {
        String start = ecbuConductorModel.start(bo);
        return Result.OK(start);
    }

    @Operation(summary = "导体数据列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuConductor/getList"})
    public Result<List<EcbuConductor>> getList(@RequestBody EcbuConductorListBo bo) {
        List<EcbuConductor> list = ecbuConductorModel.getList(bo);
        return Result.OK(list);
    }

    @Operation(summary = "编辑获取回显数据")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbConductor/getList"})
    public Result<ConductorVo> getList(@RequestBody EcbConductorBo bo) {
        return Result.ok(ecbuConductorModel.getListAndCount(bo));
    }

    @Operation(summary = "编辑获取回显数据")
    //根据EcbConductor获取EcbConductor
    @PostMapping({"/ecableErpPc/ecbConductor/getObject"})
    public Result<EcbConductor> getObject(@RequestBody EcbConductorStartBo bo) {
        return Result.ok(ecbuConductorModel.getObject(bo));
    }
}
