package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;

public interface EcbdModelService {
    EcbdModel getObject(EcbdModel record);
    int insert(EcbdModel record);
    int update(EcbdModel record);

}
