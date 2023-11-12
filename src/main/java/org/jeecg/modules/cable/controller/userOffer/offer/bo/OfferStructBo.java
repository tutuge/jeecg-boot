package org.jeecg.modules.cable.controller.userOffer.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "获取方案数据")
@Data
public class OfferStructBo {

    @Schema(description = "方案ID")
    @NotNull(message = "方案ID不得为空")
    private Integer ecuoId;

    @Schema(description = "规格")
    @NotNull(message = "规格不得为空")
    private String silkName;
}
