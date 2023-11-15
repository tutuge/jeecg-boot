package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "利润")
@Data
public class InputProfitBo {

    @Schema(description = "主键ID")
    @NotNull(message = "报价单明细ID不得为空")
    private Integer ecuqiId;

    @Schema(description = "利润是否手输")
    @NotNull(message = "利润是否手输不得为空")
    private Boolean profitInput;
}
