package org.jeecg.modules.cable.controller.cert;

import org.jeecg.modules.cable.model.certs.EcuqCertsModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class EcuqCertsController {
    @Resource
    EcuqCertsModel ecuqCertsModel;

    @PostMapping({"/ecableErpPc/ecuqCerts/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuqCertsModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuqCertsModel.getObject(request);
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuqCertsModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecuqCertsModel.start(request);
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/defaultType"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        return ecuqCertsModel.defaultType(request);
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecuqCertsModel.delete(request);
    }
}
