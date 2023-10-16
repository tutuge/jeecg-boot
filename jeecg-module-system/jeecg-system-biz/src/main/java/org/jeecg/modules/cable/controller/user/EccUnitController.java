package org.jeecg.modules.cable.controller.user;

import org.jeecg.modules.cable.model.user.EccUnitModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EccUnitController {
    @Resource
    EccUnitModel eccUnitModel;

    @PostMapping({"/ecableErpPc/eccUnit/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return eccUnitModel.getObject(request);
    }

    @PostMapping({"/ecableErpPc/eccUnit/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return eccUnitModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/eccUnit/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return eccUnitModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/eccUnit/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return eccUnitModel.start(request);
    }

    @PostMapping({"/ecableErpPc/eccUnit/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return eccUnitModel.sort(request);
    }

    @PostMapping({"/ecableErpPc/eccUnit/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return eccUnitModel.delete(request);
    }
}
