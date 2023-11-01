package org.jeecg.modules.cable.controller.userCommon.unit.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

import java.util.List;

@Schema(description = "单位vo")
@Data
public class LengthUnitVo {

    public LengthUnitVo() {
    }

    public LengthUnitVo(List<EcbulUnit> list, long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "列表")
    private List<EcbulUnit> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "单位")
    private EcbulUnit record;
}
