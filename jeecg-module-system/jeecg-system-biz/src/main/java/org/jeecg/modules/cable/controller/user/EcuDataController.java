package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuDataModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "默认参数")
@RestController
public class EcuDataController {
    @Resource
    EcuDataModel ecuDataModel;

    @Operation(summary = "编辑时回显")
    @PostMapping({"/ecableErpPc/ecuData/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuDataModel.getObject(request);
    }

    @Operation(summary = "获取默认参数列表")
    @PostMapping({"/ecableErpPc/ecuData/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuDataModel.getList(request);
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecuData/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuDataModel.deal(request);
    }

    @Operation(summary = "是否启用")
    @PostMapping({"/ecableErpPc/ecuData/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecuDataModel.start(request);
    }
}
