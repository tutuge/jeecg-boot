package org.jeecg.modules.cable.controller.user.customer;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.customer.vo.CustomerVo;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.model.user.EcCustomerModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EcCustomerController {
    @Resource
    EcCustomerModel ecCustomerModel;

    @PostMapping({"/ecableErpPc/ecCustomer/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecCustomerModel.deal(request));
    }

    @PostMapping({"/ecableErpPc/ecCustomer/getList"})
    public Result<CustomerVo> getList(HttpServletRequest request) {
        return Result.ok(ecCustomerModel.getList(request));
    }

    @PostMapping({"/ecableErpPc/ecCustomer/getObject"})
    public Result<EcCustomer> getObject(HttpServletRequest request) {
        return Result.ok(ecCustomerModel.getObject(request));
    }
}
