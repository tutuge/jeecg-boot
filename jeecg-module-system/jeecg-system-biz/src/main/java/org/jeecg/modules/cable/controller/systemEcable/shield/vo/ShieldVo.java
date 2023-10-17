package org.jeecg.modules.cable.controller.systemEcable.shield.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;

import java.util.List;


@Schema(description = "屏蔽vo")
@Data
public class ShieldVo {

    public ShieldVo() {
    }

    public ShieldVo(List<EcbShield> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "方案列表")
    private List<EcbShield> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "屏蔽")
    private EcbShield record;
}
