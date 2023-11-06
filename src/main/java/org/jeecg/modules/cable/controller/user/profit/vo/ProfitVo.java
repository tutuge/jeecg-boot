package org.jeecg.modules.cable.controller.user.profit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.user.EcProfit;

import java.util.List;

@Schema(description = "利润")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitVo extends EcProfit {


    @Schema(description = "型号")
    private List<EcSilk> silks;
}
