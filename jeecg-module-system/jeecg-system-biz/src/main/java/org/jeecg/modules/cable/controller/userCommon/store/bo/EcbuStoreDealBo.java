package org.jeecg.modules.cable.controller.userCommon.store.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcbuStoreDealBo {

    @Schema(description = "主键ID")
    private Integer ecbusId;//主键ID

    @Schema(description = "仓库名称")
    private String storeName;//仓库名称

    @Schema(description = "仓库铜利润")
    private BigDecimal percentCopper;//仓库铜利润

    @Schema(description = "仓库铝利润")
    private BigDecimal percentAluminium;//仓库铝利润

    @Schema(description = "运费加点")
    private BigDecimal dunitMoney;//运费加点

    @Schema(description = "备注")
    private String description;//备注
}
