package org.jeecg.modules.cable.controller.price.input.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.entity.userCommon.EcuConductorPrice;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;

import java.math.BigDecimal;
import java.util.List;
import java.util.Queue;

@Schema(description = "获取报价单列数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputListVo {

    @Schema(description = "开票总计")
    private BigDecimal billTotalMoney;

    @Schema(description = "不开票总计")
    private BigDecimal noBillTotalMoney;

    @Schema(description = "报价单")
    private EcuQuoted ecuQuoted;

    @Schema(description = "本报价单修改的导体价格")
    private List<EcuConductorPrice> ecuConductorPrices;

    @Schema(description = "报价单明细")
    private List<EcuqInputVo> listInput;

    @Schema(description = "快递")
    private List<DeliveryObj> listDeliveryPrice;

    @Schema(description = "报价说明")
    private EcuNotice ecuNotice;
}
