package org.jeecg.modules.cable.controller.userQuality.parameter.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParameterDealBo {

    @Schema(description = "主键ID")
    private Integer ecqupId;// 主键ID


    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "仓库ID")
    private Integer ecbusId;// 仓库ID

    @Schema(description = "每米长度")
    private BigDecimal length;// 每米长度

    @Schema(description = "成本加点")
    private BigDecimal cost;// 成本加点

    @Schema(description = "备注")
    private String description;// 备注
}
