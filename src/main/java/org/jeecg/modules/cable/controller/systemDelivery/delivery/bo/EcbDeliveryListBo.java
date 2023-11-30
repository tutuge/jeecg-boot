package org.jeecg.modules.cable.controller.systemDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcbDeliveryListBo {

    @Schema(description = "是否启用")
    private Boolean startType;

    @NotNull(message = "仓库ID不得为空")
    @Schema(description = "仓库ID")
    private Integer ecbsId;
}
