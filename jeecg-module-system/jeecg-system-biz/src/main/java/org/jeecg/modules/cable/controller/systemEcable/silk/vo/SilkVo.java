package org.jeecg.modules.cable.controller.systemEcable.silk.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;

import java.util.List;


@Schema(description = "丝型号vo")
@Data
public class SilkVo {

    public SilkVo() {
    }

    public SilkVo(List<EcbSteelBand> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "方案列表")
    private List<EcbSteelBand> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "丝型号")
    private EcbSteelBand record;
}
