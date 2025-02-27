package org.jeecg.modules.cable.controller.userCommon.platformCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取平台公司列表")
public class CompanyListBo {

    @Schema(description = "是否启用")
    private Boolean startType;
}
