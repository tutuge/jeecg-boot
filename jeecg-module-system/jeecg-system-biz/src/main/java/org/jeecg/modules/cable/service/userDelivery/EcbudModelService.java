package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;

public interface EcbudModelService {
    int insert(EcbudModel record);

    EcbudModel getObject(EcbudModel record);

    int update(EcbudModel record);

    int delete(EcbudModel record);
}
