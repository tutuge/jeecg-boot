package org.jeecg.modules.cable.controller.userQuality.level;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelBaseBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelDealBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelListBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelSortBo;
import org.jeecg.modules.cable.controller.userQuality.level.vo.LevelVo;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.model.userQuality.EcquLevelModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@Tag(name = "电缆质量等级--用户接口", description = "电缆质量等级--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "10091", parseValue = true)})})
@RestController
@Validated
@RequestMapping("/ecableErpPc/ecquLevel")
public class EcquLevelController {
    @Resource
    EcquLevelModel ecquLevelModel;

    @Operation(summary = "获取电缆质量列表")
    @PostMapping({"/getList"})
    public Result<LevelVo> getList(@RequestBody EcquLevelListBo bo) {
        return Result.ok(ecquLevelModel.getList(bo));
    }

    @Operation(summary = "获取编辑质量信息")
    @PostMapping({"/getObject"})
    public Result<EcquLevel> getObject(@Validated @RequestBody EcquLevelBaseBo bo) {
        return Result.ok(ecquLevelModel.getObject(bo));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcquLevelDealBo bo) throws IOException {
        return Result.ok(ecquLevelModel.deal(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcquLevelSortBo> bos) throws IOException {
        ecquLevelModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcquLevelBaseBo bo) throws IOException {
        ecquLevelModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcquLevelBaseBo bo) throws IOException {
        return Result.ok(ecquLevelModel.start(bo));
    }

    @Operation(summary = "设置同型号下默认")
    @PostMapping({"/defaultType"})
    public Result<?> defaultType(@Validated @RequestBody EcquLevelBaseBo bo) {
        ecquLevelModel.defaultType(bo);
        return Result.ok();
    }
}
