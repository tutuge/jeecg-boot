package org.jeecg.modules.cable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.api.CompanyBillApi;
import org.jeecg.modules.cable.controller.api.bo.AddressBo;
import org.jeecg.modules.cable.entity.hand.CompanyBill;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "转换公司信息")
@RestController
@Slf4j
public class CompanyBillApiController {

    @Operation(summary = "转换公司信息")
    @PostMapping({"/ecableErpPc/api/getCompanyBill"})
    public CompanyBill getAddress(@RequestBody AddressBo bo) {
        String text = bo.getText();
        return CompanyBillApi.getCompanyBill(text);
    }
}
