package org.jeecg.modules.cable.controller.user.data.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcuDataDealBo {

    @Schema(description = "主键ID")
    private Integer ecudId;


    @Schema(description = "型号ID")
    @NotNull(message = "型号ID不得为空")
    private Integer ecusmId;

    @Schema(description = "仓库ID")
    @NotNull(message = "仓库ID不得为空")
    private Integer ecbusId;
}
