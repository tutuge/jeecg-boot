package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;

public interface EcbudDeliveryService {

    //getObject
    EcbudDelivery getObject(EcbudDelivery record);

    //insert
    Integer insert(EcbudDelivery record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcbudDelivery record);
}
