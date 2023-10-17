package org.jeecg.modules.cable.controller.systemEcable.silk.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;

import java.util.List;


@Schema(description = "丝型号vo")
@Data
public class SilkVo {

    public SilkVo() {
    }

    public SilkVo(List<EcbSteelband> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "方案列表")
    private List<EcbSteelband> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "丝型号")
    private EcbSteelband record;
}
