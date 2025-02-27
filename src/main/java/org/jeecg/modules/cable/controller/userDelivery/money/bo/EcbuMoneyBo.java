package org.jeecg.modules.cable.controller.userDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "快递价格")
@Data
public class EcbuMoneyBo {

    @Schema(description = "快递价格ID")
    private Integer ecbudmId;

    @Schema(description = "快递ID")
    @NotNull(message = "快递ID不得为空")
    private Integer ecbudId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

}
