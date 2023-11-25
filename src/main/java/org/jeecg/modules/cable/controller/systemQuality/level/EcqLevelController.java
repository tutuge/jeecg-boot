package org.jeecg.modules.cable.controller.systemQuality.level;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelBaseBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelDealBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelListBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelSortBo;
import org.jeecg.modules.cable.controller.systemQuality.level.vo.SystemLevelVo;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.model.systemQuality.EcqLevelModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@Tag(name = "电缆质量等级--系统接口", description = "电缆质量等级--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "10090", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecqLevel")
public class EcqLevelController {
    @Resource
    private EcqLevelModel ecqLevelModel;

    @Operation(summary = "获取电缆质量列表")
    @PostMapping({"/getList"})
    public Result<SystemLevelVo> getList(@RequestBody EcqLevelListBo bo) {
        return Result.ok(ecqLevelModel.getList(bo));
    }

    @Operation(summary = "获取编辑质量信息")
    @PostMapping({"/getObject"})
    public Result<EcqLevel> getObject(@Validated @RequestBody EcqLevelBaseBo bo) {
        return Result.ok(ecqLevelModel.getObject(bo));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcqLevelDealBo bo) throws IOException {
        return Result.ok(ecqLevelModel.deal(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcqLevelSortBo> bos) throws IOException {
        ecqLevelModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcqLevelBaseBo bo) throws IOException {
        ecqLevelModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcqLevelBaseBo bo) throws IOException {
        return Result.ok(ecqLevelModel.start(bo));
    }

    @Operation(summary = "设置同型号下默认")
    @PostMapping({"/defaultType"})
    public Result<?> defaultType(@Validated @RequestBody EcqLevelBaseBo bo) {
        ecqLevelModel.defaultType(bo);
        return Result.ok();
    }
}
