package org.jeecg.modules.cable.controller.userDelivery.weight;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.weight.bo.EcbudModelBo;
import org.jeecg.modules.cable.controller.userDelivery.weight.bo.EcbudWeightInsertBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;
import org.jeecg.modules.cable.model.userDelivery.EcbudWeightModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "物流详情上的快运重量区间--用户接口", description = "物流详情上的快运重量区间--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "128", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbudModel")
public class EcbudWeightController {
    @Resource
    EcbudWeightModel ecbudWeightModel;

    @Operation(summary = "快运重量区间信息编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbudWeightInsertBo bo) {
        return Result.ok(ecbudWeightModel.deal(bo));
    }


    @Operation(summary = "快运重量区间信息详情")
    @PostMapping({"/getObject"})
    public Result<EcbudWeight> getObjectPassEcbudId(@Validated @RequestBody EcbudModelBo bo) {
        return Result.ok(ecbudWeightModel.getObject(bo));
    }
}
