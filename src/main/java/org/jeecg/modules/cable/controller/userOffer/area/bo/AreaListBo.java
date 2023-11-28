package org.jeecg.modules.cable.controller.userOffer.area.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AreaListBo {

    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级Id不得为空")
    private Integer ecqulId;
}
