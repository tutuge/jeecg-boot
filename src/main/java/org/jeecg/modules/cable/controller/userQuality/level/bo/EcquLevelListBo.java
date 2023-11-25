package org.jeecg.modules.cable.controller.userQuality.level.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcquLevelListBo {

    @Schema(description = "是否启用")
    private Boolean startType;
}
