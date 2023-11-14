package org.jeecg.modules.cable.controller.price.input.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "通过ecuqiId获取结构体")
@Data
public class InputStructBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecuqiId;

    @Schema(description = "用户导体id")
    @NotNull(message = "导体ID不得为空")
    private Integer ecbucId;

    @Schema(description = "粗芯丝号")
    @NotNull(message = "粗芯丝号不得为空")
    private BigDecimal fireSilkNumber;

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;//粗芯绞合系数

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;//细芯丝号

    @Schema(description = "细芯绞合系数")
    private BigDecimal zeroStrand;

    @Schema(description = "云母带ID")
    private Integer ecbumId;

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;

    @Schema(description = "绝缘ID")
    private Integer ecbuiId;//绝缘ID

    @Schema(description = "粗芯绝缘厚度")
    private BigDecimal insulationFireThickness;

    @Schema(description = "细芯绝缘厚度")
    private BigDecimal insulationZeroThickness;

    @Schema(description = "用户填充物ID")
    private Integer ecbuinId;

    @Schema(description = "铠装包带ID")
    private Integer ecbub22Id;

    @Schema(description = "铠装包带厚度")
    private BigDecimal bag22Thickness;//铠装包带厚度

    @Schema(description = "包带ID")
    private Integer ecbubId;//包带ID

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;//包带厚度

    @Schema(description = "用户钢带ID")
    private Integer ecbusbId;

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;//钢带厚度

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;//钢带层数

    @Schema(description = "护套ID")
    private Integer ecbuSheathId;

    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;

    @Schema(description = "铠装护套厚度")
    private BigDecimal sheath22Thickness;


    @Schema(description = "屏蔽ID")
    private Integer ecbuShieldId;

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;

}
