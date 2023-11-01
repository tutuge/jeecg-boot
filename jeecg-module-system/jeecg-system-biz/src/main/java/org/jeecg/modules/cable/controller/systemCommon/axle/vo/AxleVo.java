package org.jeecg.modules.cable.controller.systemCommon.axle.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemCommon.EcbAxle;

import java.util.List;

@Schema(description = "木轴vo")
@Data
public class AxleVo {

    public AxleVo() {
    }

    public AxleVo(List<EcbAxle> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "木轴列表")
    private List<EcbAxle> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "木轴")
    private EcbAxle record;
}
