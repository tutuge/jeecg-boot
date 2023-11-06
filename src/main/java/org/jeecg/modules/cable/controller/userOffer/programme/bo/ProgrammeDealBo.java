package org.jeecg.modules.cable.controller.userOffer.programme.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "方案")
@Data
public class ProgrammeDealBo {

    @Schema(description = "主键ID")
    private Integer ecuopId;//主键ID

    @Schema(description = "方案名称")
    @NotBlank(message = "方案名称不得为空")
    private String programmeName;//方案名称


    @Schema(description = "芯数字符串")
    @NotBlank(message = "芯数不得为空")
    private String coreStr;//芯数字符串


    @Schema(description = "平方数")
    @NotBlank(message = "平方数不得为空")
    private String areaStr;//截面

    @Schema(description = "成本加点")
    @NotNull(message = "成本加点不得为空")
    private BigDecimal addPercent;// 成本加点

    @Schema(description = "最低单价")
    @NotNull(message = "最低单价不得为空")
    private BigDecimal minPrice;

    @Schema(description = "最高单价")
    @NotNull(message = "最高单价不得为空")
    private BigDecimal maxPrice;
}
