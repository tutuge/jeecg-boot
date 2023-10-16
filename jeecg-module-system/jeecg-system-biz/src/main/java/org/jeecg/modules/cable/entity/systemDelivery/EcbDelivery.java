package org.jeecg.modules.cable.entity.systemDelivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbDelivery {
    private Integer ecbdId;//主键ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer deliveryType;//快递类型

    private String deliveryName;//快递名称

    private String description;//备注
}
