package org.jeecg.modules.cable.controller.userOffer.programme.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "获取编辑结构中的重量和金额")
@Data
public class ProgrammeVo {

    @Schema(description = "导体重量")
    private BigDecimal conductorWeight;

    @Schema(description = "导体金额")
    private BigDecimal conductorMoney;

    @Schema(description = "云母带重量")
    private BigDecimal micatapeWeight;

    @Schema(description = "云母带金额")
    private BigDecimal micatapeMoney;

    @Schema(description = "绝缘重量")
    private BigDecimal insulationWeight;

    @Schema(description = "绝缘金额")
    private BigDecimal insulationMoney;

    @Schema(description = "填充物重量")
    private BigDecimal infillingWeight;

    @Schema(description = "填充物金额")
    private BigDecimal infillingMoney;


    @Schema(description = "包带重量")
    private BigDecimal bagWeight;

    @Schema(description = "包带金额")
    private BigDecimal bagMoney;

    @Schema(description = "钢带重量")
    private BigDecimal steelBandWeight;

    @Schema(description = "钢带金额")
    private BigDecimal steelBandMoney;

    @Schema(description = "护套重量")
    private BigDecimal sheathWeight;

    @Schema(description = "护套金额")
    private BigDecimal sheathMoney;
}
