package org.jeecg.modules.cable.controller.systemCommon.unit;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitDealBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitListBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitSortBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.vo.EcblUnitListVo;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.model.systemCommon.EcblUnitModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "单位管理--系统接口", description = "单位管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "530", parseValue = true)})})
@RequestMapping("/ecableAdminPc/ecblUnit")
public class EcblUnitController {
    @Resource
    EcblUnitModel ecblUnitModel;


    @Operation(summary = "新增或编辑")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcblUnitDealBo bo) {
        return Result.ok(ecblUnitModel.deal(bo));
    }


    @Operation(summary = "获取数据列表")
    @PostMapping({"/getList"})
    public Result<EcblUnitListVo> getList(@RequestBody EcblUnitListBo bo) {
        return Result.ok(ecblUnitModel.getList(bo));
    }


    @Operation(summary = "获取对象")
    @PostMapping({"/getObject"})
    public Result<EcblUnit> getObject(@Validated @RequestBody EcblUnitBaseBo bo) {
        return Result.ok(ecblUnitModel.getObject(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcblUnitSortBo> bos) {
        ecblUnitModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "启用、禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcblUnitBaseBo bo) {
        return Result.ok(ecblUnitModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcblUnitBaseBo bo) {
        ecblUnitModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "设置默认单位")
    @PostMapping({"/dealDefault"})
    public Result<?> dealDefault(@Validated @RequestBody EcblUnitBaseBo bo) {
        ecblUnitModel.dealDefault(bo);
        return Result.ok();
    }
}
