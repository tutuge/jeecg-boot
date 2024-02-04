package org.jeecg.modules.cable.controller.systemEcable.material.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "系统基础材料名称列表请求参数")
@Data
public class EcbMaterialListBo {

    @Schema(description = "是否启用")
    private Boolean startType;
}
