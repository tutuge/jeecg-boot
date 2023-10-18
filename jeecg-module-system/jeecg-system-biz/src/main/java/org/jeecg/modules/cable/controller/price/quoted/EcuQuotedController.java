package org.jeecg.modules.cable.controller.price.quoted;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.price.quoted.vo.QuotedVo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.model.price.EcuQuotedModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "报价单")
@RestController
public class EcuQuotedController {
    @Resource
    EcuQuotedModel ecuQuotedModel;

    @Operation(summary = "根据参数进行筛选")
    //getList
    @PostMapping({"/ecableErpPc/ecuQuoted/getList"})
    public Result<QuotedVo> getList(HttpServletRequest request) {
        return Result.ok(ecuQuotedModel.getListAndCount(request));
    }

    @Operation(summary = "获取主表信息信息")
    //getObject
    @PostMapping({"/ecableErpPc/ecuQuoted/getObject"})
    public Result<EcuQuoted> getObjectPassId(HttpServletRequest request) {
        return Result.ok(ecuQuotedModel.getObject(request));
    }

    @Operation(summary = "获取最新报价单")
    //getLatestObject
    @PostMapping({"/ecableErpPc/ecuQuoted/getLatestObject"})
    public Result<EcuQuoted> getObjectLatestPassId(HttpServletRequest request) {
        return Result.ok(ecuQuotedModel.getLatestObject(request));
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecuQuoted/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecuQuotedModel.deal(request));
    }

    //dealMoneyPassInput
    @PostMapping({"/ecableErpPc/ecuQuoted/dealMoneyPassInput"})
    public Result<?> dealMoneyPassInput(HttpServletRequest request) {
        ecuQuotedModel.dealMoneyPassInput(request);
        return Result.ok();
    }

    @Operation(summary = "报价单提交")
    //dealComplete 提交已成交
    @PostMapping({"/ecableErpPc/ecuQuoted/dealComplete"})
    public Result<?> dealComplete(HttpServletRequest request) {
        ecuQuotedModel.dealComplete(request);
        return Result.ok();
    }

    @Operation(summary = "下方备注添加")
    //dealQuoted 提交
    @PostMapping({"/ecableErpPc/ecuQuoted/dealTotalDesc"})
    public Result<?> dealTotalDesc(HttpServletRequest request) {
        ecuQuotedModel.dealTotalDesc(request);
        return Result.ok();
    }
}
