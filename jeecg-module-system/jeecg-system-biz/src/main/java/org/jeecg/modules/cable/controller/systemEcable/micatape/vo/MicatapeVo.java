package org.jeecg.modules.cable.controller.systemEcable.micatape.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;

import java.util.List;


@Schema(description = "云母带vo")
@Data
public class MicatapeVo {

    public MicatapeVo() {
    }

    public MicatapeVo(List<EcbMicatape> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "方案列表")
    private List<EcbMicatape> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "云母带")
    private EcbMicatape record;
}
