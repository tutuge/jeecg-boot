package org.jeecg.modules.cable.controller.systemCommon.company.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "公司")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCompanySortBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecdcId;//主键ID


    @Schema(description = "序号")
    @NotNull(message = "排序不得为空")
    private Integer sortId;//序号

}
