package org.jeecg.modules.cable.controller.systemDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcbDeliveryDealBo {

    @Schema(description = "主键ID")
    private Integer ecbdId;

    @Schema(description = "仓库ID")
    private Integer ecbsId;

    @Schema(description = "快递类型")
    private Integer deliveryType;

    @Schema(description = "快递名称")
    private String deliveryName;

    @Schema(description = "备注")
    private String description;
}
