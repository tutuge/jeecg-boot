package org.jeecg.modules.cable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.api.CompanyBillApi;
import org.jeecg.modules.cable.entity.hand.CompanyBill;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "转换公司信息")
@RestController
@Slf4j
public class CompanyBillApiController {

    @Operation(summary = "转换公司信息")
    @PostMapping({"/ecableErpPc/api/getCompanyBill"})
    public CompanyBill getAddress(HttpServletRequest request) {
        String text = request.getParameter("text");
        return CompanyBillApi.getCompanyBill(text);
    }
}
