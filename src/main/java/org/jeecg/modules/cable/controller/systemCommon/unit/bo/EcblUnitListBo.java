package org.jeecg.modules.cable.controller.systemCommon.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "长度单位")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcblUnitListBo {
    @Schema(description = "是否启用")
    private Boolean startType;
}
