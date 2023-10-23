package org.jeecg.modules.cable.controller.user.profit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "利润列表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitListBo {

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "分页开始")
    private Integer startNum;

    @Schema(description = "每页数量")
    private Integer pageNumber;
}
