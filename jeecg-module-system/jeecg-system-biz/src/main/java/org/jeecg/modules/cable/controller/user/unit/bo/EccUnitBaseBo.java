package org.jeecg.modules.cable.controller.user.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "默认单位")
public class EccUnitBaseBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer eccuId;
}
