package org.jeecg.modules.cable.controller.systemCommon.unit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;

import java.util.List;


@Schema(description = "长度单位")
@Data
public class EcblUnitListVo {

    public EcblUnitListVo() {
    }

    public EcblUnitListVo(List<EcblUnit> list, Long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "列表")
    private List<EcblUnit> list;

    @Schema(description = "数量")
    private Long count;
}
