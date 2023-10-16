package org.jeecg.modules.cable.controller.quality;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.quality.EcquLevelModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Tag(name = "电缆等级")
@RestController
public class EcquLevelController {
    @Resource
    EcquLevelModel ecquLevelModel;

    @Operation(summary = "获取电缆质量列表")
    //getList
    @PostMapping({"/ecableErpPc/ecquLevel/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecquLevelModel.getList(request);
    }

    @Operation(summary = "获取编辑质量信息")
    //getObject
    @PostMapping({"/ecableErpPc/ecquLevel/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecquLevelModel.getObject(request);
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecquLevel/deal"})
    public Map<String, Object> deal(HttpServletRequest request) throws IOException {
        return ecquLevelModel.deal(request);
    }

    @Operation(summary = "排序")
    //sort
    @PostMapping({"/ecableErpPc/ecquLevel/sort"})
    public Map<String, Object> sort(HttpServletRequest request) throws IOException {
        return ecquLevelModel.sort(request);
    }

    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecquLevel/delete"})
    public Map<String, Object> delete(HttpServletRequest request) throws IOException {
        return ecquLevelModel.delete(request);
    }


    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecquLevel/start"})
    public Map<String, Object> start(HttpServletRequest request) throws IOException {
        return ecquLevelModel.start(request);
    }

    @Operation(summary = "是否默认")
    //defaultTYpe
    @PostMapping({"/ecableErpPc/ecquLevel/defaultType"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        return ecquLevelModel.defaultType(request);
    }
}
