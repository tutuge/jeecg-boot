package org.jeecg.modules.cable.entity.price;

import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuqInput {
    private Integer ecuqiId;//主键ID
    private Integer ecuqId;//报价单ID
    private Integer ecqulId;//质量等级ID
    private Integer storeId;//仓库ID
    private Boolean startType;//是否启用
    private Integer sortId;//序号
    private String silkName;//丝名称
    private String silkNameAs;//丝名称别名
    private Boolean silkNameInput;//丝名称是否手输
    private String areaStr;//截面积
    private String areaStrAs;//截面积别名
    private Boolean areaStrInput;//截面手输
    private Integer saleNumber;//销售数量
    private Integer ecbuluId;//单位长度
    private BigDecimal profit;//利润
    private Boolean profitInput;//利润是否手输
    private BigDecimal billPercent;//实际税点 此税点即为开发票的税点
    private String itemDesc;//条目备注
    private EcbuStore ecbuStore;//仓库
    private EcSilk ecSilk;//丝型号
    private EcquLevel ecquLevel;//质量等级
    private EcuqDesc ecuqDesc;//报价desc
    private EcbuConductor ecbuConductor;//用户导体
    private BigDecimal noBillSingleMoney;//无票单价
    private BigDecimal billSingleMoney;//有票单价
    private BigDecimal noBillComputeMoney;//无票小计
    private BigDecimal billComputeMoney;//有票小计
    private EcquParameter ecquParameter;//质量参数
    private BigDecimal totalWeight;//总重
    private EcbulUnit ecbulUnit;//长度单位
    private Integer meterNumber;//总米数
}
