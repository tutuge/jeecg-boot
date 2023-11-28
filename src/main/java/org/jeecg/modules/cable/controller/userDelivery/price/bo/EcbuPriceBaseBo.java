package org.jeecg.modules.cable.controller.userDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "快运价格信息Base")
@Data
public class EcbuPriceBaseBo {

    @NotNull(message = "快运价格ID不得为空")
    @Schema(description = "快运价格ID")
    private Integer ecbudpId;
}
