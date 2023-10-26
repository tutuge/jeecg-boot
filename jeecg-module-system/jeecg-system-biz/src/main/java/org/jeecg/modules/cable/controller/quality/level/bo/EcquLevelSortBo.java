package org.jeecg.modules.cable.controller.quality.level.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcquLevelSortBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecqulId;// 主键ID

    @Schema(description = "序号")
    @NotNull(message = "排序不得为空")
    private Integer sortId;// 序号
}
