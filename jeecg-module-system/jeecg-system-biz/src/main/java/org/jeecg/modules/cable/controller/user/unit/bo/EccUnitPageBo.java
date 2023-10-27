package org.jeecg.modules.cable.controller.user.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "默认单位")
public class EccUnitPageBo {

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "每页数量")
    @NotNull(message = "每页数量不得为空")
    private Integer pageSize;

    @Schema(description = "页码")
    @NotNull(message = "页码不得为空")
    private Integer pageNum;
}
