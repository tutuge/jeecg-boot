package org.jeecg.modules.cable.controller.userCommon.uCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取平台公司")
public class UCompanySortBo {

    @Schema(description = "主键ID")
    private Integer ecducId;//主键ID

    @Schema(description = "序号")
    private Integer sortId;//序号
}
