package org.jeecg.modules.cable.controller.userCommon.taxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "获取税点")
public class EcduTaxPointDealBo {

    @Schema(description = "系统发票税点id")
    private Integer ecdtId;

    @Schema(description = "自定义名称")
    @NotBlank(message = "自定义名称不得为空")
    private String name;

    @Schema(description = "普票税点")
    @NotNull(message = "普票税点不得为空")
    private BigDecimal percentCommon;

    @Schema(description = "专票税点")
    @NotNull(message = "专票税点不得为空")
    private BigDecimal percentSpecial;

    @Schema(description = "备注")
    @NotBlank(message = "备注不得为空")
    private String description;
}
