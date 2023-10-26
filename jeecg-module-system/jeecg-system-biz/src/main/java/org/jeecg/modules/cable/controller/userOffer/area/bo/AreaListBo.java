package org.jeecg.modules.cable.controller.userOffer.area.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AreaListBo {

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID
}
