package org.jeecg.modules.cable.controller.user.udesc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescDealBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescPageBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescSortBo;
import org.jeecg.modules.cable.controller.user.udesc.vo.EcuDescVo;
import org.jeecg.modules.cable.controller.user.udesc.vo.UDescListVo;
import org.jeecg.modules.cable.model.user.EcuDescModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "备注管理--用户接口", description = "备注管理--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "969", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecuDesc")
public class EcuDescController {
    @Resource
    EcuDescModel ecuDescModel;

    @Operation(summary = "获取回显数据")
    @PostMapping({"/getObject"})
    public Result<EcuDescVo> getObject(@RequestBody EcuDescBo bo) {
        return Result.ok(ecuDescModel.getObject(bo));
    }

    @Operation(summary = "获取备注说明列表")
    @PostMapping({"/getList"})
    public Result<UDescListVo> getList(@RequestBody EcuDescPageBo bo) {
        return Result.ok(ecuDescModel.getList(bo));
    }

    @Operation(summary = "新增/修改")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcuDescDealBo bo) {
        return Result.ok(ecuDescModel.deal(bo));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcuDescBo bo) {
        return Result.ok(ecuDescModel.start(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcuDescSortBo> bos) {
        ecuDescModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcuDescBo bo) {
        ecuDescModel.delete(bo);
        return Result.ok();
    }

    @Operation(summary = "设置默认")
    // 设置默认
    @PostMapping({"/defaultType"})
    public Result<?> defaultType(@RequestBody EcuDescBo bo) {
        ecuDescModel.defaultType(bo);
        return Result.ok();
    }
}
