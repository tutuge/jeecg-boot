package org.jeecg.modules.cable.controller.userCommon.store.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcbuStoreDealBo {

    @Schema(description = "主键ID")
    private Integer ecbusId;//主键ID

    @Schema(description = "仓库名称")
    @NotBlank(message = "仓库名称不得为空")
    private String storeName;//仓库名称

    @Schema(description = "仓库铜利润")
    @NotNull(message = "仓库铜利润不得为空")
    private BigDecimal percentCopper;//仓库铜利润

    @Schema(description = "仓库铝利润")
    @NotNull(message = "仓库铝利润不得为空")
    private BigDecimal percentAluminium;//仓库铝利润

    @Schema(description = "运费加点")
    @NotNull(message = "运费加点不得为空")
    private BigDecimal dunitMoney;//运费加点

    @Schema(description = "备注")
    @NotBlank(message = "备注不得为空")
    private String description;//备注
}
