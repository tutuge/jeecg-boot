package org.jeecg.modules.cable.controller.user.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.customer.bo.EcCustomerDealBo;
import org.jeecg.modules.cable.controller.user.customer.vo.CustomerVo;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.model.user.EcCustomerModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "客户信息", description = "客户信息",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "999", parseValue = true)})})
@RestController
@Slf4j
public class EcCustomerController {
    @Resource
    EcCustomerModel ecCustomerModel;

    @Operation(summary = "编辑")
    @PostMapping({"/ecableErpPc/ecCustomer/deal"})
    public Result<String> deal(@RequestBody EcCustomerDealBo bo) {
        return Result.ok(ecCustomerModel.deal(bo));
    }

    @Operation(summary = "列表")
    @PostMapping({"/ecableErpPc/ecCustomer/getList"})
    public Result<CustomerVo> getList(@RequestBody EcCustomerDealBo bo) {
        return Result.ok(ecCustomerModel.getList(bo));
    }

    @Operation(summary = "获取对象")
    @PostMapping({"/ecableErpPc/ecCustomer/getObject"})
    public Result<EcCustomer> getObject(HttpServletRequest request) {
        return Result.ok(ecCustomerModel.getObject(request));
    }
}
