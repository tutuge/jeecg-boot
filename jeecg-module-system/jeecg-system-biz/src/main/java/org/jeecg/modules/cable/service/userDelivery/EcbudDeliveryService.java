package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;

public interface EcbudDeliveryService {

    //getObject
    EcbudDelivery getObject(EcbudDelivery record);

    //insert
    int insert(EcbudDelivery record);

    //updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(EcbudDelivery record);
}
