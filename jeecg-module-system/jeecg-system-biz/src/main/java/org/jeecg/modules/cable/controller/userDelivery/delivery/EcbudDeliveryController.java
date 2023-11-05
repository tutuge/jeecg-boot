package org.jeecg.modules.cable.controller.userDelivery.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbudDeliveryBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.model.userDelivery.EcbudDeliveryModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户默认物流或快递类型--用户接口", description = "用户默认物流或快递类型--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "118", parseValue = true)})})
@RestController
public class EcbudDeliveryController {
    @Resource
    EcbudDeliveryModel ecbudDeliveryModel;


    @Operation(summary = "获取公司或者个人默认快递")

    @PostMapping({"/ecableErpPc/ecbudDelivery/getObject"})
    public Result<EcbudDelivery> getObject() {
        return Result.ok(ecbudDeliveryModel.getObject());
    }

    @Operation(summary = "默认快递提交")

    @PostMapping({"/ecableErpPc/ecbudDelivery/deal"})
    public Result<String> deal(@RequestBody EcbudDeliveryBo bo) {
        return Result.ok(ecbudDeliveryModel.deal(bo));
    }
}
