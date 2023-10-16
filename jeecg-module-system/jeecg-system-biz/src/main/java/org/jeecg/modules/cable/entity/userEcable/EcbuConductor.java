package org.jeecg.modules.cable.entity.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuConductor {
    private Integer ecbucId;//主键ID

    private Integer ecbcId;//系统导体ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private String name;//自定义名称

    private BigDecimal unitPrice;//单价

    private BigDecimal density;//密度

    private BigDecimal resistivity;//电阻

    private String description;//详情

    //EcbConductor
    private EcbConductor ecbConductor;//系统导体

}
