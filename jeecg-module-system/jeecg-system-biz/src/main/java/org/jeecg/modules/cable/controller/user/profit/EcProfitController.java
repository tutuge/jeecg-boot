package org.jeecg.modules.cable.controller.user.profit;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitVo;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.model.user.EcProfitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiSort(600)
@Tag(name = "利润管理")
@RestController
public class EcProfitController {
    @Resource
    EcProfitModel ecProfitModel;

    @Operation(summary = "获取利润")
    @PostMapping({"/ecableErpPc/ecProfit/getObject"})
    public Result<EcProfit> getObject(HttpServletRequest request) {
        return Result.ok(ecProfitModel.getObject(request));
    }

    @Operation(summary = "获取利润列表")
    @PostMapping({"/ecableErpPc/ecProfit/getList"})
    public Result<ProfitVo> getList(HttpServletRequest request) {
        return Result.ok(ecProfitModel.getList(request));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecProfit/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecProfitModel.deal(request));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecProfit/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecProfitModel.start(request));
    }

    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecProfit/sort"})
    public Result<?> sort(HttpServletRequest request) {
        ecProfitModel.sort(request);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecProfit/delete"})
    public Result<String> delete(HttpServletRequest request) {
        ecProfitModel.delete(request);
        return Result.ok();
    }
}
