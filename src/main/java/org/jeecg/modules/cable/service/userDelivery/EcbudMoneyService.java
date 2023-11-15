package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;

import java.util.List;

public interface EcbudMoneyService {
    List<EcbudMoney> getList(EcbudMoney record);

    long getCount(EcbudMoney record);

    EcbudMoney getObject(EcbudMoney record);

    Integer insert(EcbudMoney record);

    Integer update(EcbudMoney record);

    Integer delete(EcbudMoney record);

    
    List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record);

    //getObjectPassProvinceName
    EcbudMoney getObjectPassProvinceName(EcbudMoney record);


    EcbudMoney getLatestObject(EcbudMoney record);
}
