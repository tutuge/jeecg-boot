package org.jeecg.modules.cable.controller.quality.uarea.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "获取截面列表")
public class UAreaBo {


    @Schema(description = "是否启用")
    private Boolean startType = true;

    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不得为空")
    private Integer ecqulId;
}
