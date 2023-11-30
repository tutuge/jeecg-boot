package org.jeecg.modules.cable.controller.systemDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcbdMoneyDealBo {

    @Schema(description = "主键ID")
    private Integer ecbdmId;

    @Schema(description = "快递ID")
    @NotNull(message = "快递ID不得为空")
    private Integer ecbdId;

    @Schema(description = "省名称")
    @NotBlank(message = "省名称不得为空")
    private String provinceName;

    @Schema(description = "首重")
    private Integer firstWeight;

    @Schema(description = "首重金额")
    private BigDecimal firstMoney;

    @Schema(description = "续重金额")
    private BigDecimal continueMoney;
}
