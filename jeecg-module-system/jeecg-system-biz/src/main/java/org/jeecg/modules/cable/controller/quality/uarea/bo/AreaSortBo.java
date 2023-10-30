package org.jeecg.modules.cable.controller.quality.uarea.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "编辑截面")
public class AreaSortBo {


    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecuaId;

    @Schema(description = "排序")
    @NotNull(message = "排序不得为空")
    private Integer sortId;
}
