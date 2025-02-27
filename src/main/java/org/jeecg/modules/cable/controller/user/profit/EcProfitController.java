package org.jeecg.modules.cable.controller.user.profit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitListBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitSortBo;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitListVo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcProfitEditBo;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.model.user.EcProfitModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "利润管理", description = "利润管理",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "979", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecProfit")
public class EcProfitController {
    @Resource
    EcProfitModel ecProfitModel;

    @Operation(summary = "根据主键ID获取利润")
    @PostMapping({"/getObject"})
    public Result<EcProfit> getObject(@Validated @RequestBody ProfitBo bo) {
        return Result.ok(ecProfitModel.getObject(bo));
    }

    @Operation(summary = "获取利润列表")
    @PostMapping({"/getList"})
    public Result<ProfitListVo> getList(@RequestBody ProfitListBo bo) {
        return Result.ok(ecProfitModel.getList(bo));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcProfitEditBo bo) {
        return Result.ok(ecProfitModel.deal(bo));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody ProfitBo bo) {
        return Result.ok(ecProfitModel.start(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<ProfitSortBo> bos) {
        ecProfitModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<String> delete(@RequestBody ProfitBo bo) {
        ecProfitModel.delete(bo);
        return Result.ok();
    }
}
