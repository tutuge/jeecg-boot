package org.jeecg.modules.cable.controller.userDelivery.money;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyBo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyStartBo;
import org.jeecg.modules.cable.controller.userDelivery.money.vo.MoneyVo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.model.userDelivery.EcbudMoneyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "快递价格")
@RestController
public class EcbudMoneyController {
    @Resource
    EcbudMoneyModel ecbudMoneyModel;

    @Operation(summary = "快递价格信息默认加载")
    //load 加载默认省信息
    @PostMapping({"/ecableErpPc/ecbudMoney/load"})
    public Result<?> load(@RequestBody EcbuMoneyBo bo, HttpServletRequest request) {
        ecbudMoneyModel.load(bo, request);
        return Result.ok();
    }


    @Operation(summary = "快递价格信息列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbudMoney/getList"})
    public Result<MoneyVo> getList(@RequestBody EcbuMoneyBo bo) {
        return Result.ok(ecbudMoneyModel.getListAndCount(bo));
    }


    @Operation(summary = "快递价格信息详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecbudMoney/getObject"})
    public Result<EcbudMoney> getObject(@RequestBody EcbuMoneyBo bo) {
        return Result.ok(ecbudMoneyModel.getObject(bo));
    }


    @Operation(summary = "快递价格信息编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecbudMoney/deal"})
    public Result<String> deal(@RequestBody EcbuMoneyInsertBo bo, HttpServletRequest request) {
        return Result.ok(ecbudMoneyModel.deal(bo, request));
    }


    @Operation(summary = "快递价格信息排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbudMoney/sort"})
    public Result<?> sort(@RequestBody EcbuMoneyBo bo, HttpServletRequest request) {

        ecbudMoneyModel.sort(bo, request);
        return Result.ok();
    }


    @Operation(summary = "快递价格信息删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbudMoney/delete"})
    public Result<?> delete(@RequestBody EcbuMoneyBo bo, HttpServletRequest request) {
        ecbudMoneyModel.delete(bo, request);
        return Result.ok();
    }


    @Operation(summary = "快递价格信息开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbudMoney/start"})
    public Result<?> start(@RequestBody EcbuMoneyStartBo bo) {
        return Result.ok(ecbudMoneyModel.start(bo));
    }
}
