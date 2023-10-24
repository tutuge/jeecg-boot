package org.jeecg.modules.cable.controller.systemDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbDeliveryListBo {

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用
}
