package org.jeecg.modules.cable.controller.userDelivery.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.model.bo.EcbudModelBo;
import org.jeecg.modules.cable.controller.userDelivery.model.bo.EcbudModelInsertBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudModelModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "快运重量区间--用户接口", description = "快运重量--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "128", parseValue = true)})})
@RestController
public class EcbudModelController {
    @Resource
    EcbudModelModel ecbudModelModel;

    @Operation(summary = "快运重量区间信息编辑提交")
    @PostMapping({"/ecableErpPc/ecbudModel/deal"})
    public Result<String> deal(@RequestBody EcbudModelInsertBo bo) {
        return Result.ok(ecbudModelModel.deal(bo));
    }


    @Operation(summary = "快运重量区间信息详情")
    @PostMapping({"/ecableErpPc/ecbudModel/getObject"})
    public Result<EcbudModel> getObjectPassEcbudId(@Validated @RequestBody EcbudModelBo bo) {
        return Result.ok(ecbudModelModel.getObject(bo));
    }
}
