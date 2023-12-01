package org.jeecg.modules.cable.controller.user.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "默认单位修改bo")
public class EccUnitDealBo {

    @Schema(description = "主键ID")
    private Integer eccuId;

    @Schema(description = "型号")
    @NotBlank(message = "型号不得为空")
    private String ecusmId;

    @Schema(description = "型号")
    private String silkName;

    @Schema(description = "单位")
    @NotNull(message = "单位Id不得为空")
    private Integer ecbuluId;

    @Schema(description = "备注")
    @NotBlank(message = "备注不得为空")
    private String description;
}
