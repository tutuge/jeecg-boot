package org.jeecg.modules.cable.controller.systemDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcbdPriceListBo {

    @Schema(description = "快递ID")
    @NotNull(message = "快递ID不得为空")
    private Integer ecbdId;

    @Schema(description = "是否启用")
    private Boolean startType;
}
