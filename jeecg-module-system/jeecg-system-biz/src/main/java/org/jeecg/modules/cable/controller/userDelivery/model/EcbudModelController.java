package org.jeecg.modules.cable.controller.userDelivery.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userDelivery.model.bo.EcbudModelBo;
import org.jeecg.modules.cable.controller.userDelivery.model.bo.EcbudModelInsertBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudModelModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "表头")
@RestController
public class EcbudModelController {
    @Resource
    EcbudModelModel ecbudModelModel;

    @Operation(summary = "表头编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecbudModel/deal"})
    public Result<String> deal(@RequestBody EcbudModelInsertBo bo) {
        return Result.ok(ecbudModelModel.deal(bo));
    }


    @Operation(summary = "表头详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecbudModel/getObject"})
    public Result<EcbudModel> getObjectPassEcbudId(@RequestBody EcbudModelBo bo) {
        return Result.ok(ecbudModelModel.getObject(bo));
    }
}
