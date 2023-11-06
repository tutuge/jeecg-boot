package org.jeecg.modules.cable.controller.quality.parameter.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParameterBaseBo {


    @Schema(description = "质量参数ID")
    @NotNull(message = "质量参数ID不得为空")
    private Integer ecqupId;
}
