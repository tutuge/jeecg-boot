package org.jeecg.modules.cable.controller.systemDelivery.delivery.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcbDeliverySortBo {


    @Schema(description = "主键ID")
    @NotNull(message = "主键不得为空")
    private Integer ecdcId;//主键ID


    @Schema(description = "序号")
    @NotNull(message = "序号不得为空")
    private Integer sortId;//序号
}
