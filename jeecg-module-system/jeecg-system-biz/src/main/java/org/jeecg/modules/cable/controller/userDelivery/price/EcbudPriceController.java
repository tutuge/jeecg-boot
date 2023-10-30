package org.jeecg.modules.cable.controller.userDelivery.price;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbuPriceBaseBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbuPriceSortBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.price.vo.EcbudPriceVo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.model.userDelivery.EcbudPriceModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "物流价格(快运)--用户接口", description = "物流价格--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "121", parseValue = true)})})
@RestController
public class EcbudPriceController {
    @Resource
    EcbudPriceModel ecbudPriceModel;


    @Operation(summary = "快运价格信息列表")
    // getList
    @PostMapping({"/ecableErpPc/ecbudPrice/getList"})
    public Result<EcbudPriceVo> getList(@RequestBody EcbudPriceBo bo) {
        return Result.ok(ecbudPriceModel.getListAndCount(bo));
    }


    @Operation(summary = "快运价格信息详情")
    // getObject
    @PostMapping({"/ecableErpPc/ecbudPrice/getObject"})
    public Result<EcbudPrice> getObject(@Validated @RequestBody EcbuPriceBaseBo bo) {
        return Result.ok(ecbudPriceModel.getObject(bo));
    }


    @Operation(summary = "快运价格信息编辑")
    // deal
    @PostMapping({"/ecableErpPc/ecbudPrice/deal"})
    public Result<?> deal(@RequestBody EcbudPriceInsertBo bo) {
        return Result.ok(ecbudPriceModel.deal(bo));
    }


    @Operation(summary = "快运价格信息排序")
    // sort
    @PostMapping({"/ecableErpPc/ecbudPrice/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbuPriceSortBo> bos) {
        ecbudPriceModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "快运价格信息删除")
    // delete
    @PostMapping({"/ecableErpPc/ecbudPrice/delete"})
    public Result<?> delete(@Validated @RequestBody EcbuPriceBaseBo bo) {
        ecbudPriceModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "快运价格信息开启禁用")
    // start
    @PostMapping({"/ecableErpPc/ecbudPrice/start"})
    public Result<?> start(@Validated @RequestBody EcbuPriceBaseBo bo) {
        return Result.ok(ecbudPriceModel.start(bo));
    }
}
