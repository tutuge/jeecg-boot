package org.jeecg.modules.cable.controller.user.udesc.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "备注管理")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuDescPageBo {


    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "每页数量")
    private Integer pageSize;

    @Schema(description = "页码")
    private Integer pageNum;
}
