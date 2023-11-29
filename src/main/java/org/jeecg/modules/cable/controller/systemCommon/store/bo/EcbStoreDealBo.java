package org.jeecg.modules.cable.controller.systemCommon.store.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcbStoreDealBo {

    @Schema(description = "主键ID")
    private Integer ecbsId;

    @Schema(description = "仓库名称")
    @NotBlank(message = "仓库名称不得为空")
    private String storeName;

    @Schema(description = "仓库铜利润")
    @NotNull(message = "仓库铜利润不得为空")
    private BigDecimal percentCopper;

    @Schema(description = "仓库铝利润")
    @NotNull(message = "仓库铝利润不得为空")
    private BigDecimal percentAluminium;

    @Schema(description = "运费加点")
    @NotNull(message = "运费加点不得为空")
    private BigDecimal dunitMoney;

    @Schema(description = "备注")
    @NotBlank(message = "备注不得为空")
    private String description;
}
