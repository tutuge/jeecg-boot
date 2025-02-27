package org.jeecg.modules.cable.controller.user.unit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitBaseBo;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitDealBo;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitPageBo;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitSortBo;
import org.jeecg.modules.cable.controller.user.unit.vo.UnitListVo;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.model.user.EccUnitModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "默认单位管理", description = "默认单位管理",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "959", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/eccUnit")
public class EccUnitController {
    @Resource
    EccUnitModel eccUnitModel;

    @Operation(summary = "获取单位回显数据")
    @PostMapping({"/getObject"})
    public Result<EccUnit> getObject(@Validated @RequestBody EccUnitBaseBo bo) {
        return Result.ok(eccUnitModel.getObject(bo));
    }

    @Operation(summary = "获取默认单位列表")
    @PostMapping({"/getList"})
    public Result<UnitListVo> getList(@Validated @RequestBody EccUnitPageBo bo) {
        return Result.ok(eccUnitModel.getList(bo));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EccUnitDealBo bo) {
        return Result.ok(eccUnitModel.deal(bo));
    }


    @Operation(summary = "启用禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EccUnitBaseBo bo) {
        return Result.ok(eccUnitModel.start(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EccUnitSortBo> boList) {
        eccUnitModel.sort(boList);
        return Result.ok();
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EccUnitBaseBo bo) {
        eccUnitModel.delete(bo);
        return Result.ok();
    }
}
