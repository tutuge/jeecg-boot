package org.jeecg.modules.cable.controller.userCommon;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.jeecg.modules.cable.model.userCommon.EcbusAttributeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiSort(10121)
@Tag(name = "属性隐藏或者展示")
@RestController
@Slf4j
public class EcbusAttributeController {
    @Resource
    EcbusAttributeModel ecbusAttributeModel;

    @Operation(summary = "控制属性信息显示与隐藏")
    @PostMapping({"/ecableErpPc/ecbusAttribute/deal"})
    public Result<?> deal(HttpServletRequest request) {
        ecbusAttributeModel.deal(request);
        return Result.ok();
    }


    @Operation(summary = "属性隐藏或者展示")
    @PostMapping({"/ecableErpPc/ecbusAttribute/getObject"})
    public Result<EcbusAttribute> getObject() {
        EcbusAttribute object = ecbusAttributeModel.getObject();
        return Result.ok(object);
    }
}
