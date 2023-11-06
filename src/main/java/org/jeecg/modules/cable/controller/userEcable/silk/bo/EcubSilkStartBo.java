package org.jeecg.modules.cable.controller.userEcable.silk.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "丝型号")
@Data
public class EcubSilkStartBo {


    @Schema(description = "丝型号ID")
    private Integer ecbsbId;

    @Schema(description = "丝型号名称")
    private String silkName;
}
