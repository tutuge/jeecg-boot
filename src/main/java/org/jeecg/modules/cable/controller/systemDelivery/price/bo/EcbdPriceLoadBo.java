package org.jeecg.modules.cable.controller.systemDelivery.price.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbdPriceLoadBo {


    @Schema(description = "快递ID")
    private Integer ecbdId;//主键ID
}
