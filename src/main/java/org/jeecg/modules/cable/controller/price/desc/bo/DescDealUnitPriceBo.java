package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "结构信息")
@Data
public class DescDealUnitPriceBo {
    @Schema(description = "inputId")
    private Integer ecuqiId;// inputId

    @Schema(description = "税前单价是否手输")
    private Boolean unitPriceInput;// 税前单价是否手输

    @Schema(description = "税前单价")
    private BigDecimal unitPrice;// 税前单价
}
