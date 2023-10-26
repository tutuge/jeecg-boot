package org.jeecg.modules.cable.controller.userCommon.taxpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.vo.TaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.jeecg.modules.cable.model.userCommon.EcdTaxpointModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "发票税点", description = "发票税点",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "550", parseValue = true)})})
@RestController
public class EcdTaxpointController {
    @Resource
    EcdTaxpointModel ecdTaxpointModel;

    @Operation(summary = "获取税点列表")
    //getList
    @PostMapping({"/ecableErpPc/ecdTaxpoint/getList"})
    public Result<TaxPointVo> getList(@RequestBody TaxPointBo bo) {
        return Result.ok(ecdTaxpointModel.getListAndCount(bo));
    }

    @Operation(summary = "获取税点")
    //getObject
    @PostMapping({"/ecableErpPc/ecdTaxpoint/getObject"})
    public Result<EcdTaxpoint> getObject(@Validated @RequestBody TaxPointBaseBo bo) {
        return Result.ok(ecdTaxpointModel.getObject(bo));
    }
}
