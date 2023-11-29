package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;

public interface EcbudWeightService {
    Integer insert(EcbudWeight record);

    EcbudWeight getObject(EcbudWeight record);

    Integer update(EcbudWeight record);

    Integer delete(EcbudWeight record);
}
