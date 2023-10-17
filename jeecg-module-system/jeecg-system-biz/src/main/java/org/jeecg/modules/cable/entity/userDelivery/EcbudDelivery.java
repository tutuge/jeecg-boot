package org.jeecg.modules.cable.entity.userDelivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudDelivery {
    /**
     * 用户默认物流或快递类型
     */
    private Integer ecbuddId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecuId;//用户ID

    private Integer sortId;//序号
}
