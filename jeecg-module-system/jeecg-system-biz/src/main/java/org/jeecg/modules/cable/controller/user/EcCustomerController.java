package org.jeecg.modules.cable.controller.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.model.user.EcCustomerModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class EcCustomerController {
    @Resource
    EcCustomerModel ecCustomerModel;

    @PostMapping({"/ecableErpPc/ecCustomer/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecCustomerModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecCustomer/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecCustomerModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecCustomer/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecCustomerModel.getObject(request);
    }
}
