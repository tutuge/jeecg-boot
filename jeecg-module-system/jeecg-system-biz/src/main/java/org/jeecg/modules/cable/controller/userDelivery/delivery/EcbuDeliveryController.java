package org.jeecg.modules.cable.controller.userDelivery.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.vo.EcbuDeliveryVo;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "物流")
@RestController
public class EcbuDeliveryController {
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel;

    @Operation(summary = "获取物流列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuDelivery/getList"})
    public Result<EcbuDeliveryVo> getList(@RequestBody EcbuDeliveryBo bo) {
        return Result.ok(ecbuDeliveryModel.getListAndCount(bo));
    }

    @Operation(summary = "获取物流详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecbuDelivery/getObject"})
    public Result<EcbuDelivery> getObject(@RequestBody EcbuDeliveryBo bo) {
        return Result.ok(ecbuDeliveryModel.getObject(bo));
    }


    @Operation(summary = "编辑物流")
    //deal
    @PostMapping({"/ecableErpPc/ecbuDelivery/deal"})
    public Result<String> deal(@RequestBody EcbuDeliveryInsertBo bo) {
        return Result.ok(ecbuDeliveryModel.deal(bo));
    }


    @Operation(summary = "物流排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbuDelivery/sort"})
    public Result<?> sort(@RequestBody EcbuDeliveryBo bo) {
        ecbuDeliveryModel.sort(bo);
        return Result.ok();
    }


    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbuDelivery/delete"})
    public Result<?> delete(@RequestBody EcbuDeliveryBo bo) {

        ecbuDeliveryModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuDelivery/start"})
    public Result<?> start(@RequestBody EcbuDeliveryBo bo) {
        return Result.ok(ecbuDeliveryModel.start(bo));
    }
}
