package org.jeecg.modules.cable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillBo {

    BigDecimal billSingleMoney = BigDecimal.ZERO;// 开票单价
    BigDecimal billComputeMoney = BigDecimal.ZERO;// 开票小计
}
