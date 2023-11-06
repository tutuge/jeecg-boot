package org.jeecg.modules.cable.controller.userDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "物流信息")
@Data
public class EcbuDeliverySortBo {

    @Schema(description = "快递ID")
    @NotNull(message = "快递ID不得为空")
    private Integer ecbudId;

    @Schema(description = "排序")
    @NotNull(message = "排序不得为空")
    private Integer sortId;

}
