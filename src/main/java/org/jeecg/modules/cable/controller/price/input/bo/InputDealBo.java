package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "页面显示列表编辑提交")
@Data
public class InputDealBo {

    @Schema(description = "主键ID")
    private Integer ecuqiId;

    @Schema(description = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;


    @Schema(description = "发票类型")
    private Integer priceType;

    @Schema(description = "仓库名称")
    private String storeName;

    @Schema(description = "型号名称")
    private String silkName;

    @Schema(description = "规格（截面）")
    private String areaStr;

    @Schema(description = "销售数量")
    private Integer saleNumber;


    @Schema(description = "单位长度")
    private Integer ecbuluId;

    @Schema(description = "利润")
    private BigDecimal profit;

    @Schema(description = "实际税点")
    private BigDecimal billPercent;
}
