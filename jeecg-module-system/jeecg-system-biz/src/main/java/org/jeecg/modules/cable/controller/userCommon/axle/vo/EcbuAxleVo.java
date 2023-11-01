package org.jeecg.modules.cable.controller.userCommon.axle.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;

import java.util.List;

@Schema(description = "木轴vo")
@Data
public class EcbuAxleVo {

    public EcbuAxleVo() {
    }

    public EcbuAxleVo(List<EcbuAxle> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "木轴列表")
    private List<EcbuAxle> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "木轴")
    private EcbuAxle record;
}
