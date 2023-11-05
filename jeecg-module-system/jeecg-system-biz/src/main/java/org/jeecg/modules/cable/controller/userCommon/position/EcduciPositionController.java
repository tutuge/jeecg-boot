package org.jeecg.modules.cable.controller.userCommon.position;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcduciPositionBo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.PositionBo;
import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.model.userCommon.EcduciPositionModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "报价单图片位置", description = "报价单图片位置",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "141", parseValue = true)})})
@RestController
public class EcduciPositionController {
    @Resource
    EcduciPositionModel ecduciPositionModel;

    @Operation(summary = "获取图片位置")
    @PostMapping({"/ecableErpPc/ecduciPosition/getObject"})
    public Result<EcduciPosition> getObject(@Validated @RequestBody PositionBo bo) {
        return Result.ok(ecduciPositionModel.getObject(bo));
    }


    @Operation(summary = "图片位置编辑")

    @PostMapping({"/ecableErpPc/ecduciPosition/deal"})
    public Result<String> deal(@Validated @RequestBody EcduciPositionBo bo) {
        return Result.ok(ecduciPositionModel.deal(bo));
    }
}
