package org.jeecg.modules.cable.controller.quality;

import org.jeecg.modules.cable.model.quality.EcquLevelModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class EcquLevelController {
    @Resource
    EcquLevelModel ecquLevelModel;

    //getList
    @PostMapping({"/ecableErpPc/ecquLevel/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecquLevelModel.getList(request);
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecquLevel/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecquLevelModel.getObject(request);
    }

    //deal
    @PostMapping({"/ecableErpPc/ecquLevel/deal"})
    public Map<String, Object> deal(HttpServletRequest request) throws IOException {
        return ecquLevelModel.deal(request);
    }

    //sort
    @PostMapping({"/ecableErpPc/ecquLevel/sort"})
    public Map<String, Object> sort(HttpServletRequest request) throws IOException {
        return ecquLevelModel.sort(request);
    }

    //delete
    @PostMapping({"/ecableErpPc/ecquLevel/delete"})
    public Map<String, Object> delete(HttpServletRequest request) throws IOException {
        return ecquLevelModel.delete(request);
    }

    //start
    @PostMapping({"/ecableErpPc/ecquLevel/start"})
    public Map<String, Object> start(HttpServletRequest request) throws IOException {
        return ecquLevelModel.start(request);
    }

    //defaultTYpe
    @PostMapping({"/ecableErpPc/ecquLevel/defaultType"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        return ecquLevelModel.defaultType(request);
    }
}
