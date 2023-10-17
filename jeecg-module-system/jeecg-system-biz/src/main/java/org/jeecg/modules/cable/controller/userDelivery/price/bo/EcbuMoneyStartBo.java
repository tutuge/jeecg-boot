package org.jeecg.modules.cable.controller.userDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "快递价格")
@Data
public class EcbuMoneyStartBo {

    @NotNull(message = "快递价格ID不得为空")
    @Schema(description = "快递价格ID")
    private Integer ecbudmId;
}
