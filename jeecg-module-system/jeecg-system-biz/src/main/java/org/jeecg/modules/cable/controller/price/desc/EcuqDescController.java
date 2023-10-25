package org.jeecg.modules.cable.controller.price.desc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.price.desc.bo.DescBo;
import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "结构信息",description = "结构信息",extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "42", parseValue = true)})})
@RestController
public class EcuqDescController {
    @Resource
    EcuqDescModel ecuqDescModel;

    @Operation(summary = "编辑提交")
    //dealStructure
    @PostMapping({"/ecableErpPc/ecuqDesc/dealStructure"})
    public Result<?> dealStructure(HttpServletRequest request) {
        ecuqDescModel.dealStructure(request);
        return Result.ok();
    }

    @Operation(summary = "修改金额")
    //dealMoney
    @PostMapping({"/ecableErpPc/ecuqDesc/dealMoney"})
    public Result<?> dealMoney(HttpServletRequest request) {
        ecuqDescModel.dealMoney(request);
        return Result.ok();
    }

    @Operation(summary = "更改为手输或自动")
    //dealInputStart 更改为手输或是自动计算价格 false 是自动 true 是手输
    @PostMapping({"/ecableErpPc/ecuqDesc/dealInputStart"})
    public Result<?> dealInputStart(HttpServletRequest request) {

        ecuqDescModel.dealInputStart(request);
        return Result.ok();
    }

    @Operation(summary = "修改为手动更改税前单价")
    //dealUnitPrice 修改为手动更改税前单价
    @PostMapping({"/ecableErpPc/ecuqDesc/dealUnitPrice"})
    public Result<?> dealUnitPrice(HttpServletRequest request) {
        ecuqDescModel.dealUnitPrice(request);
        return Result.ok();
    }

    @Operation(summary = "修改木轴")
    //dealAxle 修改木轴
    @PostMapping({"/ecableErpPc/ecuqDesc/dealAxle"})
    public Result<?> dealAxle(HttpServletRequest request) {
        ecuqDescModel.dealAxle(request);
        return Result.ok();
    }

    @Operation(summary = "将税前单价由手动改为自动")
    //dealUnitPriceInput 将税前单价由手动改为自动
    @PostMapping({"/ecableErpPc/ecuqDesc/dealUnitPriceInput"})
    public Result<?> dealUnitPriceInput(@RequestBody DescBo bo) {
        ecuqDescModel.dealUnitPriceInput(bo);
        return Result.ok();
    }
}
