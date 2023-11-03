package org.jeecg.modules.cable.controller.cert;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.cert.bo.CertsBo;
import org.jeecg.modules.cable.entity.certs.EcuqCerts;
import org.jeecg.modules.cable.model.certs.EcuqCertsModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class EcuqCertsController {
    @Resource
    EcuqCertsModel ecuqCertsModel;

    @PostMapping({"/ecableErpPc/ecuqCerts/getList"})
    public Result<Map<String, Object>> getList(@RequestBody CertsBo certsBo) {
        return Result.OK(ecuqCertsModel.getList(certsBo));
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/getObject"})
    public Result<EcuqCerts> getObject() {
        return Result.ok(ecuqCertsModel.getObject());
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecuqCertsModel.deal(request));
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecuqCertsModel.start(request));
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/defaultType"})
    public Result<String> defaultType(HttpServletRequest request) {
        return Result.ok(ecuqCertsModel.defaultType(request));
    }

    @PostMapping({"/ecableErpPc/ecuqCerts/delete"})
    public Result<?> delete(HttpServletRequest request) {
        ecuqCertsModel.delete(request);
        return Result.ok();
    }
}
