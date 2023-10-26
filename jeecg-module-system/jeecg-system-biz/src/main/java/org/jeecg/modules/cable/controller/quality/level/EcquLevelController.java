package org.jeecg.modules.cable.controller.quality.level;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.quality.level.bo.EcquLevelBaseBo;
import org.jeecg.modules.cable.controller.quality.level.bo.EcquLevelDealBo;
import org.jeecg.modules.cable.controller.quality.level.bo.EcquLevelListBo;
import org.jeecg.modules.cable.controller.quality.level.bo.EcquLevelSortBo;
import org.jeecg.modules.cable.controller.quality.level.vo.LevelVo;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.model.quality.EcquLevelModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@Tag(name = "电缆等级--用户接口", description = "电缆等级--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "10091", parseValue = true)})})
@RestController
@Validated
public class EcquLevelController {
    @Resource
    EcquLevelModel ecquLevelModel;

    @Operation(summary = "获取电缆质量列表")
    // getList
    @PostMapping({"/ecableErpPc/ecquLevel/getList"})
    public Result<LevelVo> getList(@RequestBody EcquLevelListBo bo) {
        return Result.ok(ecquLevelModel.getList(bo));
    }

    @Operation(summary = "获取编辑质量信息")
    // getObject
    @PostMapping({"/ecableErpPc/ecquLevel/getObject"})
    public Result<EcquLevel> getObject(@Validated @RequestBody EcquLevelBaseBo bo) {
        return Result.ok(ecquLevelModel.getObject(bo));
    }

    @Operation(summary = "编辑提交")
    // deal
    @PostMapping({"/ecableErpPc/ecquLevel/deal"})
    public Result<String> deal(@RequestBody EcquLevelDealBo bo) throws IOException {
        return Result.ok(ecquLevelModel.deal(bo));
    }

    @Operation(summary = "排序")
    // sort
    @PostMapping({"/ecableErpPc/ecquLevel/sort"})
    public Result<?> sort(@RequestBody List<EcquLevelSortBo> bos) throws IOException {
        ecquLevelModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")
    // delete
    @PostMapping({"/ecableErpPc/ecquLevel/delete"})
    public Result<?> delete(@RequestBody EcquLevelBaseBo bo) throws IOException {
        ecquLevelModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    // start
    @PostMapping({"/ecableErpPc/ecquLevel/start"})
    public Result<String> start(@RequestBody EcquLevelBaseBo bo) throws IOException {
        return Result.ok(ecquLevelModel.start(bo));
    }

    @Operation(summary = "是否默认")
    // defaultTYpe
    @PostMapping({"/ecableErpPc/ecquLevel/defaultType"})
    public Result<?> defaultType(@RequestBody EcquLevelBaseBo bo) {
        ecquLevelModel.defaultType(bo);
        return Result.ok();
    }
}
