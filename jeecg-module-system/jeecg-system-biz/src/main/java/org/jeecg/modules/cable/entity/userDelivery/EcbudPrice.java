package org.jeecg.modules.cable.entity.userDelivery;

import org.jeecg.modules.cable.entity.pcc.EcProvince;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudPrice {
    private Integer ecbudpId;

    private Integer ecbudId;

    private Boolean startType;

    private Integer sortId;

    private Integer ecpId;

    private String provinceName;

    private BigDecimal firstPrice;

    private BigDecimal price1;

    private BigDecimal price2;

    private BigDecimal price3;

    private BigDecimal price4;

    private BigDecimal price5;

    //Ec_province
    private EcProvince ecProvince;
}
