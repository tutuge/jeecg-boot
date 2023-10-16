package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuDescModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "报价说明")
@RestController
public class EcuDescController {
    @Resource
    EcuDescModel ecuDescModel;

    @Operation(summary = "获取回显数据")
    @PostMapping({"/ecableErpPc/ecuDesc/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuDescModel.getObject(request);
    }

    @Operation(summary = "获取报价说明列表")
    @PostMapping({"/ecableErpPc/ecuDesc/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuDescModel.getList(request);
    }

    @Operation(summary = "编辑数据")
    @PostMapping({"/ecableErpPc/ecuDesc/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuDescModel.deal(request);
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecuDesc/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecuDescModel.start(request);
    }

    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecuDesc/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecuDescModel.sort(request);
    }

    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecuDesc/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecuDescModel.delete(request);
    }

    @Operation(summary = "设置默认")
    //设置默认
    @PostMapping({"/ecableErpPc/ecuDesc/defaultType"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        return ecuDescModel.defaultType(request);
    }
}
