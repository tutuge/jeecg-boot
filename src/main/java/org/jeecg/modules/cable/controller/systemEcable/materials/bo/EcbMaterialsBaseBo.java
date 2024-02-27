package org.jeecg.modules.cable.controller.systemEcable.materials.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "材料")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbMaterialsBaseBo {

    @NotNull(message = "材料ID不得为空")
    @Schema(description = "材料ID")
    private Integer id;
}
