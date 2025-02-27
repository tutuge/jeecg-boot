package org.jeecg.modules.cable.controller.systemCommon.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "单位长度")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcblUnitBaseBo {
    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecbluId;
}
