package org.jeecg.modules.cable.controller.systemDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbdPriceListBo {


    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用
}
