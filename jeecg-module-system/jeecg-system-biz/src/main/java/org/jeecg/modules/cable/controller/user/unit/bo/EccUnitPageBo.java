package org.jeecg.modules.cable.controller.user.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "默认单位")
public class EccUnitPageBo {

    @Schema(description = "是否启用")
    private Boolean startType;

    @NotNull(message = "每页数量")
    private Integer pageNumber;

    @NotNull(message = "页码")
    private Integer page;
}
