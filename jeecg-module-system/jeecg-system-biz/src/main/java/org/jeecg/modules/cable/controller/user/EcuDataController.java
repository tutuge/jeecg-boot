package org.jeecg.modules.cable.controller.user;

import org.jeecg.modules.cable.model.user.EcuDataModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuDataController {
    @Resource
    EcuDataModel ecuDataModel;

    @PostMapping({"/ecableErpPc/ecuData/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuDataModel.getObject(request);
    }

    @PostMapping({"/ecableErpPc/ecuData/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuDataModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecuData/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuDataModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecuData/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecuDataModel.start(request);
    }
}
