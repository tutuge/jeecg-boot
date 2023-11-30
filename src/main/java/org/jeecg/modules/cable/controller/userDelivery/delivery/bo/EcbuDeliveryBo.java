package org.jeecg.modules.cable.controller.userDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "物流信息")
@Data
public class EcbuDeliveryBo {

    @NotNull(message = "仓库ID不得为空")
    @Schema(description = "仓库ID")
    private Integer ecbusId;

    @Schema(description = "快递ID")
    private Integer ecbudId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

}
