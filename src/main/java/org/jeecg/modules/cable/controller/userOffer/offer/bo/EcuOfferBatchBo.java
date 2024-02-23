package org.jeecg.modules.cable.controller.userOffer.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "批量修改成本库表")
public class EcuOfferBatchBo {

    @Schema(description = "主键ID")
    @NotBlank(message = "主键不得为空")
    private String ecuoId;

    @Schema(description = "材料ID")
    @NotNull(message = "材料ID不得为空")
    private Integer materialId;

    @Schema(description = "材料类型ID")
    @NotNull(message = "材料类型ID不得为空")
    private Integer materialTypeId;

    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    @NotNull(message = "材料类型不得为空")
    private Integer materialType;

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;

    //--------------------内部材料-----------------
    @Schema(description = "粗芯材料厚度")
    private BigDecimal fireThickness;

    @Schema(description = "细芯材料厚度")
    private BigDecimal zeroThickness;

    //--------------------外部材料-----------------

    @Schema(description = "材料厚度")
    private BigDecimal thickness;

    @Schema(description = "系数")
    private BigDecimal factor;
    
    //@Schema(description = "公司ID")
    //private Integer ecCompanyId;
    //
    //@Schema(description = "质量等级ID")
    //private Integer ecqulId;
    //
    //@Schema(description = "用户导体ID")
    //private Integer ecbucId;
    //
    //@Schema(description = "是否启用")
    //private Boolean startType;
    //
    //@Schema(description = "排序")
    //private Integer sortId;
    //
    //@Schema(description = "截面")
    //private String areaStr;
    //
    //@Schema(description = "成本加点")
    //private BigDecimal addPercent;
    //
    //
    //@Schema(description = "粗芯丝号")
    //private BigDecimal fireSilkNumber;
    //
    //@Schema(description = "粗芯根数")
    //private Integer fireRootNumber;
    //
    //@Schema(description = "粗芯过膜")
    //private Integer fireMembrance;
    //
    //@Schema(description = "粗芯压型")
    //private BigDecimal firePress;
    //
    //@Schema(description = "粗芯绞合系数")
    //private BigDecimal fireStrand;
    //
    //@Schema(description = "细芯丝号")
    //private BigDecimal zeroSilkNumber;
    //
    //@Schema(description = "细芯根数")
    //private Integer zeroRootNumber;
    //
    //@Schema(description = "细芯过膜")
    //private Integer zeroMembrance;
    //
    //@Schema(description = "细芯压型")
    //private BigDecimal zeroPress;
    //
    //@Schema(description = "细芯绞系数")
    //private BigDecimal zeroStrand;
    //
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
    //private Integer ecbub22Id;
    //
    //@Schema(description = "铠装包带厚度")
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
    //private Integer ecbusid;
    //
    //
    //@Schema(description = "护套厚度")
    //private BigDecimal sheathThickness;
    //
    //@Schema(description = "铠装护套厚度")
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
    //
    //@Schema(description = "默认重量")
    //private BigDecimal defaultWeight;
    //
    //@Schema(description = "默认金额")
    //private BigDecimal defaultMoney;

}
