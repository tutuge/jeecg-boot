package org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "平台")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbPlatformCompanyListBo {

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用
}
