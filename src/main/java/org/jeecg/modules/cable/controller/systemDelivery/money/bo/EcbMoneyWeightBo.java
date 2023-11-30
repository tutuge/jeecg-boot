package org.jeecg.modules.cable.controller.systemDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "系统快递修改首重")
@Data
public class EcbMoneyWeightBo {

    @Schema(description = "快递价格ID")
    @NotNull(message = "快递价格ID不得为空")
    private Integer ecbdmId;

    @Schema(description = "首重")
    @NotNull(message = "首重不得为空")
    private Integer firstWeight;// 首重
}
