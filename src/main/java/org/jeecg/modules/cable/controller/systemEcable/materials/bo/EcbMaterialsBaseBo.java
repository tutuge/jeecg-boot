package org.jeecg.modules.cable.controller.systemEcable.materials.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "材料")
@Data
public class EcbMaterialsBaseBo {

    @NotNull(message = "材料ID不得为空")
    @Schema(description = "材料ID")
    private Integer id;
}
