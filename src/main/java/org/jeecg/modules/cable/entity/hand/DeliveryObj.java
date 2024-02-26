package org.jeecg.modules.cable.entity.hand;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryObj {

    @Schema(description = "仓库与运输对应表ID")
    private Integer ecbudId;

    @Schema(description = "快递名称")
    private String deliveryName;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "是否默认选择")
    private Boolean dSelect = Boolean.FALSE;

    @Schema(description = "单价")
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Schema(description = "价格")
    private BigDecimal price = BigDecimal.ZERO;
}
