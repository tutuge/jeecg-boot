package org.jeecg.modules.cable.controller.systemDelivery.money;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.money.bo.*;
import org.jeecg.modules.cable.controller.systemDelivery.money.vo.EcbdMoneyListVo;
import org.jeecg.modules.cable.model.systemDelivery.EcbdMoneyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order =2050)
@RestController
@Slf4j
public class EcbdMoneyController {
    @Resource
    EcbdMoneyModel ecbdMoneyModel;

    //load 加载默认省信息
    @PostMapping({"/ecableAdminPc/ecbdMoney/load"})
    public Result<?> load(@RequestBody EcbdMoneyBo bo) {
        ecbdMoneyModel.load(bo);
        return Result.ok();
    }

    //getList 获取数据列表
    @PostMapping({"/ecableAdminPc/ecbdMoney/getList"})
    public Result<EcbdMoneyListVo> getList(@RequestBody EcbdMoneyListBo bo) {
        return Result.ok(ecbdMoneyModel.getList(bo));
    }

    //deal 修改信息
    @PostMapping({"/ecableAdminPc/ecbdMoney/deal"})
    public Result<?> deal(@RequestBody EcbdMoneyDealBo bo) {
        ecbdMoneyModel.deal(bo);
        return Result.ok();
    }

    //sort 排序
    @PostMapping({"/ecableAdminPc/ecbdMoney/sort"})
    public Result<?> sort(@RequestBody List<EcbdMoneySortBo> bos) {
        ecbdMoneyModel.sort(bos);
        return Result.ok();
    }

    //start 启用、禁用
    @PostMapping({"/ecableAdminPc/ecbdMoney/start"})
    public Result<String> start(@RequestBody EcbdMoneyBaseBo bo) {
        return Result.ok(ecbdMoneyModel.start(bo));
    }
}
