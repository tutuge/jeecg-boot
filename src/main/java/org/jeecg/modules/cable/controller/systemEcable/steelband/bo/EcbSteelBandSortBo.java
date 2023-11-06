package org.jeecg.modules.cable.controller.systemEcable.steelband.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "钢带")
@Data
public class EcbSteelBandSortBo {

    @NotNull(message = "钢带ID不得为空")
    @Schema(description = "钢带ID")
    private Integer ecbsbId;

    @Schema(description = "排序")
    private Integer sortId;
}
