package org.jeecg.modules.cable.controller.user.profit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "利润")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitSortBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键不得为空")
    private Integer ecpId;//主键ID

    @Schema(description = "序号")
    @NotNull(message = "序号不得为空")
    private Integer sortId;//序号
}
