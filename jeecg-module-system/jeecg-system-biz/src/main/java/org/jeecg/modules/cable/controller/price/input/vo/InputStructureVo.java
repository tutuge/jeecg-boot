package org.jeecg.modules.cable.controller.price.input.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.math.BigDecimal;

@Schema(description = "通过ecuqiId获取结构体")
@Data
public class InputStructureVo {

    @Schema(description = "导体外径")
    private BigDecimal conductorDiameter;

    @Schema(description = "粗芯外径")
    private BigDecimal fireDiameter;

    @Schema(description = "细芯外径")
    private BigDecimal zeroDiameter;

    @Schema(description = "粗芯重量")
    private BigDecimal fireWeight;

    @Schema(description = "粗芯金额")
    private BigDecimal fireMoney;

    @Schema(description = "细芯重量")
    private BigDecimal zeroWeight;

    @Schema(description = "细芯金额")
    private BigDecimal zeroMoney;

    @Schema(description = "导体重量")
    private BigDecimal conductorWeight;

    @Schema(description = "导体金额")
    private BigDecimal conductorMoney;


    @Schema(description = "粗芯云母带半径")
    private BigDecimal fireMicatapeDiameter;

    @Schema(description = "细芯云母带半径")
    private BigDecimal zeroMicatapeDiameter;

    @Schema(description = "云母带重量")
    private BigDecimal micatapeWeight;

    @Schema(description = "云母带金额")
    private BigDecimal micatapeMoney;


    @Schema(description = "绝缘耐火直径")
    private BigDecimal insulationFireDiameter;

    @Schema(description = "绝缘耐火细芯外径")
    private BigDecimal insulationZeroDiameter;


    @Schema(description = "绝缘重量")
    private BigDecimal insulationWeight;

    @Schema(description = "绝缘金额")
    private BigDecimal insulationMoney;

    @Schema(description = "导体外径")
    private BigDecimal externalDiameter;

    @Schema(description = "填充物重量")
    private BigDecimal infillingWeight;

    @Schema(description = "填充物金额")
    private BigDecimal infillingMoney;

    @Schema(description = "包带外径")
    private BigDecimal bagDiameter;

    @Schema(description = "包带重量")
    private BigDecimal bagWeight;

    @Schema(description = "包带金额")
    private BigDecimal bagMoney;

    @Schema(description = "屏蔽重量")
    private BigDecimal shieldWeight;

    @Schema(description = "屏蔽金额")
    private BigDecimal shieldMoney;

    @Schema(description = "钢带外径")
    private BigDecimal steelbandDiameter;

    @Schema(description = "钢带重量")
    private BigDecimal steelbandWeight;

    @Schema(description = "钢带金额")
    private BigDecimal steelbandMoney;

    @Schema(description = "护套外径")
    private BigDecimal sheathDiameter;
    
    @Schema(description = "护套重量")
    private BigDecimal sheathMoney;

    @Schema(description = "护套金额")
    private BigDecimal sheathWeight;

    @Schema(description = "总重量")
    private BigDecimal totalWeight;

    @Schema(description = "总金额")
    private BigDecimal totalMoney;

    @Schema(description = "报价单")
    private EcuqDesc ecuqDesc;
}
