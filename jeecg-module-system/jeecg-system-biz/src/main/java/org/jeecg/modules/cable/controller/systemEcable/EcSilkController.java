package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "丝型号")
@RestController
public class EcSilkController {
    @Resource
    EcSilkModel ecSilkModel;


    @Operation(summary = "获取丝型号")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecSilk/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecSilkModel.getList(request);
    }


    @Operation(summary = "根据silkName获取丝列号列表")
    //根据silkName获取数据列表列表
    @PostMapping({"/ecableErpPc/ecSilk/getListPassSilkName"})
    public Map<String, Object> getListPassSilkName(HttpServletRequest request) {
        return ecSilkModel.getListPassSilkName(request);
    }

    @Operation(summary = "获取所有丝型号列表")
    //获取数据列表列表
    @PostMapping({"/ecableErpPc/ecSilk/getListSilkName"})
    public Map<String, Object> getListSilkName(HttpServletRequest request) {
        return ecSilkModel.getListSilkName(request);
    }
}
