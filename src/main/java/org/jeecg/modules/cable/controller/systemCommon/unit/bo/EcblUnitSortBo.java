package org.jeecg.modules.cable.controller.systemCommon.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "长度单位")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcblUnitSortBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecbluId;// 主键ID

    @Schema(description = "排序")
    @NotNull(message = "排序不得为空")
    private Integer sortId;
}
