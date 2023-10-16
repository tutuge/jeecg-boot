package org.jeecg.modules.cable.controller.user;

import org.jeecg.modules.cable.model.user.EcuDescModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuDescController {
    @Resource
    EcuDescModel ecuDescModel;

    @PostMapping({"/ecableErpPc/ecuDesc/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuDescModel.getObject(request);
    }

    @PostMapping({"/ecableErpPc/ecuDesc/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuDescModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecuDesc/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuDescModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecuDesc/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecuDescModel.start(request);
    }

    @PostMapping({"/ecableErpPc/ecuDesc/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecuDescModel.sort(request);
    }

    @PostMapping({"/ecableErpPc/ecuDesc/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecuDescModel.delete(request);
    }

    //设置默认
    @PostMapping({"/ecableErpPc/ecuDesc/defaultType"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        return ecuDescModel.defaultType(request);
    }
}
