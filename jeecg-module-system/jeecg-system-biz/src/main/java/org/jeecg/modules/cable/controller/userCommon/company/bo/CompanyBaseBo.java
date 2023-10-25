package org.jeecg.modules.cable.controller.userCommon.company.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取平台公司")
public class CompanyBaseBo {

    @Schema(description = "主键ID")
    private Integer ecbupId;//主键ID
}
