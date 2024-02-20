package org.jeecg.modules.cable.controller.userEcable.materials.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户材料")
@Data
public class EcbuMaterialsListBo {

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "材料ID")
    private Integer materialTypeId;

    @Schema(description = "是否启用")
    private Boolean startType;
}
