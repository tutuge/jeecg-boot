package org.jeecg.modules.cable.controller.userCommon.unit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitBaseBo;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitBo;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitInsertBo;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitSortBo;
import org.jeecg.modules.cable.controller.userCommon.unit.vo.LengthUnitVo;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.model.userCommon.EcbulUnitModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "单位管理--用户接口", description = "单位管理--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "530", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbulUnit")
public class EcbulUnitController {
    @Resource
    EcbulUnitModel ecbulUnitModel;

    @Operation(summary = "获取单位长度列表")
    @PostMapping({"/getList"})
    public Result<LengthUnitVo> getList(@RequestBody EcbuUnitBo bo) {
        return Result.ok(ecbulUnitModel.getListAndCount(bo));
    }

    @Operation(summary = "获取单位长度")
    @PostMapping({"/getObject"})
    public Result<EcbulUnit> getObject(@RequestBody EcbuUnitBo bo) {
        return Result.ok(ecbulUnitModel.getObject(bo));
    }


    @Operation(summary = "编辑单位长度")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbuUnitInsertBo bo) {
        return Result.ok(ecbulUnitModel.deal(bo));
    }


    @Operation(summary = "单位长度排序")
    @PostMapping({"/sort"})
    public Result<String> sort(@Validated @RequestBody List<EcbuUnitSortBo> bos) {
        ecbulUnitModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<String> delete(@RequestBody EcbuUnitBo bo) {
        ecbulUnitModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启或禁用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbuUnitBo bo) {
        return Result.ok(ecbulUnitModel.start(bo));
    }


    @Operation(summary = "设置默认单位")
    @PostMapping({"/dealDefault"})
    public Result<?> dealDefault(@Validated @RequestBody EcbuUnitBaseBo bo) {
        ecbulUnitModel.dealDefault(bo);
        return Result.ok();
    }
}
