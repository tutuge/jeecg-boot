package org.jeecg.modules.cable.controller.userDelivery.price.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;

import java.util.List;

@Schema(description = "物流信息vo")
@Data
public class EcbudPriceVo {

    public EcbudPriceVo() {
    }

    public EcbudPriceVo(List<EcbudPrice> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "物流信息列表")
    private List<EcbudPrice> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "物流信息")
    private EcbudPrice record;
}
