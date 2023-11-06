package org.jeecg.modules.cable.controller.userCommon.axle.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "木轴")
@Data
public class EcbuAxleBo {

    @Schema(description = "是否启用")
    private Boolean startType;

}
