package org.jeecg.modules.cable.controller.systemEcable.micatape.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;

import java.util.List;


@Schema(description = "云母带vo")
@Data
public class MicatapeVo {

    public MicatapeVo() {
    }

    public MicatapeVo(List<EcbMicaTape> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "方案列表")
    private List<EcbMicaTape> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "云母带")
    private EcbMicaTape record;
}
