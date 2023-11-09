package org.jeecg.modules.cable.controller.userDelivery.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryBaseBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliverySortBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.vo.EcbuDeliveryVo;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "运费管理--用户接口", description = "运费管理--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "117", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbuDelivery")
public class EcbuDeliveryController {
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel;

    @Operation(summary = "获取物流列表")
    @PostMapping({"/getList"})
    public Result<EcbuDeliveryVo> getList(@Validated @RequestBody EcbuDeliveryBo bo) {
        return Result.ok(ecbuDeliveryModel.getListAndCount(bo));
    }

    @Operation(summary = "获取物流详情")
    @PostMapping({"/getObject"})
    public Result<EcbuDelivery> getObject(@Valid @RequestBody EcbuDeliveryBaseBo bo) {
        return Result.ok(ecbuDeliveryModel.getObject(bo));
    }


    @Operation(summary = "编辑物流")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbuDeliveryInsertBo bo) {
        return Result.ok(ecbuDeliveryModel.deal(bo));
    }


    @Operation(summary = "物流排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Valid @RequestBody List<EcbuDeliverySortBo> bos) {
        ecbuDeliveryModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Valid @RequestBody EcbuDeliveryBaseBo bo) {
        ecbuDeliveryModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<?> start(@Valid @RequestBody EcbuDeliveryBaseBo bo) {
        return Result.ok(ecbuDeliveryModel.start(bo));
    }
}
