package org.jeecg.modules.cable.controller.systemQuality.parameter.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParameterDealBo {

    @Schema(description = "主键ID")
    private Integer ecqpId;

    @Schema(description = "质量等级ID")
    private Integer ecqlId;

    @Schema(description = "仓库ID")
    private Integer ecbsId;

    @Schema(description = "每米长度")
    private BigDecimal length;

    @Schema(description = "成本加点")
    private BigDecimal cost;

    @Schema(description = "备注")
    private String description;
}
