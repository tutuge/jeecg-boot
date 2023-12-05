package org.jeecg.modules.cable.controller.userCommon.platformCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "获取平台公司")
public class CompanyDealBo {

    @Schema(description = "主键ID")
    private Integer ecbupId;//主键ID

    @Schema(description = "平台类型ID")
    private Integer platformId;//平台类型ID

    @Schema(description = "平台名称")
    private String pcName;

    @Schema(description = "平台税点")
    private BigDecimal pcPercent;

    @Schema(description = "备注")
    private String description;

}
