package org.jeecg.modules.cable.entity.systemEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbShield {
    private Integer ecbsId;//主键ID

    private Integer ecaId;//管理员ID

    private String ecaName;//管理员名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String abbreviation;//简称

    private String fullName;//全称

    private BigDecimal unitPrice;//单价

    private BigDecimal density;//密度

    private String description;//备注

    private Long addTime;//添加时间

    private Long updateTime;//更新时间

    private EcbuShield ecbuShield;//用户屏蔽

    private int ecCompanyId;//公司ID
}
