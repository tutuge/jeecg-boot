package org.jeecg.modules.cable.controller.userDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "快递价格Base")
@Data
public class EcbuMoneyBaseBo {

    @Schema(description = "快递价格ID")
    @NotNull(message = "快递价格ID不得为空")
    private Integer ecbudmId;
}
