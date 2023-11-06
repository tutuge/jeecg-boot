package org.jeecg.modules.cable.controller.quality.parameter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.quality.parameter.bo.ParameterBaseBo;
import org.jeecg.modules.cable.controller.quality.parameter.bo.ParameterBo;
import org.jeecg.modules.cable.controller.quality.parameter.bo.ParameterDealBo;
import org.jeecg.modules.cable.controller.quality.parameter.vo.ParameterVo;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.model.quality.EcquParameterModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "获取电缆质量参数--用户接口", description = "获取电缆质量--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "710", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecquParameter")
public class EcquParameterController {
    @Resource
    EcquParameterModel ecquParameterModel;

    @Operation(summary = "获取电缆质量等级参数列表")
    @PostMapping({"/getList"})
    public Result<ParameterVo> getList(@RequestBody ParameterBo bo) {
        return Result.ok(ecquParameterModel.getListAndCount(bo));
    }


    @PostMapping({"/getObject"})
    public Result<EcquParameter> getObject(@Validated @RequestBody ParameterBaseBo bo) {
        return Result.ok(ecquParameterModel.getObject(bo));
    }

    @Operation(summary = "编辑提交")

    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody ParameterDealBo bo) {
        return Result.ok(ecquParameterModel.deal(bo));
    }
}
