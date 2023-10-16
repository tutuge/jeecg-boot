package org.jeecg.modules.cable.controller.api;

import org.jeecg.modules.cable.api.CompanyBillApi;
import org.jeecg.modules.cable.entity.hand.CompanyBill;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CompanyBillApiController {
    @PostMapping({"/ecableErpPc/api/getCompanyBill"})
    public CompanyBill getAddress(HttpServletRequest request) {
        String text = request.getParameter("text");
        return CompanyBillApi.getCompanyBill(text);
    }
}
