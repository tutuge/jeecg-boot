package org.jeecg.modules.cable.controller.systemDelivery.money;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.money.bo.*;
import org.jeecg.modules.cable.controller.systemDelivery.money.vo.EcbdMoneyListVo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyWeightBo;
import org.jeecg.modules.cable.model.systemDelivery.EcbdMoneyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "快递价格--系统接口", description = "快递价格--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2051", parseValue = true)})})
@RestController
@Slf4j
@Validated
@RequestMapping("/ecableAdminPc/ecbdMoney")
public class EcbdMoneyController {
    @Resource
    EcbdMoneyModel ecbdMoneyModel;

    // load 加载默认省信息
    @Operation(summary = "快递价格信息默认加载")
    @PostMapping({"/load"})
    public Result<?> load(@RequestBody EcbdMoneyBo bo) {
        ecbdMoneyModel.load(bo);
        return Result.ok();
    }


    @Operation(summary = "获取数据列表")
    @PostMapping({"/getList"})
    public Result<EcbdMoneyListVo> getList(@RequestBody EcbdMoneyListBo bo) {
        return Result.ok(ecbdMoneyModel.getList(bo));
    }

    @Operation(summary = "修改信息")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbdMoneyDealBo bo) {
        String msg = ecbdMoneyModel.deal(bo);
        return Result.ok(msg);
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbdMoneySortBo> bos) {
        ecbdMoneyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "启用、禁用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbdMoneyBaseBo bo) {
        return Result.ok(ecbdMoneyModel.start(bo));
    }

    @Operation(summary = "快递价格信息删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbdMoneyBaseBo bo) {
        ecbdMoneyModel.delete(bo);
        return Result.ok();
    }

    @Operation(summary = "快递首重信息批量编辑")
    @PostMapping({"/weight"})
    public Result<String> weight(@Validated @RequestBody List<EcbMoneyWeightBo> bos) {
        return Result.ok(ecbdMoneyModel.weight(bos));
    }
}
