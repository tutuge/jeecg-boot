package org.jeecg.modules.cable.controller.systemEcable.silk.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "型号")
@Data
public class EcbSilkSortBo {

    @NotNull(message = "型号ID不得为空")
    @Schema(description = "型号ID")
    private Integer ecsId;


    @Schema(description = "排序")
    private Integer sortId;
}
