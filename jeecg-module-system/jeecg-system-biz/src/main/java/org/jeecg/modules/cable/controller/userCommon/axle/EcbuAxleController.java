package org.jeecg.modules.cable.controller.userCommon.axle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleBaseBo;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleBo;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleInsertBo;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleSortBo;
import org.jeecg.modules.cable.controller.userCommon.axle.vo.EcbuAxleVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;
import org.jeecg.modules.cable.model.userCommon.EcbuAxleModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "木轴管理--用户接口", description = "木轴管理--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "540", parseValue = true)})})
@RestController
public class EcbuAxleController {
    @Resource
    EcbuAxleModel ecbuAxleModel;

    @Operation(summary = "获取木轴列表")

    @PostMapping({"/ecableErpPc/ecbuAxle/getList"})
    public Result<EcbuAxleVo> getList(@RequestBody EcbuAxleBo bo) {
        return Result.ok(ecbuAxleModel.getListAndCount(bo));
    }


    @Operation(summary = "获取木轴")

    @PostMapping({"/ecableErpPc/ecbuAxle/getObject"})
    public Result<EcbuAxle> getObjectPassId(@Validated @RequestBody EcbuAxleBaseBo bo) {
        return Result.ok(ecbuAxleModel.getObject(bo));
    }

    @Operation(summary = "提交")

    @PostMapping({"/ecableErpPc/ecbuAxle/deal"})
    public Result<String> deal(@RequestBody EcbuAxleInsertBo bo) {
        return Result.ok(ecbuAxleModel.deal(bo));
    }

    @Operation(summary = "排序")

    @PostMapping({"/ecableErpPc/ecbuAxle/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbuAxleSortBo> bos) {
        ecbuAxleModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")

    @PostMapping({"/ecableErpPc/ecbuAxle/delete"})
    public Result<?> delete(@Validated @RequestBody EcbuAxleBaseBo bo) {
        ecbuAxleModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")

    @PostMapping({"/ecableErpPc/ecbuAxle/start"})
    public Result<String> start(@Validated @RequestBody EcbuAxleBaseBo bo) {
        return Result.ok(ecbuAxleModel.start(bo));
    }
}
