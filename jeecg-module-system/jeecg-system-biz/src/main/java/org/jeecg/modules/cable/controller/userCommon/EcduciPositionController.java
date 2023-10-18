package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.model.userCommon.EcduciPositionModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "图片位置")
@RestController
public class EcduciPositionController {
    @Resource
    EcduciPositionModel ecduciPositionModel;

    @Operation(summary = "获取图片位置")
    @PostMapping({"/ecableErpPc/ecduciPosition/getObject"})
    public Result<EcduciPosition> getObject(HttpServletRequest request) {
        return Result.ok(ecduciPositionModel.getObject(request));
    }


    @Operation(summary = "图片位置编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecduciPosition/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecduciPositionModel.deal(request));
    }
}
