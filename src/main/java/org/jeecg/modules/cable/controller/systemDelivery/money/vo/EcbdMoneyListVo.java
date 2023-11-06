package org.jeecg.modules.cable.controller.systemDelivery.money.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdMoneyListVo {

    @Schema(description = "列表")
    private List<EcbdMoney> list;

    @Schema(description = "数量")
    private Long count;
}
