package org.jeecg.modules.cable.service.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.user.EcCustomer;

import java.util.List;

public interface EcCustomerService {
    List<EcCustomer> getList(EcCustomer record);

    long getCount(EcCustomer record);

    EcCustomer getObject(EcCustomer record);
    EcCustomer getObjectById(Integer eccuId);

    Integer insert(EcCustomer record);

    Integer update(EcCustomer record);

    void deleteById(Integer eccuId);

    IPage<EcCustomer> page(Page<EcCustomer> page, QueryWrapper<EcCustomer> queryWrapper);
}
