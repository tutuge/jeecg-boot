package org.jeecg.modules.cable.controller.systemDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcbdPriceDealBo {

    @Schema(description = "主键ID")
    private Integer ecbdpId;

    @Schema(description = "快递ID")
    @NotNull(message = "快递ID不得为空")
    private Integer ecbdId;

    @Schema(description = "省名称")
    @NotBlank(message = "省名称不得为空")
    private String provinceName;

    @Schema(description = "首重金额")
    private BigDecimal firstPrice;

    @Schema(description = "金额")
    private BigDecimal price1;

    @Schema(description = "金额")
    private BigDecimal price2;

    @Schema(description = "金额")
    private BigDecimal price3;

    @Schema(description = "金额")
    private BigDecimal price4;

    @Schema(description = "金额")
    private BigDecimal price5;
}
