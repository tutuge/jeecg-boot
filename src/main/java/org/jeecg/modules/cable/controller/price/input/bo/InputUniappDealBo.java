package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "页面显示列表编辑提交")
@Data
public class InputUniappDealBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空。新增ID为0")
    private Integer ecuqiId;

    @Schema(description = "报价单ID")
    @NotNull(message = "报价单ID不得为空")
    private Integer ecuqId;

    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不得为空")
    private Integer ecqulId;

    @Schema(description = "发票类型")
    private Integer priceType;

    @Schema(description = "仓库ID")
    private Integer ecbusId;

    @Schema(description = "导体ID")
    private Integer conductorId;

    @Schema(description = "型号系列ID")
    private Integer silkId;

    @Schema(description = "型号系列名称")
    private String silkName;

    @Schema(description = "型号ID")
    private Integer ecusmId;

    @Schema(description = "平方数")
    private String area;

    @Schema(description = "芯数字符串")
    private String coreStr;

    @Schema(description = "销售数量")
    private Integer saleNumber;

    @Schema(description = "单位长度ID")
    private Integer ecbuluId;

    @Schema(description = "利润")
    private BigDecimal profit;

    @Schema(description = "实际税点")
    private BigDecimal billPercent;
}
