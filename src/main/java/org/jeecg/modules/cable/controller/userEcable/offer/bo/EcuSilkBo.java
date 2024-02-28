package org.jeecg.modules.cable.controller.userEcable.offer.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户型号类型")
@Data
public class EcuSilkBo {

    @Schema(description = "用户型号类型ID")
    private Integer ecqulId;
}
