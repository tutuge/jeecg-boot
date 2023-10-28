package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;

import java.util.List;

public interface EcbDeliveryService {
    // getList
    List<EcbDelivery> getList(EcbDelivery record);

    // getCount
    long getCount(EcbDelivery record);

    // getObject
    EcbDelivery getObject(EcbDelivery record);

    // insert
    Integer insert(EcbDelivery record);

    // update
    Integer update(EcbDelivery record);

    // delete
    Integer delete(EcbDelivery record);
}
