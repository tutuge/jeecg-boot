package org.jeecg.modules.cable.controller.userQuality.uarea.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "编辑截面")
public class EcuAreaBo {

    @Schema(description = "截面")
    @NotBlank(message = "截面不得为空")
    private String areaStr;//截面

    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不得为空")
    private Integer ecqulId;

    @Schema(description = "主键ID")
    private Integer ecuaId;
}
