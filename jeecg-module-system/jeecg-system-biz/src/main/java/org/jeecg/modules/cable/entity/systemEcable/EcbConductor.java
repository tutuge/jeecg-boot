package org.jeecg.modules.cable.entity.systemEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbConductor {
    private Integer ecbcId;//主键ID

    private Integer ecaId;//管理员ID

    private String ecaName;//管理员名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String abbreviation;//简称

    private String fullName;//全称

    private BigDecimal unitPrice;//单价

    private BigDecimal density;//密度

    private BigDecimal resistivity;//电阻

    private String description;//详情

    private Long addTime;//添加时间

    private Long updateTime;//修改时间

    private EcbuConductor ecbuConductor;//用户导体

	private int ecCompanyId;//公司ID
}
