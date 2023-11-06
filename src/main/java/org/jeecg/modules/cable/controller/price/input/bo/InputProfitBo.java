package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "利润")
@Data
public class InputProfitBo {

    @Schema(description = "主键ID")
    private Integer ecuqiId;

    @Schema(description = "利润是否手输")
    private Boolean profitInput;
}
