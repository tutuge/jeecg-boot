package org.jeecg.modules.cable.controller.quality.uarea.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取截面列表")
public class UAreaBo {


    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "主键ID")
    private Integer ecqulId;
}
