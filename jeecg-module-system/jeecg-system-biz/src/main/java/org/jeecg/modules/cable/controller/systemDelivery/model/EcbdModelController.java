package org.jeecg.modules.cable.controller.systemDelivery.model;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.EcbdModelDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.ModelBaseBo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdModelModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@Tag(name = "快递重量--系统接口", description = "快递重量",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2050", parseValue = true)})})
@Validated
public class EcbdModelController {
    @Resource
    EcbdModelModel ecbdModelModel;

    // getObject
    @PostMapping({"/ecableAdminPc/ecbdModel/getObject"})
    public Result<EcbdModel> getObject(@RequestBody ModelBaseBo bo) {
        return Result.ok(ecbdModelModel.getObject(bo));
    }

    // deal
    @PostMapping({"/ecableAdminPc/ecbdModel/deal"})
    public Result<String> deal(@RequestBody EcbdModelDealBo bo) {
        return Result.ok(ecbdModelModel.deal(bo));
    }
}
