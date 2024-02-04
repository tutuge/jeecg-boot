package org.jeecg.modules.cable.controller.systemEcable.material.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "系统基础材料名称")
@Data
public class EcbMaterialBaseBo {

    @NotNull(message = "系统基础材料名称ID不得为空")
    @Schema(description = "系统基础材料名称ID")
    private Integer id;
}
