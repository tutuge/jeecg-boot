package org.jeecg.modules.cable.controller.userOffer.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "方案")
@Data
public class OfferBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "质量等级ID")
    private Integer ecqulId;

    @Schema(description = "启用")
    private Boolean startType;
}
