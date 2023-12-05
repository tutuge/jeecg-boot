package org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "平台")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbPlatformCompanyBaseBo {
    @Schema(description = "主键ID")
    @NotNull(message = "主键不得为空")
    private Integer ecbpId;// 主键ID

}
