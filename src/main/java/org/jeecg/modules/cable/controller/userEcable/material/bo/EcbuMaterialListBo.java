package org.jeecg.modules.cable.controller.userEcable.material.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户基础材料名称列表请求参数")
@Data
public class EcbuMaterialListBo {

    @Schema(description = "是否启用")
    private Boolean startType;
}
