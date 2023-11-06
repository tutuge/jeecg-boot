package org.jeecg.modules.cable.controller.systemDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbDeliverySortBo {


    @Schema(description = "主键ID")
    private Integer ecdcId;//主键ID


    @Schema(description = "序号")
    private Integer sortId;//序号
}
