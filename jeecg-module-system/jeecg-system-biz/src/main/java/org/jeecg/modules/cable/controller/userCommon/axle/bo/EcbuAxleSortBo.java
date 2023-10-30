package org.jeecg.modules.cable.controller.userCommon.axle.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "木轴")
@Data
public class EcbuAxleSortBo {

    @NotNull(message = "木轴ID不得为空")
    @Schema(description = "木轴ID")
    private Integer ecbuaId;

    @NotNull(message = "木轴排序不得为空")
    @Schema(description = "排序")
    private Integer sortId;// 序号
}
