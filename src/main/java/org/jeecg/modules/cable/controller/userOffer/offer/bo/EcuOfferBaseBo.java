package org.jeecg.modules.cable.controller.userOffer.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "方案base")
@Data
public class EcuOfferBaseBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "方案ID")
    @NotNull(message = "主键不得为空")
    private Integer ecuoId;
}
