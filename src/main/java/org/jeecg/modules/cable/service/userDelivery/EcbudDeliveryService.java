package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;

public interface EcbudDeliveryService {


    EcbudDelivery getObject(EcbudDelivery record);


    Integer insert(EcbudDelivery record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcbudDelivery record);
}
