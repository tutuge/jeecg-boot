package org.jeecg.modules.cable.controller.userDelivery.price;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.price.vo.EcbudPriceVo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.model.userDelivery.EcbudPriceModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "物流价格")
@RestController
public class EcbudPriceController {
    @Resource
    EcbudPriceModel ecbudPriceModel;


    @Operation(summary = "物流信息加载")
    @PostMapping({"/ecableErpPc/ecbudPrice/load"})
    public Result<?> load(@RequestBody EcbudPriceBo bo) {
        ecbudPriceModel.load(bo);
        return Result.ok();
    }


    @Operation(summary = "物流信息列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbudPrice/getList"})
    public Result<EcbudPriceVo> getList(@RequestBody EcbudPriceBo bo) {
        return Result.ok(ecbudPriceModel.getListAndCount(bo));
    }


    @Operation(summary = "物流信息详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecbudPrice/getObject"})
    public Result<EcbudPrice> getObject(@RequestBody EcbudPriceBo bo) {
        return Result.ok(ecbudPriceModel.getObject(bo));
    }


    @Operation(summary = "物流信息编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecbudPrice/deal"})
    public Result<?> deal(@RequestBody EcbudPriceInsertBo bo) {
        return Result.ok(ecbudPriceModel.deal(bo));
    }


    @Operation(summary = "物流信息排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbudPrice/sort"})
    public Result<?> sort(@RequestBody EcbudPriceBo bo) {
        ecbudPriceModel.sort(bo);
        return Result.ok();
    }


    @Operation(summary = "物流信息删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbudPrice/delete"})
    public Result<?> delete(@RequestBody EcbudPriceBo bo) {
        ecbudPriceModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "物流信息开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbudPrice/start"})
    public Result<?> start(@RequestBody EcbudPriceBo bo) {
        return Result.ok(ecbudPriceModel.start(bo));
    }
}
