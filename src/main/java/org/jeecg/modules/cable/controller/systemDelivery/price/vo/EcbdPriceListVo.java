package org.jeecg.modules.cable.controller.systemDelivery.price.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdPriceListVo {

    @Schema(description = "列表")
    private List<EcbdPrice> list;

    @Schema(description = "数量")
    private Long count;
}
