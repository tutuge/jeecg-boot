package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;

public interface EcbdModelService {
    EcbdModel getObject(Integer ecbdId);
    Integer insert(EcbdModel record);
    Integer update(EcbdModel record);

}
