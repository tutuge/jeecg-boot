package org.jeecg.modules.cable.controller.userOffer.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "方案list查询")
@Data
public class EcuOfferListBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不得为空")
    private Integer ecqulId;

    @Schema(description = "启用")
    private Boolean startType;
}
