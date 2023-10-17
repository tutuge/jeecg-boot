package org.jeecg.modules.cable.controller.userDelivery.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;

import java.util.List;

@Schema(description = "快递价格vo")
@Data
public class MoneyVo {

    public MoneyVo() {
    }

    public MoneyVo(List<EcbudMoney> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "快递价格列表")
    private List<EcbudMoney> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "快递价格")
    private EcbudMoney record;
}
