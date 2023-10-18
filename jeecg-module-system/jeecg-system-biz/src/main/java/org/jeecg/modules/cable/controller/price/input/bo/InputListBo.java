package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "通过ID获取EcuqInput")
@Data
public class InputListBo {

    @Schema(description = "报价单ID")
    private Integer ecuqId;//报价单ID

    @Schema(description = "快递ID")
    private Integer ecbudId;//快递ID
}
