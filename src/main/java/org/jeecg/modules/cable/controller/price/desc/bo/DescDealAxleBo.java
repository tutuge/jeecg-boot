package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "结构信息")
@Data
public class DescDealAxleBo {

    @Schema(description = "inputId")
    @NotNull(message = "报价行数据ID不得为空")
    private Integer ecuqiId;// inputId

    @Schema(description = "1米的重量")
    private BigDecimal unitWeight;// 1米的重量

    @Schema(description = "木轴ID")
    @NotNull(message = "木轴ID不得为空")
    private Integer ecbuaId;// 木轴ID

    @Schema(description = "木轴的数量")
    private Integer axleNumber;// 木轴的数量
}
