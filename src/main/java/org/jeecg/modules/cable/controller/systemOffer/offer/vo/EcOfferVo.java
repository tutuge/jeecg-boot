package org.jeecg.modules.cable.controller.systemOffer.offer.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "系统成本库表vo")
@Data
public class EcOfferVo {

    @Schema(description = "方案列表")
    private List<EcOffer> list;

    @Schema(description = "数量")
    private long count;
}
