package org.jeecg.modules.cable.controller.userCommon.attribute;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.attribute.bo.AttributeBo;
import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.jeecg.modules.cable.model.userCommon.EcbusAttributeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "属性隐藏或者展示", description = "属性隐藏或者展示",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "191", parseValue = true)})})
@RestController
@Slf4j
public class EcbusAttributeController {
    @Resource
    EcbusAttributeModel ecbusAttributeModel;

    @Operation(summary = "控制属性信息显示与隐藏")
    @PostMapping({"/ecableErpPc/ecbusAttribute/deal"})
    public Result<?> deal(@RequestBody AttributeBo bo) {
        ecbusAttributeModel.deal(bo);
        return Result.ok();
    }


    @Operation(summary = "属性隐藏或者展示")
    @PostMapping({"/ecableErpPc/ecbusAttribute/getObject"})
    public Result<EcbusAttribute> getObject() {
        EcbusAttribute object = ecbusAttributeModel.getObject();
        return Result.ok(object);
    }
}
