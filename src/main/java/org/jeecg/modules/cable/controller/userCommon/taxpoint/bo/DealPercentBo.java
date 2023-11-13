package org.jeecg.modules.cable.controller.userCommon.taxpoint.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class DealPercentBo {

    @Schema(description = "主键ID")
    private Integer ecdutId;//主键ID

    @Schema(description = "普票税点")
    private BigDecimal percentCommon;//普票税点

    @Schema(description = "专票税点")
    private BigDecimal percentSpecial;//专票税点
}
