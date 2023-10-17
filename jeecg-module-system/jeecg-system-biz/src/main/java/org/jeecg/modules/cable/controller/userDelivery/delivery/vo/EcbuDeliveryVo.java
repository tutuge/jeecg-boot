package org.jeecg.modules.cable.controller.userDelivery.delivery.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;

import java.util.List;

@Schema(description = "物流信息vo")
@Data
public class EcbuDeliveryVo {

    public EcbuDeliveryVo() {
    }

    public EcbuDeliveryVo(List<EcbuDelivery> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "物流信息列表")
    private List<EcbuDelivery> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "物流信息")
    private EcbuDelivery record;
}
