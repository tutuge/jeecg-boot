package org.jeecg.modules.cable.service.user;


import org.jeecg.modules.cable.entity.user.EcCustomer;

import java.util.List;

public interface EcCustomerService {
    List<EcCustomer> getList(EcCustomer record);

    long getCount(EcCustomer record);

    EcCustomer getObject(EcCustomer record);

    Integer insert(EcCustomer record);

    Integer update(EcCustomer record);
}
