package org.jeecg.modules.cable.controller.load.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CompanyRegisterBo {

    @Schema(description = "公司ID")
    private Integer ecCompanyId;
}
