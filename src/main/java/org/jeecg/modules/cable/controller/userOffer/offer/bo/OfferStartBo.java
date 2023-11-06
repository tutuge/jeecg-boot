package org.jeecg.modules.cable.controller.userOffer.offer.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OfferStartBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "方案ID")
    @NotNull(message = "方案ID不得为空")
    private Integer ecuoId;

    @Schema(description = "是否启用")
    @NotNull(message = "是否启用不得为空")
    private Boolean startType;//是否启用
}
