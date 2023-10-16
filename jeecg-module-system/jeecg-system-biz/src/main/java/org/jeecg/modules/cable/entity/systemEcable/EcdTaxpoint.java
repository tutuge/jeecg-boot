package org.jeecg.modules.cable.entity.systemEcable;

import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdTaxpoint {
    private Integer ecdtId;//主键ID

    private Integer ecaId;//管理员ID

    private String ecaName;//管理员名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String pointName;//税点名称

    private BigDecimal percentCommon;//普票税点

    private BigDecimal percentSpecial;//专票税点

    private String description;//备注

    private Long addTime;//添加时间

    private Long updateTime;//更新时间

    private EcduTaxpoint ecduTaxpoint;//用户发票税点

    private Integer ecCompanyId;//公司ID
}
