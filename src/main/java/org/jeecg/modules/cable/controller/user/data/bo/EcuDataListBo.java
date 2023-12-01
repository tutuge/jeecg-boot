package org.jeecg.modules.cable.controller.user.data.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcuDataListBo {

    @Schema(description = "主键ID")
    private Integer ecudId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "每页数量")
    @NotNull(message = "每页数量不得为空")
    private Integer pageSize;

    @Schema(description = "页码")
    @NotNull(message = "页码不得为空")
    private Integer pageNo;
}
