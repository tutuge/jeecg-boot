package org.jeecg.modules.cable.controller.efficiency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.efficiency.bo.EcdAreaBo;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.model.efficiency.EcdAreaModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "获取质量等级对应截面--系统接口", description = "获取质量等级对应截面--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "101", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecdArea")
public class EcdAreaController {
    @Resource
    EcdAreaModel ecdAreaModel;

    @Operation(summary = "获取质量等级对应截面")
    @PostMapping({"/getObject"})
    public Result<List<EcuArea>> getObject(@RequestBody EcdAreaBo bo) {
        return Result.ok(ecdAreaModel.getObject(bo));
    }
}
