package org.jeecg.modules.cable.entity.userOffer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "对应电缆质量等级中的成本库表")
@Data
public class EcuOffer {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuoId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;

    @Schema(description = "用户导体ID")
    private Integer ecbucId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "截面")
    private String areaStr;

    @Schema(description = "成本加点")
    private BigDecimal addPercent;


    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;

    @Schema(description = "粗芯根数")
    private Integer fireRootNumber;

    @Schema(description = "粗芯过膜")
    private Integer fireMembrance;

    @Schema(description = "粗芯压型")
    private BigDecimal firePress;

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;

    @Schema(description = "细芯根数")
    private Integer zeroRootNumber;

    @Schema(description = "细芯过膜")
    private Integer zeroMembrance;

    @Schema(description = "细芯压型")
    private BigDecimal zeroPress;

    @Schema(description = "细芯绞系数")
    private BigDecimal zeroStrand;

    @Schema(description = "绝缘ID")
    private Integer ecbuiId;

    @Schema(description = "粗芯绝缘厚度")
    private BigDecimal insulationFireThickness;

    @Schema(description = "细芯绝缘厚度")
    private BigDecimal insulationZeroThickness;

    @Schema(description = "包带ID")
    private Integer ecbubId;

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;

    @Schema(description = "铠装包带ID")
    @TableField(value = "ecbub_22_id")
    private Integer ecbub22Id;

    @Schema(description = "铠装包带厚度")
    @TableField(value = "bag_22_thickness")
    private BigDecimal bag22Thickness;


    @Schema(description = "屏蔽ID")
    private Integer ecbuShieldId;

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;

    @Schema(description = "钢带类型")
    private Integer ecbusbId;

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;

    @Schema(description = "护套ID")
    private Integer ecbuSheathId;


    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;

    @Schema(description = "铠装护套厚度")
    @TableField(value = "sheath22_thickness")
    private BigDecimal sheath22Thickness;

    @Schema(description = "云母带ID")
    private Integer ecbumId;

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;

    @Schema(description = "填充物ID")
    private Integer ecbuinId;

    @Schema(description = "钢丝ID")
    private Integer ecbuswId;

    @Schema(description = "钢丝过膜")
    private BigDecimal steelwireMembrance;

    @Schema(description = "钢丝压型")
    private BigDecimal steelwirePress;

    @Schema(description = "成缆系数")
    private BigDecimal cableStrand;

    @Schema(description = "默认重量")
    private BigDecimal defaultWeight;

    @Schema(description = "默认金额")
    private BigDecimal defaultMoney;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcquLevel ecquLevel;

    @Schema(description = "用户云母带")
    @TableField(exist = false)
    private EcbuMicaTape ecbuMicatape;

    @Schema(description = "系统云母带")
    @TableField(exist = false)
    private EcbMicaTape ecbMicatape;

    @Schema(description = "用户绝缘")
    @TableField(exist = false)
    private EcbuInsulation ecbuInsulation;

    @Schema(description = "系统绝缘")
    @TableField(exist = false)
    private EcbInsulation ecbInsulation;

    @Schema(description = "用户包带")
    @TableField(exist = false)
    private EcbuBag ecbuBag;

    @Schema(description = "用户铠装包带")
    @TableField(exist = false)
    private EcbuBag ecbu22Bag;

    @Schema(description = "铠装系统包带")
    @TableField(exist = false)
    private EcbBag ecb22Bag;

    @Schema(description = "系统包带")
    @TableField(exist = false)
    private EcbBag ecbBag;

    @Schema(description = "用户屏蔽")
    @TableField(exist = false)
    private EcbuShield ecbuShield;

    @Schema(description = "系统屏蔽")
    @TableField(exist = false)
    private EcbShield ecbShield;


    @Schema(description = "用户钢带")
    @TableField(exist = false)
    private EcbuSteelBand ecbuSteelband;

    @Schema(description = "系统钢带")
    @TableField(exist = false)
    private EcbSteelBand ecbSteelband;

    @Schema(description = "用户护套")
    @TableField(exist = false)
    private EcbuSheath ecbuSheath;

    @Schema(description = "系统护套")
    @TableField(exist = false)
    private EcbSheath ecbSheath;

    @Schema(description = "用户填充物")
    @TableField(exist = false)
    private EcbuInfilling ecbuInfilling;

    @Schema(description = "系统填充物")
    @TableField(exist = false)
    private EcbInfilling ecbInfilling;

    @Schema(description = "用户导体")
    @TableField(exist = false)
    private EcbuConductor ecbuConductor;

    @Schema(description = "系统导体")
    @TableField(exist = false)
    private EcbMaterials ecbMaterials;
}
