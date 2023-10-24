package org.jeecg.modules.cable.controller.systemDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcbdPriceDealBo {


    @Schema(description = "主键ID")
    private Integer ecbdpId;//主键ID


    @Schema(description = "首重金额")
    private BigDecimal firstPrice;//首重金额

    @Schema(description = "金额")
    private BigDecimal price1;//金额
    @Schema(description = "金额")
    private BigDecimal price2;//金额
    @Schema(description = "金额")
    private BigDecimal price3;//金额
    @Schema(description = "金额")
    private BigDecimal price4;//金额
    @Schema(description = "金额")
    private BigDecimal price5;//金额
}
