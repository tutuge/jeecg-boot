package org.jeecg.modules.cable.controller.userCommon.platformCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "获取平台公司")
public class CompanyBaseBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecbupId;
}
