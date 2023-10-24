package org.jeecg.modules.cable.controller.systemCommon.company.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "公司")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCompanyDealBo {

    @Schema(description = "主键ID")
    private Integer ecdcId;//主键ID

    @Schema(description = "公司简称")
    private String abbreviation;//公司简称

    @Schema(description = "公司全称")
    private String fullName;//公司全称

    @Schema(description = "发票税点类型")
    private Integer billPercentType;//发票税点类型

    @Schema(description = "备注")
    private String description;//备注
}
