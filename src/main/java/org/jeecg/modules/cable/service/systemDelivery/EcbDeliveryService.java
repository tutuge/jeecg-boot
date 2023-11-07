package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;

import java.util.List;

public interface EcbDeliveryService {

    List<EcbDelivery> getList(EcbDelivery record);


    long getCount(EcbDelivery record);


    EcbDelivery getObject(EcbDelivery record);

    // insert
    Integer insert(EcbDelivery record);

    // update
    Integer update(EcbDelivery record);


    Integer delete(EcbDelivery record);
}
