package org.jeecg.modules.cable.controller.price.quoted.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(description = "报价单提交")
public class QuotedDealMoneyPassBo {

    @Schema(description = "主键ID")
    private Integer ecuqId;

    @Schema(description = "不开发票总计")
    private BigDecimal nbuptMoney;//不开发票总计

    @Schema(description = "开发票总计")
    BigDecimal buptMoney;//开发票总计

}
