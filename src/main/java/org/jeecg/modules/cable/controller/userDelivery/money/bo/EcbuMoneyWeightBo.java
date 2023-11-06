package org.jeecg.modules.cable.controller.userDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "快递首重Base")
@Data
public class EcbuMoneyWeightBo {

    @Schema(description = "快递价格ID")
    @NotNull(message = "快递价格ID不得为空")
    private Integer ecbudmId;

    @Schema(description = "首重")
    @NotNull(message = "首重不得为空")
    private Integer firstWeight;// 首重
}
