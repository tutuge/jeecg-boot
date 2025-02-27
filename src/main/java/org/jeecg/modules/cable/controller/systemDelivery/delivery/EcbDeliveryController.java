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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "运费管理--系统接口", description = "运费管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "119", parseValue = true)})})
@RestController
@Slf4j
@RequestMapping("/ecableAdminPc/ecbDelivery")
public class EcbDeliveryController {
    @Resource
    private EcbDeliveryModel ecbDeliveryModel;


    @Operation(summary = "获取物流列表")
    @PostMapping({"/getList"})
    public Result<EcbDeliveryListVo> getList(@RequestBody EcbDeliveryListBo bo) {
        return Result.ok(ecbDeliveryModel.getList(bo));
    }


    @Operation(summary = "获取物流列表")
    @PostMapping({"/getObject"})
    public Result<EcbDelivery> getObject(@RequestBody EcbDeliveryBaseBo bo) {
        return Result.ok(ecbDeliveryModel.getObject(bo));
    }


    @Operation(summary = "编辑物流")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbDeliveryDealBo bo) {
        return Result.ok(ecbDeliveryModel.deal(bo));
    }


    @Operation(summary = "物流排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbDeliverySortBo> bos) {
        ecbDeliveryModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "启用")
    @PostMapping({"/start"})
    public Result<?> start(@RequestBody EcbDeliveryBaseBo bo) {
        return Result.ok(ecbDeliveryModel.start(bo));
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbDeliveryBaseBo bo) {
        ecbDeliveryModel.delete(bo);
        return Result.ok();
    }
}
