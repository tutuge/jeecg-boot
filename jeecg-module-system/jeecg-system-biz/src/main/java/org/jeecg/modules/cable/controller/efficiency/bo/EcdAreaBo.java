package org.jeecg.modules.cable.controller.efficiency.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "质量等级")
@Data
public class EcdAreaBo {

    @Schema(description = "质量等级ID")
    private Integer ecqulId;
}
