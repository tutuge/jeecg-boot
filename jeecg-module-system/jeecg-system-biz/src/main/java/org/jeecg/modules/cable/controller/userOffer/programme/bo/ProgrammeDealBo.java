package org.jeecg.modules.cable.controller.userOffer.programme.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "方案")
@Data
public class ProgrammeDealBo {

    @Schema(description = "主键ID")
    private Integer ecuopId;//主键ID

    @Schema(description = "方案名称")
    private String programmeName;//方案名称


    @Schema(description = "芯数字符串")
    private String coreStr;//芯数字符串


   @Schema(description = "截面")
   private String areaStr;//截面

    @Schema(description = "成本加点")
    private BigDecimal addPercent;// 成本加点
}
