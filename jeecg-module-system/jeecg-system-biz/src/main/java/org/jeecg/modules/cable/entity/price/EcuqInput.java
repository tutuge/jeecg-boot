package org.jeecg.modules.cable.entity.price;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuqInput {

    @Schema(description = "主键ID")
    private Integer ecuqiId;//主键ID

    @Schema(description = "报价单ID")
    private Integer ecuqId;//报价单ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;//质量等级ID

    @Schema(description = "仓库ID")
    private Integer storeId;//仓库ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "排序")
    private Integer sortId;//序号
    @Schema(description = "丝名称")
    private String silkName;//丝名称

    @Schema(description = "丝名称别名")
    private String silkNameAs;//丝名称别名

    @Schema(description = "丝名称是否手输")
    private Boolean silkNameInput;//丝名称是否手输

    @Schema(description = "截面积")
    private String areaStr;//截面积

    @Schema(description = "截面积别名")
    private String areaStrAs;//截面积别名

    @Schema(description = "截面手输")
    private Boolean areaStrInput;//截面手输

    @Schema(description = "销售数量")
    private Integer saleNumber;//销售数量

    @Schema(description = "单位长度")
    private Integer ecbuluId;//单位长度

    @Schema(description = "利润")
    private BigDecimal profit;//利润

    @Schema(description = "利润是否手输")
    private Boolean profitInput;//利润是否手输

    @Schema(description = "实际税点 此税点即为开发票的税点")
    private BigDecimal billPercent;//实际税点 此税点即为开发票的税点

    @Schema(description = "条目备注")
    private String itemDesc;//条目备注

    @Schema(description = "仓库")
    private EcbuStore ecbuStore;//仓库
    @Schema(description = "丝型号")
    private EcSilk ecSilk;//丝型号

    @Schema(description = "质量等级")
    private EcquLevel ecquLevel;//质量等级

    @Schema(description = "报价desc")
    private EcuqDesc ecuqDesc;//报价desc

    @Schema(description = "用户导体")
    private EcbuConductor ecbuConductor;//用户导体

    @Schema(description = "无票单价")
    private BigDecimal noBillSingleMoney;//无票单价

    @Schema(description = "有票单价")
    private BigDecimal billSingleMoney;//有票单价

    @Schema(description = "无票小计")
    private BigDecimal noBillComputeMoney;//无票小计

    @Schema(description = "有票小计")
    private BigDecimal billComputeMoney;//有票小计

    @Schema(description = "质量参数")
    private EcquParameter ecquParameter;//质量参数

    @Schema(description = "总重")
    private BigDecimal totalWeight;//总重

    @Schema(description = "长度单位")
    private EcbulUnit ecbulUnit;//长度单位

    @Schema(description = "总米数")
    private Integer meterNumber;//总米数
}
