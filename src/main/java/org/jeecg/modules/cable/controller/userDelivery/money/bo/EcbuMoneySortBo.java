package org.jeecg.modules.cable.controller.userDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "快递价格Base")
@Data
public class EcbuMoneySortBo {

    @Schema(description = "快递价格ID")
    @NotNull(message = "快递价格ID不得为空")
    private Integer ecbudmId;

    @Schema(description = "排序")
    @NotNull(message = "排序不得为空")
    private Integer sortId;// 序号
}
