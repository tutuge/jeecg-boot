package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EccUnitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "默认单位")
@RestController
public class EccUnitController {
    @Resource
    EccUnitModel eccUnitModel;

    @Operation(summary = "获取单位回显数据")
    @PostMapping({"/ecableErpPc/eccUnit/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return eccUnitModel.getObject(request);
    }

    @Operation(summary = "获取默认单位列表")
    @PostMapping({"/ecableErpPc/eccUnit/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return eccUnitModel.getList(request);
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/eccUnit/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return eccUnitModel.deal(request);
    }


    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/eccUnit/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return eccUnitModel.start(request);
    }


    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/eccUnit/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return eccUnitModel.sort(request);
    }


    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/eccUnit/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return eccUnitModel.delete(request);
    }
}
