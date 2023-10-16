package org.jeecg.modules.cable.entity.userDelivery;

import org.jeecg.modules.cable.entity.pcc.EcProvince;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudMoney {
    private Integer ecbudmId;//主键ID

    private Integer ecbudId;//快递ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer ecpId;//省ID

    private String provinceName;//省名称

    private Integer firstWeight;//首重

    private BigDecimal firstMoney;//首重价格

    private BigDecimal continueMoney;//续重价格

    private EcProvince ecProvince;//省表
}
