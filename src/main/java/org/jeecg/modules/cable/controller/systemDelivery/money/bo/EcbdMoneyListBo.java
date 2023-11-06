package org.jeecg.modules.cable.controller.systemDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbdMoneyListBo {

    @Schema(description = "主键ID")
    private Integer ecbdmId;//主键ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用


}
