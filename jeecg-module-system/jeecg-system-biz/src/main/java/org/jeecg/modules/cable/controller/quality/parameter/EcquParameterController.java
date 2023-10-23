package org.jeecg.modules.cable.controller.quality.parameter;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.quality.parameter.vo.ParameterVo;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.model.quality.EcquParameterModel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiSort(710)
@Tag(name = "获取电缆质量")
@RestController
public class EcquParameterController {
    @Resource
    EcquParameterModel ecquParameterModel;

    @Operation(summary = "获取电缆质量等级参数列表")
    //getList
    @PostMapping({"/ecableErpPc/ecquParameter/getList"})
    public Result<ParameterVo> getList(HttpServletRequest request) {
        return Result.ok(ecquParameterModel.getListAndCount(request));
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecquParameter/getObject"})
    public Result<EcquParameter> getObjectPassId(HttpServletRequest request) {
        return Result.ok(ecquParameterModel.getObject(request));
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecquParameter/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecquParameterModel.deal(request));
    }
}
