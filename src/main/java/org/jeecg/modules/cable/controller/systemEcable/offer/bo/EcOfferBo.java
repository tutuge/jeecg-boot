package org.jeecg.modules.cable.controller.systemEcable.offer.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "方案")
@Data
public class EcOfferBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "质量等级ID")
    private Integer ecqlId;

    @Schema(description = "主键ID")
    private Integer ecoId;//主键ID

    @Schema(description = "排序")
    private Integer sortId;


    @Schema(description = "启用")
    private Boolean startType;

    @Schema(description = "截面")
    private String areaStr;//截面
}
