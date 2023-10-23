package org.jeecg.modules.cable.controller.userOffer.core.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "芯数")
public class CoreBo {

    @Schema(description = "质量等级ID")
    private Integer ecqulId;
}
