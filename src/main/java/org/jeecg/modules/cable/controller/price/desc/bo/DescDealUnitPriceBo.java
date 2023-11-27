package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "结构信息")
@Data
public class DescDealUnitPriceBo {
    
    @Schema(description = "报价单行数据ID")
    @NotNull(message = "报价单行数据ID不得为空")
    private Integer ecuqiId;// inputId

    @Schema(description = "税前单价是否手输")
    @NotNull(message = "税前单价是否手输不得为空")
    private Boolean unitPriceInput;

    @Schema(description = "税前单价")
    @NotNull(message = "税前单价不得为空")
    private BigDecimal unitPrice;// 税前单价
}
