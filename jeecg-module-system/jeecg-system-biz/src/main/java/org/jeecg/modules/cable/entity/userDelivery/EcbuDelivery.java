package org.jeecg.modules.cable.entity.userDelivery;

import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuDelivery {
    private Integer ecbudId;

    private Integer ecCompanyId;

    private Integer ecbusId;

    private Boolean startType;

    private Integer sortId;

    private Integer deliveryType;

    private String deliveryName;

    private String description;

    private EcbuStore ecbuStore;
}
