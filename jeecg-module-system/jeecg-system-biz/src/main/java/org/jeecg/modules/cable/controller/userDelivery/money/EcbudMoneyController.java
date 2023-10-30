package org.jeecg.modules.cable.controller.userDelivery.money;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyBaseBo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyBo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneySortBo;
import org.jeecg.modules.cable.controller.userDelivery.money.vo.MoneyVo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.model.userDelivery.EcbudMoneyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "快递价格--用户接口", description = "快递价格--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2050", parseValue = true)})})
@RestController
public class EcbudMoneyController {
    @Resource
    EcbudMoneyModel ecbudMoneyModel;


    @Operation(summary = "快递价格信息列表")
    // getList
    @PostMapping({"/ecableErpPc/ecbudMoney/getList"})
    public Result<MoneyVo> getList(@RequestBody EcbuMoneyBo bo) {
        return Result.ok(ecbudMoneyModel.getListAndCount(bo));
    }


    @Operation(summary = "快递价格信息详情")
    // getObject
    @PostMapping({"/ecableErpPc/ecbudMoney/getObject"})
    public Result<EcbudMoney> getObject(@Validated @RequestBody EcbuMoneyBaseBo bo) {
        return Result.ok(ecbudMoneyModel.getObject(bo));
    }


    @Operation(summary = "快递价格信息编辑")
    // deal
    @PostMapping({"/ecableErpPc/ecbudMoney/deal"})
    public Result<String> deal(@RequestBody EcbuMoneyInsertBo bo) {
        return Result.ok(ecbudMoneyModel.deal(bo));
    }


    @Operation(summary = "快递价格信息排序")
    // sort
    @PostMapping({"/ecableErpPc/ecbudMoney/sort"})
    public Result<?> sort(@RequestBody List<EcbuMoneySortBo> bos) {
        ecbudMoneyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "快递价格信息删除")
    // delete
    @PostMapping({"/ecableErpPc/ecbudMoney/delete"})
    public Result<?> delete(@Validated @RequestBody EcbuMoneyBaseBo bo) {
        ecbudMoneyModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "快递价格信息开启禁用")
    // start
    @PostMapping({"/ecableErpPc/ecbudMoney/start"})
    public Result<?> start(@RequestBody EcbuMoneyBaseBo bo) {
        return Result.ok(ecbudMoneyModel.start(bo));
    }
}
