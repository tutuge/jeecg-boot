package org.jeecg.modules.cable.controller.price.desc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.price.desc.bo.*;
import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "结构信息--用户接口",
        description = "结构信息--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "42", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecuqDesc")
public class EcuqDescController {
    @Resource
    EcuqDescModel ecuqDescModel;

    @Operation(summary = "编辑提交")
    @PostMapping({"/dealStructure"})
    public Result<?> dealStructure(@RequestBody DescDealBo bo) {
        ecuqDescModel.dealStructure(bo);
        return Result.ok();
    }

    @Operation(summary = "手动修改报价单明细行的金额")
    @PostMapping({"/dealMoney"})
    public Result<?> dealMoney(@Validated @RequestBody DescDealMoneyBo bo) {
        ecuqDescModel.dealMoney(bo);
        return Result.ok();
    }

    @Operation(summary = "更改为手输或自动 false 是自动 true 是手输")
    @PostMapping({"/dealInputStart"})
    public Result<?> dealInputStart(@Validated @RequestBody DescStartBo bo) {
        ecuqDescModel.dealInputStart(bo);
        return Result.ok();
    }

    @Operation(summary = "修改为手动更改税前单价")
    @PostMapping({"/dealUnitPrice"})
    public Result<?> dealUnitPrice(@Validated @RequestBody DescDealUnitPriceBo bo) {
        ecuqDescModel.dealUnitPrice(bo);
        return Result.ok();
    }

    @Operation(summary = "修改木轴")
    @PostMapping({"/dealAxle"})
    public Result<?> dealAxle(@Validated @RequestBody DescDealAxleBo bo) {
        ecuqDescModel.dealAxle(bo);
        return Result.ok();
    }

    @Operation(summary = "将税前单价由手动改为自动")
    @PostMapping({"/dealUnitPriceInput"})
    public Result<?> dealUnitPriceInput(@RequestBody DescBo bo) {
        ecuqDescModel.dealUnitPriceInput(bo);
        return Result.ok();
    }
}
