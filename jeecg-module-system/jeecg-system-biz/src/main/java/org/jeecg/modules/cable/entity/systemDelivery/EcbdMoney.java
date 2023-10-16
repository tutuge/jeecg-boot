package org.jeecg.modules.cable.entity.systemDelivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdMoney {
    private Integer ecbdmId;//主键ID

    private Integer ecbdId;//主键ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer ecpId;//省ID

    private String provinceName;//省名称

    private int firstWeight;//首重

    private BigDecimal firstMoney;//首重金额

    private BigDecimal continueMoney;//续重金额
}
