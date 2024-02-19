package org.jeecg.modules.cable.domain.computeBo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "导体")
@Data
public class Conductor {

    @Schema(description = "导体ID")
    private Integer id;

    @Schema(description = "材料名称")
    private String fullName;

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;

    @Schema(description = "粗芯根数")
    private Integer fireRootNumber;

    //@Schema(description = "粗芯过膜")
    //private Integer fireMembrance;
    //
    //@Schema(description = "粗芯压型")
    //private BigDecimal firePress;

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;

    @Schema(description = "细芯根数")
    private Integer zeroRootNumber;

    //@Schema(description = "细芯过膜")
    //private Integer zeroMembrance;
    //
    //@Schema(description = "细芯压型")
    //private BigDecimal zeroPress;

    @Schema(description = "细芯绞系数")
    private BigDecimal zeroStrand;

    //@Schema(description = "绝缘ID")
    //private Integer ecbuiId;
    //
    //@Schema(description = "粗芯绝缘厚度")
    //private BigDecimal insulationFireThickness;
    //
    //@Schema(description = "细芯绝缘厚度")
    //private BigDecimal insulationZeroThickness;
    //
    //@Schema(description = "包带ID")
    //private Integer ecbubId;
    //
    //@Schema(description = "包带厚度")
    //private BigDecimal bagThickness;
    //
    //@Schema(description = "铠装包带ID")
    //@TableField(value = "ecbub_22_id")
    //private Integer ecbub22Id;
    //
    //@Schema(description = "铠装包带厚度")
    //@TableField(value = "bag_22_thickness")
    //private BigDecimal bag22Thickness;
    //
    //
    //@Schema(description = "屏蔽ID")
    //private Integer ecbuShieldId;
    //
    //@Schema(description = "屏蔽厚度")
    //private BigDecimal shieldThickness;
    //
    //@Schema(description = "屏蔽编织系数")
    //private BigDecimal shieldPercent;
    //
    //@Schema(description = "钢带类型")
    //private Integer ecbusbId;
    //
    //@Schema(description = "钢带厚度")
    //private BigDecimal steelbandThickness;
    //
    //@Schema(description = "钢带层数")
    //private Integer steelbandStorey;
    //
    //@Schema(description = "护套ID")
    //private Integer ecbuSheathId;
    //
    //
    //@Schema(description = "护套厚度")
    //private BigDecimal sheathThickness;
    //
    //@Schema(description = "铠装护套厚度")
    //@TableField(value = "sheath22_thickness")
    //private BigDecimal sheath22Thickness;
    //
    //@Schema(description = "云母带ID")
    //private Integer ecbumId;
    //
    //@Schema(description = "云母带厚度")
    //private BigDecimal micatapeThickness;
    //
    //@Schema(description = "填充物ID")
    //private Integer ecbuinId;
    //
    //@Schema(description = "钢丝ID")
    //private Integer ecbuswId;
    //
    //@Schema(description = "钢丝过膜")
    //private BigDecimal steelwireMembrance;
    //
    //@Schema(description = "钢丝压型")
    //private BigDecimal steelwirePress;
    //
    //@Schema(description = "成缆系数")
    //private BigDecimal cableStrand;
}
