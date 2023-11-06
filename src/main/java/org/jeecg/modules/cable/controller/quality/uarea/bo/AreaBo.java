package org.jeecg.modules.cable.controller.quality.uarea.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取截面详情")
public class AreaBo {


    @Schema(description = "主键ID")
    private Integer ecuaId;
}
