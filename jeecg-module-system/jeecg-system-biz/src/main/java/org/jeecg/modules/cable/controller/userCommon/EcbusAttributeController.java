package org.jeecg.modules.cable.controller.userCommon;

import org.jeecg.modules.cable.model.userCommon.EcbusAttributeModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class EcbusAttributeController {
    @Resource
    EcbusAttributeModel ecbusAttributeModel;

    @PostMapping({"/ecableErpPc/ecbusAttribute/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecbusAttributeModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecbusAttribute/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecbusAttributeModel.getObject(request);
    }
}
