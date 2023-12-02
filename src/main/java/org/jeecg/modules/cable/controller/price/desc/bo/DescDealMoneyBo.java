package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "报价单明细中的金额信息")
@Data
public class DescDealMoneyBo {
    @Schema(description = "报价单明细ID")
    @NotNull(message = "报价单明细ID不得为空")
    private Integer ecuqiId;

    @Schema(description = "不开票的单价")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "9999999999.0", inclusive = false)
    private BigDecimal nbupsMoney;// no bill unit price single money 不开票的单价

    @Schema(description = "开票单价")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "9999999999.0", inclusive = false)
    private BigDecimal bupsMoney;// bill unit price single money 开票单价

    @Schema(description = "不开票小计")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "9999999999.0", inclusive = false)
    private BigDecimal nbupcMoney;// no bill unit price compute money 不开票小计

    @Schema(description = "开票小计")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "9999999999.0", inclusive = false)
    private BigDecimal bupcMoney;// bill unit price compute money 开票小计

    @Schema(description = "重量")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "9999999999.0", inclusive = false)
    private BigDecimal weight;// 重量

    @Schema(description = "税前单价是否手输")
    private Boolean unitPriceInput;// 税前单价是否手输

    @Schema(description = "税前单价")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "9999999999.0", inclusive = false)
    private BigDecimal unitPrice;// 税前单价

    @Schema(description = "1米的重量")
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "9999999999.0", inclusive = false)
    private BigDecimal unitWeight;// 1米的重量

    @Schema(description = "木轴ID")
    private Integer ecbuaId;

    @Schema(description = "木轴的数量")
    private Integer axleNumber;

    @Schema(description = "添加时间")
    private Date addTime;
}
