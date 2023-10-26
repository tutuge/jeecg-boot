package org.jeecg.modules.cable.controller.systemDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbDeliveryDealBo {

    @Schema(description = "主键ID")
    private Integer ecdcId;// 主键ID

    @Schema(description = "快递类型")
    private Integer deliveryType;// 快递类型

    @Schema(description = "快递名称")
    private String deliveryName;// 快递名称

    @Schema(description = "备注")
    private String description;// 备注
}
