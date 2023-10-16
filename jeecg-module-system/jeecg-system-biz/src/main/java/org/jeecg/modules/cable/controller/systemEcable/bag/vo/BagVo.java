package org.jeecg.modules.cable.controller.systemEcable.bag.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "包带vo")
@Data
public class BagVo {

    @Schema(description = "方案列表")
    private List<EcbBag> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "包带")
    private EcbBag record;
}
