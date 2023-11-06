package org.jeecg.modules.cable.controller.systemDelivery.money.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbdMoneySortBo {

    @Schema(description = "主键ID")
    private Integer ecbdmId;//主键ID

    @Schema(description = "序号")
    private Integer sortId;//序号

}
