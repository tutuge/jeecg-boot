package org.jeecg.modules.cable.controller.userOffer.offer.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "方案vo")
@Data
public class OfferVo {

    @Schema(description = "方案列表")
    private List<EcuOffer> list;

    @Schema(description = "数量")
    private long count;

    //@Schema(description = "方案")
    //private EcuOffer record;
}
