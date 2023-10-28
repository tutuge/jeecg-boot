package org.jeecg.modules.cable.controller.systemDelivery.price;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.*;
import org.jeecg.modules.cable.controller.systemDelivery.price.vo.EcbdPriceListVo;
import org.jeecg.modules.cable.model.systemDelivery.EcbdPriceModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "物流价格--系统接口", description = "物流价格--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "120", parseValue = true)})})
@RestController
@Slf4j
@Validated
@RequestMapping("/ecableAdminPc/ecbdPrice")
public class EcbdPriceController {
    @Resource
    EcbdPriceModel ecbdPriceModel;

    // load 加载默认省信息
    @Operation(summary = "加载默认省信息")
    @PostMapping({"/load"})
    public Result<?> load(@RequestBody EcbdPriceLoadBo bo) {
        ecbdPriceModel.load(bo);
        return Result.ok();
    }

    // getList 获取数据列表
    @Operation(summary = "获取数据列表")
    @PostMapping({"/getList"})
    public Result<EcbdPriceListVo> getList(@RequestBody EcbdPriceListBo bo) {
        return Result.ok(ecbdPriceModel.getList(bo));
    }

    // deal 修改信息
    @Operation(summary = "修改信息")
    @PostMapping({"/deal"})
    public Result<?> deal(@RequestBody EcbdPriceDealBo bo) {
        ecbdPriceModel.deal(bo);
        return Result.ok();
    }

    // sort 排序
    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbdPriceSortBo> bos) {
        ecbdPriceModel.sort(bos);
        return Result.ok();
    }

    // start 启用、禁用
    @Operation(summary = "启用、禁用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbdPriceBaseBo bo) {
        return Result.ok(ecbdPriceModel.start(bo));
    }
}
