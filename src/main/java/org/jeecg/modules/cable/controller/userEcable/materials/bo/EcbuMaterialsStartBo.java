package org.jeecg.modules.cable.controller.userEcable.materials.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "材料")
@Data
public class EcbuMaterialsStartBo {

    @NotNull(message = "材料ID不得为空")
    @Schema(description = "材料ID")
    private Integer id;
}
