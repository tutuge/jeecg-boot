package org.jeecg.modules.cable.controller.userOffer.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "获取方案数据")
@Data
public class OfferStructBo {

    @Schema(description = "方案ID")
    private Integer ecuoId;

    @Schema(description = "型号名")
    private String silkName;
}
