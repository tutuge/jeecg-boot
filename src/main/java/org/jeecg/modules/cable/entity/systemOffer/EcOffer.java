package org.jeecg.modules.cable.entity.systemOffer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;

import java.math.BigDecimal;


@Schema(description = "系统成本库表对应数据")
@Data
public class EcOffer {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecoId;

    @Schema(description = "系统质量等级ID")
    private Integer ecqlId;

    @Schema(description = "型号类型ID")
    private Integer ecsId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
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
    private Integer ecbiId;

    @Schema(description = "粗芯绝缘厚度")
    private BigDecimal insulationFireThickness;

    @Schema(description = "细芯绝缘厚度")
    private BigDecimal insulationZeroThickness;

    @Schema(description = "包带ID")
    private Integer ecbbId;

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;

    @Schema(description = "铠装包带ID")
    @TableField(value = "ecbb_22_id")
    private Integer ecbb22Id;

    @Schema(description = "铠装包带厚度")
    @TableField(value = "bag_22_thickness")
    private BigDecimal bag22Thickness;

    @Schema(description = "屏蔽ID")
    private Integer ecbShieldId;

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;

    @Schema(description = "钢带类型")
    private Integer ecbsbId;

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;

    @Schema(description = "护套ID")
    private Integer ecbSheathId;

    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;

    @Schema(description = "铠装护套厚度")
    @TableField(value = "sheath22_thickness")
    private BigDecimal sheath22Thickness;

    @Schema(description = "云母带ID")
    private Integer ecbmId;

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;

    @Schema(description = "填充物ID")
    private Integer ecbinId;

    @Schema(description = "钢丝ID")
    private Integer ecbswId;

    @Schema(description = "钢丝过膜")
    private BigDecimal steelwireMembrance;

    @Schema(description = "钢丝压型")
    private BigDecimal steelwirePress;

    @Schema(description = "成缆系数")
    private BigDecimal cableStrand;

    @Schema(description = "重量")
    private BigDecimal defaultWeight;

    @Schema(description = "金额")
    private BigDecimal defaultMoney;


    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcqLevel ecqLevel;

    @Schema(description = "系统云母带")
    @TableField(exist = false)
    private EcbMicaTape ecbMicatape;


    @Schema(description = "系统绝缘")
    @TableField(exist = false)
    private EcbInsulation ecbInsulation;


    @Schema(description = "铠装系统包带")
    @TableField(exist = false)
    private EcbBag ecb22Bag;

    @Schema(description = "系统包带")
    @TableField(exist = false)
    private EcbBag ecbBag;


    @Schema(description = "系统屏蔽")
    @TableField(exist = false)
    private EcbShield ecbShield;


    @Schema(description = "系统钢带")
    @TableField(exist = false)
    private EcbSteelBand ecbSteelband;


    @Schema(description = "系统护套")
    @TableField(exist = false)
    private EcbSheath ecbSheath;


    @Schema(description = "系统填充物")
    @TableField(exist = false)
    private EcbInfilling ecbInfilling;


    @Schema(description = "系统导体")
    @TableField(exist = false)
    private EcbConductor ecbConductor;
}
