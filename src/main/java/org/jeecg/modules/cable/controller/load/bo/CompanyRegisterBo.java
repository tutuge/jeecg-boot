package org.jeecg.modules.cable.controller.load.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRegisterBo {

    @Schema(description = "公司ID")
    @NotNull(message = "公司ID不得为空")
    private Integer ecCompanyId;
}
