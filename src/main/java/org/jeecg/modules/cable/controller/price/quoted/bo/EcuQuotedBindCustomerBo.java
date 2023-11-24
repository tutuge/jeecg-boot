package org.jeecg.modules.cable.controller.price.quoted.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description ="报价单绑定客户" )
public class EcuQuotedBindCustomerBo {

    @Schema(description = "报价单ID")
    @NotNull(message = "报价单ID不得为空")
    private Integer ecuqId;


    @Schema(description = "客户ID")
    @NotNull(message = "客户ID不得为空")
    private Integer eccuId;
}
