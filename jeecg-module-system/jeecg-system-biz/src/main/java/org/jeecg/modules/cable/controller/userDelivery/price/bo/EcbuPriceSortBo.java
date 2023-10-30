package org.jeecg.modules.cable.controller.userDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "快递价格Base")
@Data
public class EcbuPriceSortBo {

    @NotNull(message = "快快运价格ID不得为空")
    @Schema(description = "快快运价格ID")
    private Integer ecbudpId;

    @Schema(description = "排序")
    @NotNull(message = "排序不得为空")
    private Integer sortId;// 序号
}
