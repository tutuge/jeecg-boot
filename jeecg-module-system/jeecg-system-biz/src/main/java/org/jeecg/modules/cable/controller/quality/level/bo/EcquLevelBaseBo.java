package org.jeecg.modules.cable.controller.quality.level.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcquLevelBaseBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecqulId;// 主键ID
}
