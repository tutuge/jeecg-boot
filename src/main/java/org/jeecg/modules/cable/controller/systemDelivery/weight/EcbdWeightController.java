package org.jeecg.modules.cable.controller.systemDelivery.weight;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.weight.bo.EcbdModelDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.weight.bo.ModelBaseBo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdWeight;
import org.jeecg.modules.cable.model.systemDelivery.EcbdWeightlModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@Tag(name = "快运重量--系统接口", description = "快运重量--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "127", parseValue = true)})})
@Validated
@RequestMapping("/ecableAdminPc/ecbdModel")
public class EcbdWeightController {
    @Resource
    EcbdWeightlModel ecbdWeightlModel;

    @Operation(summary = "表头重量信息详情")
    @PostMapping({"/getObject"})
    public Result<EcbdWeight> getObject(@RequestBody ModelBaseBo bo) {
        return Result.ok(ecbdWeightlModel.getObject(bo));
    }


    @Operation(summary = "表头重量信息编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbdModelDealBo bo) {
        return Result.ok(ecbdWeightlModel.deal(bo));
    }
}
