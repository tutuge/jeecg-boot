package org.jeecg.modules.cable.controller.systemEcable.offer.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcOfferStartBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "方案ID")
    @NotNull(message = "方案ID不得为空")
    private Integer ecoId;

    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不得为空")
    private Integer ecqlId;

    @Schema(description = "是否启用")
    @NotNull(message = "是否启用不得为空")
    private Boolean startType;//是否启用
}
