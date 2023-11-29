package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdWeight;

public interface EcbdWeightService {
    EcbdWeight getObject(Integer ecbdId);
    Integer insert(EcbdWeight record);
    Integer update(EcbdWeight record);

}
