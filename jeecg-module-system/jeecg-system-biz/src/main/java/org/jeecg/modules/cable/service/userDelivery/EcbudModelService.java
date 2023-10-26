package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;

public interface EcbudModelService {
    Integer insert(EcbudModel record);

    EcbudModel getObject(EcbudModel record);

    Integer update(EcbudModel record);

    Integer delete(EcbudModel record);
}
