package org.jeecg.modules.cable.controller.userEcable.materials.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户材料")
@Data
public class EcbuMaterialsListBo {

    @Schema(description = "ID")
    @NotNull(message = "ID不得为空")
    private Integer id;

    @Schema(description = "是否启用")
    private Boolean startType;
}
