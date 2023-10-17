package org.jeecg.modules.cable.controller.userDelivery.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "表头")
@Data
public class EcbudModelBo {

    @Schema(description = "主键ID")
    private Integer ecbudId;
}
