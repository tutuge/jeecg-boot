package org.jeecg.modules.cable.controller.systemCommon.store.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcbStoreSortBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecbsId;

    @Schema(description = "排序")
    @NotNull(message = "排序为空")
    private Integer sortId;
}
