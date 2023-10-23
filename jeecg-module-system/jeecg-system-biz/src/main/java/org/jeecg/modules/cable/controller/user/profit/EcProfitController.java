package org.jeecg.modules.cable.controller.user.profit;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitListBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitSortBo;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitVo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcProfitEditBo;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.model.user.EcProfitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiSort(600)
@Tag(name = "利润管理")
@RestController
public class EcProfitController {
    @Resource
    EcProfitModel ecProfitModel;

    @Operation(summary = "根据主键ID获取利润")
    @PostMapping({"/ecableErpPc/ecProfit/getObject"})
    public Result<EcProfit> getObject(@RequestBody ProfitBo bo) {
        return Result.ok(ecProfitModel.getObject(bo));
    }

    @Operation(summary = "获取利润列表")
    @PostMapping({"/ecableErpPc/ecProfit/getList"})
    public Result<ProfitVo> getList(@RequestBody ProfitListBo bo) {
        return Result.ok(ecProfitModel.getList(bo));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecProfit/deal"})
    public Result<String> deal(@RequestBody EcProfitEditBo bo) {
        return Result.ok(ecProfitModel.deal(bo));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecProfit/start"})
    public Result<String> start(@RequestBody ProfitBo bo) {
        return Result.ok(ecProfitModel.start(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecProfit/sort"})
    public Result<?> sort(@RequestBody ProfitSortBo bo) {
        ecProfitModel.sort(bo);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecProfit/delete"})
    public Result<String> delete(@RequestBody ProfitBo bo) {
        ecProfitModel.delete(bo);
        return Result.ok();
    }
}
