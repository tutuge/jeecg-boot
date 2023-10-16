package org.jeecg.modules.cable.entity.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuShield {
    private Integer ecbusId;//主键ID

    private Integer ecbsId;//系统屏蔽ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private String name;//自定义名称

    private BigDecimal unitPrice;//单价

    private BigDecimal density;//密度

    private String description;//备注

    private EcbShield ecbShield;//系统屏蔽
}
