package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuStore {
    private Integer ecbusId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer sortId;//序号

    private Boolean startType;//是否启用

    private Boolean defaultType;//是否默认仓库

    private String storeName;//仓库名称

    private BigDecimal percentCopper;//仓库铜利润

    private BigDecimal percentAluminium;//仓库铝利润

    private BigDecimal dunitMoney;//运费加点

    private String description;//备注
}
