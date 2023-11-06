package org.jeecg.modules.cable.controller.user.data.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcuDataObjectBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键不得为空")
    private Integer ecudId;// 主键ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用
}
