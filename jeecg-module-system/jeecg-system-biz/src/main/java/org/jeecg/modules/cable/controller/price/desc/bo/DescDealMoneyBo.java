package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "结构信息")
@Data
public class DescDealMoneyBo {
    @Schema(description = "inputId")
    private Integer ecuqiId;// inputId

    @Schema(description = "不开票的单价")
    private BigDecimal nbupsMoney;// no bill unit price single money 不开票的单价

    @Schema(description = "开票单价")
    private BigDecimal bupsMoney;// bill unit price single money 开票单价

    @Schema(description = "不开票小计")
    private BigDecimal nbupcMoney;// no bill unit price compute money 不开票小计

    @Schema(description = "开票小计")
    private BigDecimal bupcMoney;// bill unit price compute money 开票小计

    @Schema(description = "重量")
    private BigDecimal weight;// 重量

    @Schema(description = "税前单价是否手输")
    private Boolean unitPriceInput;// 税前单价是否手输

    @Schema(description = "税前单价")
    private BigDecimal unitPrice;// 税前单价

    @Schema(description = "1米的重量")
    private BigDecimal unitWeight;// 1米的重量

    @Schema(description = "木轴ID")
    private Integer ecbuaId;// 木轴ID

    @Schema(description = "木轴的数量")
    private Integer axleNumber;// 木轴的数量

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间
}
