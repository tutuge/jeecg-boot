package org.jeecg.modules.cable.controller.systemEcable.bag.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;

import java.util.List;


@Schema(description = "包带vo")
@Data
public class BagVo {

    public BagVo() {
    }

    public BagVo(List<EcbBag> list, Long count) {
        this.list = list;
        this.count = count;
    }

    public BagVo(List<EcbBag> list, Long count, EcbBag record) {
        this.list = list;
        this.count = count;
        this.record = record;
    }

    @Schema(description = "方案列表")
    private List<EcbBag> list;

    @Schema(description = "数量")
    private Long count;

    @Schema(description = "包带")
    private EcbBag record;
}
