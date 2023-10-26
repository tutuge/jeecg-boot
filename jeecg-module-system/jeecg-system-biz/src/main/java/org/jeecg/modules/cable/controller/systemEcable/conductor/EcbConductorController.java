package org.jeecg.modules.cable.controller.systemEcable.conductor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorDealBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorListBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorSortBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.vo.ConductorVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.model.systemEcable.EcbConductorModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "导体--系统接口", description = "导体--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "411", parseValue = true)})})
@RestController
public class EcbConductorController {
    @Resource
    EcbConductorModel ecbConductorModel;

    @Operation(summary = "获取列表")
    @PostMapping({"/ecableAdminPc/ecbConductor/getList"})
    public Result<ConductorVo> getList(@RequestBody EcbConductorListBo bo) {
        return Result.ok(ecbConductorModel.getList(bo));
    }

    @Operation(summary = "获取对象")
    @PostMapping({"/ecableAdminPc/ecbConductor/getObject"})
    public Result<EcbConductor> getObject(@RequestBody EcbConductorBaseBo bo) {
        return Result.ok(ecbConductorModel.getObject(bo));
    }

    @Operation(summary = "新增或修改")
    @PostMapping({"/ecableAdminPc/ecbConductor/deal"})
    public Result<String> deal(@RequestBody EcbConductorDealBo bo) {
        return Result.ok(ecbConductorModel.deal(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/ecableAdminPc/ecbConductor/sort"})
    public Result<?> sort(@RequestBody List<EcbConductorSortBo> bos) {
        ecbConductorModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用停用")
    @PostMapping({"/ecableAdminPc/ecbConductor/start"})
    public Result<String> start(@RequestBody EcbConductorBaseBo bo) {
        return Result.ok(ecbConductorModel.start(bo));
    }

    @Operation(summary = "删除")
    @PostMapping({"/ecableAdminPc/ecbConductor/delete"})
    public Result<?> delete(@RequestBody EcbConductorBaseBo bo) {
        ecbConductorModel.delete(bo);
        return Result.ok();
    }
}
