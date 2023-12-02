package org.jeecg.modules.cable.controller.efficiency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.efficiency.bo.PccBo;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;
import org.jeecg.modules.cable.entity.userPcc.EcuProvince;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "获取省份对应信息", description = "获取省份对应信息",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "181", parseValue = true)})})
@RestController
public class EcduPccController {
    @Resource
    EcduPccModel ecduPccModel;

    @Operation(summary = "获取省份/县级对应信息")
    @PostMapping({"/ecableErpPc/ecduPcc/getObject"})
    public Result<List<EcuProvince>> getObject(@RequestBody PccBo bo) {
        return Result.ok(ecduPccModel.getObject(bo));
    }
}
