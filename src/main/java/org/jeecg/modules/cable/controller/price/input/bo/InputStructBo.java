package org.jeecg.modules.cable.controller.price.input.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "通过ecuqiId获取结构体")
@Data
public class InputStructBo {

    @Schema(description = "主键ID")
    private Integer ecuqiId;

    @Schema(description = "用户导体id")
    private Integer ecbucId;//用户导体id

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;//粗芯丝号

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;//粗芯绞合系数

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;//细芯丝号

    @Schema(description = "细芯绞合系数")
    private BigDecimal zeroStrand;//细芯绞合系数

    @Schema(description = "粗芯外径")
    private BigDecimal fireDiameter;//粗芯外径


    @Schema(description = "细芯外径")
    private BigDecimal zeroDiameter;//细芯外径

    @Schema(description = "导体重量")
    private BigDecimal conductorWeight;//导体重量


    @Schema(description = "导体金额")
    private BigDecimal conductorMoney;//导体金额

    @Schema(description = "云母带ID")
    private Integer ecbumId;//云母带ID

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;//云母带厚度

    @Schema(description = "绝缘ID")
    private Integer ecbuiId;//绝缘ID

    @Schema(description = "粗芯绝缘厚度")
    private BigDecimal insulationFireThickness;//粗芯绝缘厚度

    @Schema(description = "细芯绝缘厚度")
    private BigDecimal insulationZeroThickness;//细芯绝缘厚度

    @Schema(description = "用户填充物ID")
    private Integer ecbuinId;//用户填充物ID


    @Schema(description = "铠装包带ID")
    private Integer ecbub22Id;

    @Schema(description = "铠装包带厚度")
    private BigDecimal bag22Thickness;//铠装包带厚度

    @Schema(description = "包带ID")
    private Integer ecbubId;//包带ID

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;//包带厚度

    @Schema(description = "用户钢带ID")
    private Integer ecbusbId;//用户钢带ID

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;//钢带厚度

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;//钢带层数

    @Schema(description = "护套ID")
    private Integer ecbsid;//护套ID

    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;//护套厚度
}
