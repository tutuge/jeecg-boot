package org.jeecg.modules.cable.controller.userEcable.material.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户基础材料名称")
@Data
public class EcbuMaterialSortBo {

    @NotNull(message = "用户基础材料名称ID不得为空")
    @Schema(description = "用户基础材料名称ID")
    private Integer id;

    @Schema(description = "排序")
    private Integer sortId;

}
