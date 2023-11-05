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

    @Schema(description = "快递ID")
    private Integer ecbudId;//快递ID

    @Schema(description = "快递名称")
    private String deliveryName;//快递名称

    @Schema(description = "备注")
    private String description;//备注

    @Schema(description = "是否默认选择")
    private Boolean dSelect;//是否默认选择

    @Schema(description = "单价")
    private BigDecimal unitPrice;//单价

    @Schema(description = "价格")
    private BigDecimal price;//价格
}
