package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "通过ID获取EcuqInput")
@Data
public class InputListBo {

    @Schema(description = "报价单ID")
    @NotNull(message = "报价单ID不得为空")
    private Integer ecuqId;//报价单ID

    @Schema(description = "快递ID")
    @NotNull(message = "快递ID不得为空")
    private Integer ecbudId;//快递ID
}
