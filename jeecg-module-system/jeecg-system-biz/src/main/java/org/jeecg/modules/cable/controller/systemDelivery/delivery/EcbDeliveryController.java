package org.jeecg.modules.cable.controller.systemDelivery.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryBaseBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryListBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliverySortBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.vo.EcbDeliveryListVo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.model.systemDelivery.EcbDeliveryModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "运费管理--系统接口", description = "运费管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2040", parseValue = true)})})
@RestController
@Slf4j
@Validated
public class EcbDeliveryController {
    @Resource
    EcbDeliveryModel ecbDeliveryModel;

    // getList
    @Operation(summary = "获取物流列表")
    @PostMapping({"/ecableAdminPc/ecbDelivery/getList"})
    public Result<EcbDeliveryListVo> getList(@RequestBody EcbDeliveryListBo bo) {
        return Result.ok(ecbDeliveryModel.getList(bo));
    }

    // getObject
    @Operation(summary = "获取物流列表")
    @PostMapping({"/ecableAdminPc/ecbDelivery/getObject"})
    public Result<EcbDelivery> getObject(@RequestBody EcbDeliveryBaseBo bo) {
        return Result.ok(ecbDeliveryModel.getObject(bo));
    }

    // deal
    @Operation(summary = "编辑物流")
    @PostMapping({"/ecableAdminPc/ecbDelivery/deal"})
    public Result<String> deal(@RequestBody EcbDeliveryDealBo bo) {
        return Result.ok(ecbDeliveryModel.deal(bo));
    }

    // sort
    @Operation(summary = "物流排序")
    @PostMapping({"/ecableAdminPc/ecbDelivery/sort"})
    public Result<?> sort(@RequestBody List<EcbDeliverySortBo> bos) {
        ecbDeliveryModel.sort(bos);
        return Result.ok();
    }

    // start
    @Operation(summary = "启用")
    @PostMapping({"/ecableAdminPc/ecbDelivery/start"})
    public Result<?> start(@RequestBody EcbDeliveryBaseBo bo) {
        return Result.ok(ecbDeliveryModel.start(bo));
    }

    // delete
    @Operation(summary = "删除")
    @PostMapping({"/ecableAdminPc/ecbDelivery/delete"})
    public Result<?> delete(@RequestBody EcbDeliveryBaseBo bo) {
        ecbDeliveryModel.delete(bo);
        return Result.ok();
    }
}
