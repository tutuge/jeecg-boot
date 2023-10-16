package org.jeecg.modules.cable.entity.userOffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuoProgramme {
    private Integer ecuopId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer sortId;//序号

    private String programmeName;//方案名称

    private String coreStr;//芯数

    private String areaStr;//平方数

    private BigDecimal addPercent;//加点点数
}
