package org.jeecg.modules.cable.controller.quality.uarea.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "编辑截面")
public class AreaSortBo {


    @Schema(description = "主键ID")
    private Integer ecuaId;

    @Schema(description = "排序")
    private Integer sortId;
}
