package org.jeecg.modules.cable.controller.userCommon.position.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduciPositionBo {

    @Schema(description = "图片ID")
    private Integer ecduciId;//图片ID

    @Schema(description = "x轴")
    @NotBlank(message = "x轴不得为空")
    private String pX;//x轴

    @Schema(description = "y轴")
    @NotBlank(message = "y轴不得为空")
    private String pY;//y轴

    @Schema(description = "图片缩放比")
    private BigDecimal imagePercent;//图片缩放比

}
