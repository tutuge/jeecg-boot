package org.jeecg.modules.cable.controller.userCommon.axle.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "木轴")
@Data
public class EcbuAxleBo {

    @NotNull(message = "木轴ID不得为空")
    @Schema(description = "木轴ID")
    private Integer ecbuaId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;//序号

}
