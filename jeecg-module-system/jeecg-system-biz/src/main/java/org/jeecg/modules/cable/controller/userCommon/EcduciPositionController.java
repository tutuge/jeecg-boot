package org.jeecg.modules.cable.controller.userCommon;

import org.jeecg.modules.cable.model.userCommon.EcduciPositionModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcduciPositionController {
    @Resource
    EcduciPositionModel ecduciPositionModel;

    @PostMapping({"/ecableErpPc/ecduciPosition/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecduciPositionModel.getObject(request);
    }

    //deal
    @PostMapping({"/ecableErpPc/ecduciPosition/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecduciPositionModel.deal(request);
    }
}
