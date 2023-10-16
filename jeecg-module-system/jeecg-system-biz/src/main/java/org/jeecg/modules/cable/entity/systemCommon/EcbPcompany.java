package org.jeecg.modules.cable.entity.systemCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbPcompany {
    private Integer ecbpId;//主键ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer platformId;//平台类型ID

    private String pcName;//平台名称

    private BigDecimal pcPercent;//平台税点

    private String description;//备注
}
