package org.jeecg.modules.cable.controller.user.data;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.jeecg.modules.cable.model.user.EcuDataModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "默认参数")
@RestController
public class EcuDataController {
    @Resource
    EcuDataModel ecuDataModel;

    @Operation(summary = "根据id获取对象")
    @PostMapping({"/ecableErpPc/ecuData/getObject"})
    public Result<EcuData> getObject(HttpServletRequest request) {
        return Result.ok(ecuDataModel.getObject(request));
    }

    @Operation(summary = "获取默认参数列表")
    @PostMapping({"/ecableErpPc/ecuData/getList"})
    public Result<Map<String, Object>> getList(HttpServletRequest request) {
        return Result.ok(ecuDataModel.getList(request));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecuData/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecuDataModel.deal(request));
    }

    @Operation(summary = "是否启用")
    @PostMapping({"/ecableErpPc/ecuData/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecuDataModel.start(request));
    }
}
