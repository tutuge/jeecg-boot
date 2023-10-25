package org.jeecg.modules.cable.controller.userCommon.attribute.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AttributeBo {

    @Schema(description = "铜利润")
    private Boolean pcShowOrHide;


    @Schema(description = "铝利润")
    private Boolean paShowOrHide;

    @Schema(description = "运费加点")
    private Boolean dmShowOrHide;
}
