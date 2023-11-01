package org.jeecg.modules.cable.controller.userCommon.taxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "编辑税点")
public class TaxPointDealBo {

    @Schema(description = "系统发票税点id")
    @NotNull(message = "系统发票税点ID不得为空")
    private Integer ecdtId;//系统发票税点id

    @Schema(description = "税点名称")
    @NotBlank(message = "税点名称不得为空")
    private String pointName;

    @Schema(description = "普票税点")
    @NotNull(message = "普票税点不得为空")
    private BigDecimal percentCommon;//普票税点

    @Schema(description = "专票税点")
    @NotNull(message = "专票税点不得为空")
    private BigDecimal percentSpecial;//专票税点

    @Schema(description = "备注")
    @NotBlank(message = "备注不得为空")
    private String description;//备注
}
