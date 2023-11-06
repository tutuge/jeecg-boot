package org.jeecg.modules.cable.controller.systemCommon.axle.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "木轴Base")
@Data
public class EcbAxleBaseBo {

    @NotNull(message = "木轴ID不得为空")
    @Schema(description = "木轴ID")
    private Integer ecbaId;
}
