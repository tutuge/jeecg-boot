package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;

import java.util.List;

public interface EcbdMoneyService {
    List<EcbdMoney> getList(EcbdMoney record);

    long getCount(EcbdMoney record);

    EcbdMoney getObject(EcbdMoney record);

    Integer insert(EcbdMoney record);

    Integer update(EcbdMoney record);

    void deleteById(Integer ecbdmId);

    EcbdMoney getObjectPassProvinceName(EcbdMoney money);

    EcbdMoney getLatestObject(EcbdMoney record);
}
