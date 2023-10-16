package org.jeecg.modules.cable.entity.systemDelivery;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdPrice {
    private Integer ecbdpId;//主键ID

    private Integer ecbdId;//快递ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer ecpId;//省ID

    private String provinceName;//省名称

    private BigDecimal firstPrice;//首重金额

    private BigDecimal price1;//金额

    private BigDecimal price2;//金额

    private BigDecimal price3;//金额

    private BigDecimal price4;//金额

    private BigDecimal price5;//金额
}
