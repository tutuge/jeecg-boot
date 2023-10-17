package org.jeecg.modules.cable.controller.userDelivery.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.model.userDelivery.EcbudDeliveryModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "快递")
@RestController
public class EcbudDeliveryController {
    @Resource
    EcbudDeliveryModel ecbudDeliveryModel;


    @Operation(summary = "获取公司或者个人默认快递")
    //getObject
    @PostMapping({"/ecableErpPc/ecbudDelivery/getObject"})
    public Result<EcbudDelivery> getObject(HttpServletRequest request) {
        return Result.ok(ecbudDeliveryModel.getObject(request));
    }

    @Operation(summary = "默认快递提交")
    //deal
    @PostMapping({"/ecableErpPc/ecbudDelivery/deal"})
    public Result<String> deal(@RequestBody EcbuDeliveryBo bo) {
        return Result.ok(ecbudDeliveryModel.deal(bo));
    }
}
