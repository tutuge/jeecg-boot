package org.jeecg.modules.cable.controller.quality.level;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.quality.level.vo.LevelVo;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.model.quality.EcquLevelModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "电缆等级")
@RestController
public class EcquLevelController {
    @Resource
    EcquLevelModel ecquLevelModel;

    @Operation(summary = "获取电缆质量列表")
    //getList
    @PostMapping({"/ecableErpPc/ecquLevel/getList"})
    public Result<LevelVo> getList(HttpServletRequest request) {
        return Result.ok(ecquLevelModel.getList(request));
    }

    @Operation(summary = "获取编辑质量信息")
    //getObject
    @PostMapping({"/ecableErpPc/ecquLevel/getObject"})
    public Result<EcquLevel> getObject(HttpServletRequest request) {
        return Result.ok(ecquLevelModel.getObject(request));
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecquLevel/deal"})
    public Result<String> deal(HttpServletRequest request) throws IOException {
        return Result.ok(ecquLevelModel.deal(request));
    }

    @Operation(summary = "排序")
    //sort
    @PostMapping({"/ecableErpPc/ecquLevel/sort"})
    public Result<?> sort(HttpServletRequest request) throws IOException {
        ecquLevelModel.sort(request);
        return Result.ok();
    }

    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecquLevel/delete"})
    public Result<?> delete(HttpServletRequest request) throws IOException {
        ecquLevelModel.delete(request);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecquLevel/start"})
    public Result<String> start(HttpServletRequest request) throws IOException {
        return Result.ok(ecquLevelModel.start(request));
    }

    @Operation(summary = "是否默认")
    //defaultTYpe
    @PostMapping({"/ecableErpPc/ecquLevel/defaultType"})
    public Result<?> defaultType(HttpServletRequest request) {
        ecquLevelModel.defaultType(request);
        return Result.ok();
    }
}
