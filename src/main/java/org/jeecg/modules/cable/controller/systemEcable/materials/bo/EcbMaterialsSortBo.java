package org.jeecg.modules.cable.controller.systemEcable.materials.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "导体")
@Data
public class EcbMaterialsSortBo {

    @NotNull(message = "导体ID不得为空")
    @Schema(description = "导体ID")
    private Integer ecbcId;

    @Schema(description = "排序")
    private Integer sortId;

}
