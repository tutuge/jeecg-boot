package org.jeecg.modules.cable.controller.systemDelivery.delivery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbDeliveryListVo {

    @Schema(description = "列表")
    private List<EcbDelivery> list;

    @Schema(description = "数量")
    private Long count;
}
