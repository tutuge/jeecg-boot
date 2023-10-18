package org.jeecg.modules.cable.controller.quality.uarea;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.quality.uarea.bo.UAreaBo;
import org.jeecg.modules.cable.controller.quality.uarea.vo.UAreaVo;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.model.quality.EcuAreaModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "获取截面")
@RestController
public class EcuAreaController {
    @Resource
    EcuAreaModel ecuAreaModel;

    @Operation(summary = "获取截面列表")
    //getList
    @PostMapping({"/ecableErpPc/ecuArea/getList"})
    public Result<UAreaVo> getList(@RequestBody UAreaBo bo) {
        return Result.ok(ecuAreaModel.getListAndCount(bo));
    }

    @Operation(summary = "获取编辑截面信息")
    //getObject
    @PostMapping({"/ecableErpPc/ecuArea/getObject"})
    public Result<EcuArea> getObject(HttpServletRequest request) {
        return Result.ok(ecuAreaModel.getObject(request));
    }

    @Operation(summary = "提交")
    //deal
    @PostMapping({"/ecableErpPc/ecuArea/deal"})
    public Result<String> deal(HttpServletRequest request) throws IOException {
        return Result.ok(ecuAreaModel.deal(request));
    }

    @Operation(summary = "排序")
    //sort
    @PostMapping({"/ecableErpPc/ecuArea/sort"})
    public Result<?> sort(HttpServletRequest request) throws IOException {
        ecuAreaModel.sort(request);
        return Result.ok();
    }

    @Operation(summary = "开启")
    //start
    @PostMapping({"/ecableErpPc/ecuArea/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecuAreaModel.start(request));
    }
}
