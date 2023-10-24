package org.jeecg.modules.cable.controller.systemDelivery.price;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.*;
import org.jeecg.modules.cable.controller.systemDelivery.price.vo.EcbdPriceListVo;
import org.jeecg.modules.cable.model.systemDelivery.EcbdPriceModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order =2060)
@RestController
@Slf4j
public class EcbdPriceController {
    @Resource
    EcbdPriceModel ecbdPriceModel;

    //load 加载默认省信息
    @PostMapping({"/ecableAdminPc/ecbdPrice/load"})
    public Result<?> load(@RequestBody EcbdPriceLoadBo bo) {
        ecbdPriceModel.load(bo);
        return Result.ok();
    }

    //getList 获取数据列表
    @PostMapping({"/ecableAdminPc/ecbdPrice/getList"})
    public Result<EcbdPriceListVo> getList(@RequestBody EcbdPriceListBo bo) {
        return Result.ok(ecbdPriceModel.getList(bo));
    }

    //deal 修改信息
    @PostMapping({"/ecableAdminPc/ecbdPrice/deal"})
    public Result<?> deal(@RequestBody EcbdPriceDealBo bo) {
        ecbdPriceModel.deal(bo);
        return Result.ok();
    }

    //sort 排序
    @PostMapping({"/ecableAdminPc/ecbdPrice/sort"})
    public Result<?> sort(@RequestBody List<EcbdPriceSortBo> bos) {
        ecbdPriceModel.sort(bos);
        return Result.ok();
    }

    //start 启用、禁用
    @PostMapping({"/ecableAdminPc/ecbdPrice/start"})
    public Result<String> start(@RequestBody EcbdPriceBaseBo bo) {
        return Result.ok(ecbdPriceModel.start(bo));
    }
}
