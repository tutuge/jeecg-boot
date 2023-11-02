package org.jeecg.modules.cable.controller.userOffer.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "方案base")
@Data
public class OfferBaseBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "方案ID")
    private Integer ecuoId;
}
