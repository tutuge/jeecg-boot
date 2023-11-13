package org.jeecg.modules.cable.controller.price.input.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;

import java.math.BigDecimal;
import java.util.List;

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

    @Schema(description = "报价单明细")
    private List<EcuqInputVo> listInput;

    @Schema(description = "快递")
    private List<DeliveryObj> listDeliveryPrice;
}
