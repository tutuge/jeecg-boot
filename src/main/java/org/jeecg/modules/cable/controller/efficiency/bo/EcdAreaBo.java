package org.jeecg.modules.cable.controller.efficiency.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "质量等级")
@Data
public class EcdAreaBo {

    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不得为空")
    private Integer ecqulId;
}
