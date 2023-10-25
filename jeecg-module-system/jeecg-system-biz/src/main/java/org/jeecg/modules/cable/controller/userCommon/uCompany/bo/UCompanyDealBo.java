package org.jeecg.modules.cable.controller.userCommon.uCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "编辑平台公司")
public class UCompanyDealBo {

    @Schema(description = "主键ID")
    private Integer ecducId;//主键ID


    @Schema(description = "公司简称")
    private String abbreviation;//公司简称

    @Schema(description = "公司全称")
    private String fullName;//公司全称

    @Schema(description = "发票税点类型")
    private Integer billPercentType;//发票税点类型

    @Schema(description = "备注")
    private String description;//备注
}
