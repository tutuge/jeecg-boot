package org.jeecg.modules.cable.entity.systemOffer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Schema(description = "成本库表对应数据")
@Data
public class EcOffer {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecoId;

    @Schema(description = "电缆丝型号ID")
    private Integer ecsId;//电缆丝型号ID

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "截面")
    private String areaStr;//截面

    @Schema(description = "成本加点")
    private BigDecimal addPercent;//成本加点

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;//粗芯丝号

    @Schema(description = "粗芯根数")
    private Integer fireRootNumber;//粗芯根数

    @Schema(description = "粗芯过膜")
    private Integer fireMembrance;//粗芯过膜

    @Schema(description = "粗芯压型")
    private BigDecimal firePress;//粗芯压型

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;//粗芯绞合系数

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;//细芯丝号

    @Schema(description = "细芯根数")
    private Integer zeroRootNumber;//细芯根数

    @Schema(description = "细芯过膜")
    private Integer zeroMembrance;//细芯过膜

    @Schema(description = "细芯压型")
    private BigDecimal zeroPress;//细芯压型

    @Schema(description = "细芯绞系数")
    private BigDecimal zeroStrand;//细芯绞系数

    @Schema(description = "绝缘ID")
    private Integer ecbiId;//绝缘ID

    @Schema(description = "粗芯绝缘厚度")
    private BigDecimal insulationFireThickness;//粗芯绝缘厚度

    @Schema(description = "细芯绝缘厚度")
    private BigDecimal insulationZeroThickness;//细芯绝缘厚度

    @Schema(description = "包带ID")
    private Integer ecbbId;//包带ID

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;//包带厚度

    @Schema(description = "铠装包带ID")
    private Integer ecbb22Id;//铠装包带ID

    @Schema(description = "铠装包带厚度")
    private BigDecimal bag22Thickness;//铠装包带厚度

    @Schema(description = "屏蔽ID")
    private Integer ecbShieldId;//屏蔽ID

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;//屏蔽厚度

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;//屏蔽编织系数

    @Schema(description = "钢带类型")
    private Integer ecbsbId;//钢带类型

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;//钢带厚度

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;//钢带层数

    @Schema(description = "护套ID")
    private Integer ecbuSheathId;//护套ID

    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;//护套厚度

    @Schema(description = "铠装护套厚度")
    private BigDecimal sheath22Thickness;//铠装护套厚度

    @Schema(description = "云母带ID")
    private Integer ecbmId;//云母带ID

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;//云母带厚度

    @Schema(description = "填充物ID")
    private Integer ecbinId;//填充物ID

    @Schema(description = "钢丝ID")
    private Integer ecbswId;//钢丝ID

    @Schema(description = "钢丝过膜")
    private BigDecimal steelwireMembrance;//钢丝过膜

    @Schema(description = "钢丝压型")
    private BigDecimal steelwirePress;//钢丝压型

    @Schema(description = "成缆系数")
    private BigDecimal cableStrand;//成缆系数

    @Schema(description = "重量")
    private BigDecimal defaultWeight;//重量

    @Schema(description = "金额")
    private BigDecimal defaultMoney;//金额
}
