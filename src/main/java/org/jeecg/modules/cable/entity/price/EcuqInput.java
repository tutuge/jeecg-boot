package org.jeecg.modules.cable.entity.price;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.entity.userQuality.EcquParameter;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "报价单每列数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuqInput {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuqiId;

    @Schema(description = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;

    @Schema(description = "仓库ID")
    private Integer ecbusId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "型号系列ID")
    private Integer silkId;

    @Schema(description = "型号系列名称")
    private String silkName;

    @Schema(description = "型号ID")
    private Integer ecusmId;

    @Schema(description = "型号名称")
    private String silkModelName;

    @Schema(description = "型号名称别名")
    private String silkNameAs;

    @Schema(description = "丝名称是否手输")
    private Boolean silkNameInput;

    @Schema(description = "截面积")
    private String areaStr;

    @Schema(description = "截面积别名")
    private String areaStrAs;

    @Schema(description = "截面手输")
    private Boolean areaStrInput;

    @Schema(description = "销售数量")
    private Integer saleNumber;

    @Schema(description = "单位长度ID")
    private Integer ecbuluId;

    @Schema(description = "利润")
    private BigDecimal profit;

    @Schema(description = "利润是否手输")
    private Boolean profitInput;

    @Schema(description = "实际税点 此税点即为开发票的税点")
    private BigDecimal billPercent;

    @Schema(description = "条目备注")
    private String itemDesc;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "无票单价")
    @TableField(exist = false)
    private BigDecimal noBillSingleMoney;

    @Schema(description = "有票单价")
    @TableField(exist = false)
    private BigDecimal billSingleMoney;

    @Schema(description = "无票小计")
    @TableField(exist = false)
    private BigDecimal noBillComputeMoney;

    @Schema(description = "有票小计")
    @TableField(exist = false)
    private BigDecimal billComputeMoney;


    @Schema(description = "总重量")
    @TableField(exist = false)
    private BigDecimal totalWeight;

    @Schema(description = "长度单位")
    @TableField(exist = false)
    private EcbulUnit ecbulUnit;

    @Schema(description = "总米数")
    @TableField(exist = false)
    private Integer meterNumber;

    @Schema(description = "质量参数")
    @TableField(exist = false)
    private EcquParameter ecquParameter;

    @Schema(description = "仓库")
    @TableField(exist = false)
    private EcbuStore ecbuStore;

    @Schema(description = "型号系列")
    @TableField(exist = false)
    private EcuSilk ecuSilk;

    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcquLevel ecquLevel;

    @Schema(description = "报价desc")
    @TableField(exist = false)
    private EcuqDesc ecuqDesc;

    @Schema(description = "用户导体")
    @TableField(exist = false)
    private EcbuConductor ecbuConductor;
}
