package org.jeecg.modules.cable.controller.quality.uarea.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "编辑截面")
public class EcuAreaBo {

    @Schema(description = "截面")
    private String areaStr;//截面

    @Schema(description = "质量等级ID")
    private Integer ecqulId;

    @Schema(description = "主键ID")
    private Integer ecuaId;
}
