package org.jeecg.modules.cable.controller.price.input.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.math.BigDecimal;

@Schema(description = "通过ecuqiId获取结构体")
@Data
public class InputStructureVo {

    @Schema(description = "导体外径")
    private BigDecimal conductorDiameter = BigDecimal.ZERO;

    @Schema(description = "粗芯外径")
    private BigDecimal fireDiameter = BigDecimal.ZERO;

    @Schema(description = "细芯外径")
    private BigDecimal zeroDiameter = BigDecimal.ZERO;

    @Schema(description = "粗芯重量")
    private BigDecimal fireWeight = BigDecimal.ZERO;

    @Schema(description = "粗芯金额")
    private BigDecimal fireMoney = BigDecimal.ZERO;

    @Schema(description = "细芯重量")
    private BigDecimal zeroWeight = BigDecimal.ZERO;

    @Schema(description = "细芯金额")
    private BigDecimal zeroMoney = BigDecimal.ZERO;

    @Schema(description = "导体重量")
    private BigDecimal conductorWeight = BigDecimal.ZERO;

    @Schema(description = "导体金额")
    private BigDecimal conductorMoney = BigDecimal.ZERO;


    @Schema(description = "粗芯云母带半径")
    private BigDecimal fireMicatapeDiameter = BigDecimal.ZERO;

    @Schema(description = "细芯云母带半径")
    private BigDecimal zeroMicatapeDiameter = BigDecimal.ZERO;

    @Schema(description = "云母带重量")
    private BigDecimal micatapeWeight = BigDecimal.ZERO;

    @Schema(description = "云母带金额")
    private BigDecimal micatapeMoney = BigDecimal.ZERO;


    @Schema(description = "绝缘耐火直径")
    private BigDecimal insulationFireDiameter = BigDecimal.ZERO;

    @Schema(description = "绝缘耐火细芯外径")
    private BigDecimal insulationZeroDiameter = BigDecimal.ZERO;


    @Schema(description = "绝缘重量")
    private BigDecimal insulationWeight = BigDecimal.ZERO;

    @Schema(description = "绝缘金额")
    private BigDecimal insulationMoney = BigDecimal.ZERO;

    @Schema(description = "导体外径")
    private BigDecimal externalDiameter = BigDecimal.ZERO;

    @Schema(description = "填充物重量")
    private BigDecimal infillingWeight = BigDecimal.ZERO;

    @Schema(description = "填充物金额")
    private BigDecimal infillingMoney = BigDecimal.ZERO;

    @Schema(description = "包带外径")
    private BigDecimal bagDiameter = BigDecimal.ZERO;

    @Schema(description = "包带重量")
    private BigDecimal bagWeight = BigDecimal.ZERO;

    @Schema(description = "包带金额")
    private BigDecimal bagMoney = BigDecimal.ZERO;

    @Schema(description = "屏蔽重量")
    private BigDecimal shieldWeight = BigDecimal.ZERO;

    @Schema(description = "屏蔽金额")
    private BigDecimal shieldMoney = BigDecimal.ZERO;

    @Schema(description = "钢带外径")
    private BigDecimal steelbandDiameter = BigDecimal.ZERO;

    @Schema(description = "钢带重量")
    private BigDecimal steelbandWeight = BigDecimal.ZERO;

    @Schema(description = "钢带金额")
    private BigDecimal steelbandMoney = BigDecimal.ZERO;

    @Schema(description = "护套外径")
    private BigDecimal sheathDiameter = BigDecimal.ZERO;

    @Schema(description = "护套重量")
    private BigDecimal sheathMoney = BigDecimal.ZERO;

    @Schema(description = "护套金额")
    private BigDecimal sheathWeight = BigDecimal.ZERO;

    @Schema(description = "总重量")
    private BigDecimal totalWeight = BigDecimal.ZERO;

    @Schema(description = "总金额")
    private BigDecimal totalMoney = BigDecimal.ZERO;

    @Schema(description = "报价单")
    private EcuqDesc ecuqDesc;
}
