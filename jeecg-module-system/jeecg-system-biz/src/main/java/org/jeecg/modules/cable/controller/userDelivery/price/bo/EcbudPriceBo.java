package org.jeecg.modules.cable.controller.userDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "物流信息")
@Data
public class EcbudPriceBo {

    @Schema(description = "物流信息ID")
    private Integer ecbudpId;

    @Schema(description = "快递ID")
    private Integer ecbudId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;//序号

}
