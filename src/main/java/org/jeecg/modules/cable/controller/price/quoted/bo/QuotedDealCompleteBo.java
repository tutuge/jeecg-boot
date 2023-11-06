package org.jeecg.modules.cable.controller.price.quoted.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(description = "报价单提交")
public class QuotedDealCompleteBo {

    @Schema(description = "主键ID")
    private Integer ecuqId;

    @Schema(description = "总金额")
    private BigDecimal totalMoney;//总金额

}
