package org.jeecg.modules.cable.controller.efficiency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.efficiency.bo.EcdCollectBo;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "获取除丝号以外的txt内容", description = "获取除丝号以外的txt内容",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "21", parseValue = true)})})
@RestController
public class EcdCollectController {
    @Resource
    EcdCollectModel ecdCollectModel;

    @Operation(summary = "获取除丝号以外的txt内容")
    @PostMapping({"/ecableErpPc/ecdCollect/getObject"})
    public Result<Map<String, Object>> getObject(@Validated @RequestBody EcdCollectBo bo) {
        return Result.ok(ecdCollectModel.getObject(bo));
    }
}
