package org.jeecg.modules.cable.controller.userDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "默认物流")
@Data
public class EcbudDeliveryBo {

    @Schema(description = "报价单ID")
    @NotNull(message = "报价单ID不得为空")
    private Integer ecuqId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    @NotNull(message = "排序不得为空")
    private Integer sortId;//序号

}
