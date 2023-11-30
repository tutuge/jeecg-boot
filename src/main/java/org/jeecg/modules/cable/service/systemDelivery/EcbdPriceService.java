package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;

import java.util.List;

public interface EcbdPriceService {
    List<EcbdPrice> getList(EcbdPrice record);

    long getCount(EcbdPrice record);

    EcbdPrice getObject(EcbdPrice record);

    Integer insert(EcbdPrice record);

    Integer update(EcbdPrice record);

    void deleteById(Integer ecbdpId);
}
