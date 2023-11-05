package org.jeecg.modules.cable.controller.systemCommon.axle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleInsertBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleSortBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.vo.AxleVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbAxle;
import org.jeecg.modules.cable.model.systemCommon.EcbAxleModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "木轴管理--系统接口", description = "木轴管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "541", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbuAxle")
public class EcbAxleController {
    @Resource
    EcbAxleModel ecbAxleModel;

    @Operation(summary = "获取木轴列表")
    @PostMapping({"/getList"})
    public Result<AxleVo> getList(@RequestBody EcbAxleBo bo) {
        return Result.ok(ecbAxleModel.getListAndCount(bo));
    }


    @Operation(summary = "获取木轴")
    @PostMapping({"/getObject"})
    public Result<EcbAxle> getObjectPassId(@Validated @RequestBody EcbAxleBaseBo bo) {
        return Result.ok(ecbAxleModel.getObject(bo));
    }

    @Operation(summary = "提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbAxleInsertBo bo) {
        return Result.ok(ecbAxleModel.deal(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbAxleSortBo> bos) {
        ecbAxleModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbAxleBaseBo bo) {
        ecbAxleModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbAxleBaseBo bo) {
        return Result.ok(ecbAxleModel.start(bo));
    }
}
