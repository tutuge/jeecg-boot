package org.jeecg.modules.cable.entity.userOffer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.userEcable.*;

import java.math.BigDecimal;

@Schema(description = "对应电缆质量等级中的成本库表")
@Data
public class EcuOffer {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuoId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;//质量等级ID

    @Schema(description = "用户导体ID")
    private Integer ecbucId;//用户导体ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "排序")
    private Integer sortId;//序号

    @Schema(description = "截面")
    private String areaStr;

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
    private Integer ecbuiId;//绝缘ID

    @Schema(description = "粗芯绝缘厚度")
    private BigDecimal insulationFireThickness;//粗芯绝缘厚度

    @Schema(description = "细芯绝缘厚度")
    private BigDecimal insulationZeroThickness;//细芯绝缘厚度

    @Schema(description = "包带ID")
    private Integer ecbubId;//包带ID

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;//包带厚度

    @Schema(description = "铠装包带ID")
    @TableField(value = "ecbub_22_id")
    private Integer ecbub22Id;//铠装包带ID

    @Schema(description = "铠装包带厚度")
    @TableField(value = "bag_22_thickness")
    private BigDecimal bag22Thickness;//铠装包带厚度


    @Schema(description = "屏蔽ID")
    private Integer ecbuShieldId;//屏蔽ID

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;//屏蔽厚度

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;//屏蔽编织系数

    @Schema(description = "钢带类型")
    private Integer ecbusbId;//钢带类型

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;//钢带厚度

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;//钢带层数

    @Schema(description = "护套ID")
    private Integer ecbuSheathId;//护套ID


    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;//护套厚度

    @Schema(description = "铠装护套厚度")
    @TableField(value = "bag_22_thickness")
    private BigDecimal sheath22Thickness;//铠装护套厚度

    @Schema(description = "云母带ID")
    private Integer ecbumId;//云母带ID

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;//云母带厚度

    @Schema(description = "填充物ID")
    private Integer ecbuinId;//填充物ID

    @Schema(description = "钢丝ID")
    private Integer ecbuswId;//钢丝ID

    @Schema(description = "钢丝过膜")
    private BigDecimal steelwireMembrance;//钢丝过膜

    @Schema(description = "钢丝压型")
    private BigDecimal steelwirePress;//钢丝压型

    @Schema(description = "成缆系数")
    private BigDecimal cableStrand;//成缆系数

    @Schema(description = "默认重量")
    private BigDecimal defaultWeight;//默认重量

    @Schema(description = "默认金额")
    private BigDecimal defaultMoney;//默认金额

    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcquLevel ecquLevel;//质量等级

    @Schema(description = "用户云母带")
    @TableField(exist = false)
    private EcbuMicaTape ecbuMicatape;//用户云母带

    @Schema(description = "系统云母带")
    @TableField(exist = false)
    private EcbMicaTape ecbMicatape;//系统云母带

    @Schema(description = "用户绝缘")
    @TableField(exist = false)
    private EcbuInsulation ecbuInsulation;//用户绝缘

    @Schema(description = "系统绝缘")
    @TableField(exist = false)
    private EcbInsulation ecbInsulation;//系统绝缘

    @Schema(description = "用户包带")
    @TableField(exist = false)
    private EcbuBag ecbuBag;//用户包带

    @Schema(description = "用户铠装包带")
    @TableField(exist = false)
    private EcbuBag ecbu22Bag;//用户铠装包带

    @Schema(description = "铠装系统包带")
    @TableField(exist = false)
    private EcbBag ecb22Bag;//铠装系统包带

    @Schema(description = "系统包带")
    @TableField(exist = false)
    private EcbBag ecbBag;//系统包带

    @Schema(description = "用户屏蔽")
    @TableField(exist = false)
    private EcbuShield ecbuShield;//用户屏蔽

    @Schema(description = "系统屏蔽")
    @TableField(exist = false)
    private EcbShield ecbShield;//系统屏蔽


    @Schema(description = "用户钢带")
    @TableField(exist = false)
    private EcbuSteelband ecbuSteelband;//用户钢带

    @Schema(description = "系统钢带")
    @TableField(exist = false)
    private EcbSteelBand ecbSteelband;//系统钢带

    @Schema(description = "用户护套")
    @TableField(exist = false)
    private EcbuSheath ecbuSheath;//用户护套

    @Schema(description = "系统护套")
    @TableField(exist = false)
    private EcbSheath ecbSheath;//系统护套

    @Schema(description = "用户填充物")
    @TableField(exist = false)
    private EcbuInfilling ecbuInfilling;//用户填充物

    @Schema(description = "系统填充物")
    @TableField(exist = false)
    private EcbInfilling ecbInfilling;//系统填充物

    @Schema(description = "用户导体")
    @TableField(exist = false)
    private EcbuConductor ecbuConductor;//用户导体

    @Schema(description = "系统导体")
    @TableField(exist = false)
    private EcbConductor ecbConductor;//系统导体
}
