package org.jeecg.modules.cable.controller.systemEcable;

import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcSilkController {
    @Resource
    EcSilkModel ecSilkModel;

    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecSilk/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecSilkModel.getList(request);
    }

    //根据silkName获取数据列表列表
    @PostMapping({"/ecableErpPc/ecSilk/getListPassSilkName"})
    public Map<String, Object> getListPassSilkName(HttpServletRequest request) {
        return ecSilkModel.getListPassSilkName(request);
    }

    //获取数据列表列表
    @PostMapping({"/ecableErpPc/ecSilk/getListSilkName"})
    public Map<String, Object> getListSilkName(HttpServletRequest request) {
        return ecSilkModel.getListSilkName(request);
    }
}
