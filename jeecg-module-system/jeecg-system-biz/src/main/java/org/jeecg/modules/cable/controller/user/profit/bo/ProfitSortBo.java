package org.jeecg.modules.cable.controller.user.profit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "利润")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitSortBo {

    @Schema(description = "主键ID")
    private Integer ecpId;//主键ID

    @Schema(description = "序号")
    private Integer sortId;//序号
}
