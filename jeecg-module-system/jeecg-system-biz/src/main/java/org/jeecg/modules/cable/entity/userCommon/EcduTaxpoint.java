package org.jeecg.modules.cable.entity.userCommon;

import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduTaxpoint {
    private Integer ecdutId;//主键ID

    private Integer ecdtId;//系统发票税点id

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private String name;//自定义名称

    private BigDecimal percentCommon;//普票税点

    private BigDecimal percentSpecial;//专票税点

    private String description;//备注

    private EcdTaxpoint ecdTaxpoint;//系统发票税点
}
