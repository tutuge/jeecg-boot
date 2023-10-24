package org.jeecg.modules.cable.controller.price.quoted;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.price.quoted.bo.*;
import org.jeecg.modules.cable.controller.price.quoted.vo.QuotedVo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.model.price.EcuQuotedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiSupport(order = 161)
@Tag(name = "报价单",description = "报价单")
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
    public Result<EcuQuoted> getObjectPassId(@RequestBody EcuQuotedObjectBo bo) {
        return Result.ok(ecuQuotedModel.getObject(bo));
    }

    @Operation(summary = "获取最新报价单")
    //getLatestObject
    @GetMapping({"/ecableErpPc/ecuQuoted/getLatestObject"})
    public Result<EcuQuoted> getObjectLatestPassId() {
        return Result.ok(ecuQuotedModel.getLatestObject());
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecuQuoted/deal"})
    public Result<String> deal(@RequestBody EcuQuotedBo bo) {
        return Result.ok(ecuQuotedModel.deal(bo));
    }

    //dealMoneyPassInput
    @Operation(summary = "通过手输的方式改变总额")
    @PostMapping({"/ecableErpPc/ecuQuoted/dealMoneyPassInput"})
    public Result<?> dealMoneyPassInput(@RequestBody QuotedDealMoneyPassBo bo) {
        ecuQuotedModel.dealMoneyPassInput(bo);
        return Result.ok();
    }

    @Operation(summary = "报价单提交")
    //dealComplete 提交已成交
    @PostMapping({"/ecableErpPc/ecuQuoted/dealComplete"})
    public Result<?> dealComplete(@RequestBody QuotedDealCompleteBo bo) {
        ecuQuotedModel.dealComplete(bo);
        return Result.ok();
    }

    @Operation(summary = "下方备注添加")
    //dealQuoted 提交
    @PostMapping({"/ecableErpPc/ecuQuoted/dealTotalDesc"})
    public Result<?> dealTotalDesc(@RequestBody QuotedTotalDealBo bo) {
        ecuQuotedModel.dealTotalDesc(bo);
        return Result.ok();
    }
}
