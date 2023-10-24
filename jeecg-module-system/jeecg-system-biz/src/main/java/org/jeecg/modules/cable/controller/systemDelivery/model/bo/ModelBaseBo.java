package org.jeecg.modules.cable.controller.systemDelivery.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ModelBaseBo {

    @Schema(description = "主键ID")
    private Integer ecbdId;//主键ID
}
