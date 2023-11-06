package org.jeecg.modules.cable.controller.systemCommon.axle.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "木轴bo")
@Data
public class EcbAxleBo {

    @Schema(description = "是否启用")
    private Boolean startType;

}
