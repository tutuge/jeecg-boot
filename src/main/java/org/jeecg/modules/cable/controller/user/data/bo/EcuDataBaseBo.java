package org.jeecg.modules.cable.controller.user.data.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcuDataBaseBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键不得为空")
    private Integer ecudId;// 主键ID
}
