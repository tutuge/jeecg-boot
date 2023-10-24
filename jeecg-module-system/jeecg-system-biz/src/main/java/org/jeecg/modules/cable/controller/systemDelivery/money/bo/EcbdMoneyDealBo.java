package org.jeecg.modules.cable.controller.systemDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcbdMoneyDealBo {

    @Schema(description = "主键ID")
    private Integer ecbdmId;//主键ID

    @Schema(description = "首重")
    private int firstWeight;//首重

    @Schema(description = "首重金额")
    private BigDecimal firstMoney;//首重金额

    @Schema(description = "续重金额")
    private BigDecimal continueMoney;//续重金额
}
