package org.jeecg.modules.cable.controller.systemEcable.conductor.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;

import java.util.List;

@Schema(description = "导体vo")
@Data
public class ConductorVo {

    public ConductorVo() {
    }

    public ConductorVo(List<EcbConductor> list, long count) {
        this.list = list;
        this.count = count;
    }

    public ConductorVo(List<EcbConductor> list, long count, EcbConductor record) {
        this.list = list;
        this.count = count;
        this.record = record;
    }

    @Schema(description = "方案列表")
    private List<EcbConductor> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "导体")
    private EcbConductor record;
}
