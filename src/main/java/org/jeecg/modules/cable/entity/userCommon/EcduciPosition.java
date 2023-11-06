package org.jeecg.modules.cable.entity.userCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduciPosition {

    @Schema(description = "主键ID")
    private Integer ecducipId;//主键ID

    @Schema(description = "图片ID")
    private Integer ecduciId;//图片ID

    @Schema(description = "x轴")
    private String pX;//x轴

    @Schema(description = "y轴")
    private String pY;//y轴

    @Schema(description = "图片缩放比")
    private BigDecimal imagePercent;//图片缩放比

    @Schema(description = "影响时间")
    private Long effectTime;//影响时间
}
