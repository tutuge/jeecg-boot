package org.jeecg.modules.cable.entity.userCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuStore {

    @Schema(description = "主键ID")
    private Integer ecbusId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "是否默认仓库")
    private Boolean defaultType;//是否默认仓库

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
