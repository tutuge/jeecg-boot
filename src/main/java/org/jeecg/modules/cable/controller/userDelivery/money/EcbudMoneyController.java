package org.jeecg.modules.cable.controller.userDelivery.money;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.*;
import org.jeecg.modules.cable.controller.userDelivery.money.vo.MoneyVo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.model.userDelivery.EcbudMoneyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "快递价格--用户接口", description = "快递价格--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "125", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbudMoney")
public class EcbudMoneyController {
    @Resource
    private EcbudMoneyModel ecbudMoneyModel;


    @Operation(summary = "快递价格信息列表")
    @PostMapping({"/getList"})
    public Result<MoneyVo> getList(@RequestBody EcbuMoneyBo bo) {
        return Result.ok(ecbudMoneyModel.getListAndCount(bo));
    }


    @Operation(summary = "快递价格信息详情")
    @PostMapping({"/getObject"})
    public Result<EcbudMoney> getObject(@Validated @RequestBody EcbuMoneyBaseBo bo) {
        return Result.ok(ecbudMoneyModel.getObject(bo));
    }


    @Operation(summary = "快递价格信息编辑")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbuMoneyInsertBo bo) {
        return Result.ok(ecbudMoneyModel.deal(bo));
    }

    @Operation(summary = "快递首重信息批量编辑")
    @PostMapping({"/weight"})
    public Result<String> weight(@Validated @RequestBody List<EcbuMoneyWeightBo> bos) {
        return Result.ok(ecbudMoneyModel.weight(bos));
    }

    @Operation(summary = "快递价格信息排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbuMoneySortBo> bos) {
        ecbudMoneyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "快递价格信息删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbuMoneyBaseBo bo) {
        ecbudMoneyModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "快递价格信息开启禁用")
    @PostMapping({"/start"})
    public Result<?> start(@RequestBody EcbuMoneyBaseBo bo) {
        return Result.ok(ecbudMoneyModel.start(bo));
    }
}
