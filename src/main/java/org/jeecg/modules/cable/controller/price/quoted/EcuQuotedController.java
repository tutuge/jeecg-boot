package org.jeecg.modules.cable.controller.price.quoted;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.price.quoted.bo.*;
import org.jeecg.modules.cable.controller.price.quoted.vo.QuotedVo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.model.price.EcuQuotedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "历史报价--用户接口", description = "历史报价--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecuQuoted")
public class EcuQuotedController {
    @Resource
    EcuQuotedModel ecuQuotedModel;

    @Operation(summary = "列表查询")
    @PostMapping({"/getList"})
    public Result<QuotedVo> getList(@Validated @RequestBody EcuQuotedListBo bo) {
        return Result.ok(ecuQuotedModel.getListAndCount(bo));
    }

    @Operation(summary = "获取主表信息信息")
    @PostMapping({"/getObject"})
    public Result<EcuQuoted> getObjectPassId(@RequestBody EcuQuotedObjectBo bo) {
        return Result.ok(ecuQuotedModel.getObject(bo));
    }

    @Operation(summary = "获取最新报价单")
    @GetMapping({"/getLatestObject"})
    public Result<EcuQuoted> getObjectLatestPassId() {
        return Result.ok(ecuQuotedModel.getLatestObject());
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcuQuotedBo bo) {
        return Result.ok(ecuQuotedModel.deal(bo));
    }

    // dealMoneyPassInput
    @Operation(summary = "通过手输的方式改变总额")
    @PostMapping({"/dealMoneyPassInput"})
    public Result<?> dealMoneyPassInput(@RequestBody QuotedDealMoneyPassBo bo) {
        ecuQuotedModel.dealMoneyPassInput(bo);
        return Result.ok();
    }

    @Operation(summary = "报价单提交")
    @PostMapping({"/dealComplete"})
    public Result<?> dealComplete(@RequestBody QuotedDealCompleteBo bo) {
        ecuQuotedModel.dealComplete(bo);
        return Result.ok();
    }

    @Operation(summary = "下方备注添加")
    @PostMapping({"/dealTotalDesc"})
    public Result<?> dealTotalDesc(@RequestBody QuotedTotalDealBo bo) {
        ecuQuotedModel.dealTotalDesc(bo);
        return Result.ok();
    }
}
