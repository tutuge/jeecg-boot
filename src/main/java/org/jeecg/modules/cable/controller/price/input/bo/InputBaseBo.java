package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "通过ID获取EcuqInput")
@Data
public class InputBaseBo {

    @Schema(description = "报价单明细ID")
    @NotNull(message = "报价单明细ID不得为空")
    private Integer ecuqiId;
}
