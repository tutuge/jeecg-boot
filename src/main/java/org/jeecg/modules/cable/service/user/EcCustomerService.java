package org.jeecg.modules.cable.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.user.EcCustomer;

import java.util.List;

public interface EcCustomerService extends IService<EcCustomer> {
    List<EcCustomer> getList(EcCustomer record);

    long getCount(EcCustomer record);

    EcCustomer getObject(EcCustomer record);

    Integer insert(EcCustomer record);

    Integer update(EcCustomer record);

    void deleteById(Integer eccuId);
}
