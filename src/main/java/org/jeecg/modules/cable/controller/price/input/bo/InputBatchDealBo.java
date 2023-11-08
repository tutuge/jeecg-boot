package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "页面显示列表编辑提交")
@Data
public class InputBatchDealBo {

    @Schema(description = "报价单ID")
    @NotNull(message = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "发票类型")
    @NotNull(message = "发票类型")
    private Integer priceType;
}
