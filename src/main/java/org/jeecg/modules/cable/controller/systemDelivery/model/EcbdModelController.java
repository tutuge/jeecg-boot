package org.jeecg.modules.cable.controller.systemDelivery.model;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "快运重量--系统接口", description = "快运重量--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "127", parseValue = true)})})
@Validated
public class EcbdModelController {
    @Resource
    EcbdModelModel ecbdModelModel;

    @Operation(summary = "表头重量信息详情")
    @PostMapping({"/ecableAdminPc/ecbdModel/getObject"})
    public Result<EcbdModel> getObject(@RequestBody ModelBaseBo bo) {
        return Result.ok(ecbdModelModel.getObject(bo));
    }


    @Operation(summary = "表头重量信息编辑提交")
    @PostMapping({"/ecableAdminPc/ecbdModel/deal"})
    public Result<String> deal(@RequestBody EcbdModelDealBo bo) {
        return Result.ok(ecbdModelModel.deal(bo));
    }
}
