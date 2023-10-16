package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuPcompany {
    private Integer ecbupId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer platformId;//平台类型ID

    private String pcName;//平台名称

    private BigDecimal pcPercent;//平台税点

    private String description;//备注
}
