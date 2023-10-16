package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.userCommon.EcduciPositionModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "图片位置")
@RestController
public class EcduciPositionController {
    @Resource
    EcduciPositionModel ecduciPositionModel;

    @Operation(summary = "获取图片位置")
    @PostMapping({"/ecableErpPc/ecduciPosition/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecduciPositionModel.getObject(request);
    }


    @Operation(summary = "图片位置编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecduciPosition/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecduciPositionModel.deal(request);
    }
}
