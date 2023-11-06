package org.jeecg.modules.cable.controller.systemEcable.steelband.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;

import java.util.List;


@Schema(description = "屏蔽vo")
@Data
public class SteelbandVo {

    public SteelbandVo() {
    }

    public SteelbandVo(List<EcbSteelBand> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "方案列表")
    private List<EcbSteelBand> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "屏蔽")
    private EcbSteelBand record;
}
