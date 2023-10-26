package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcCustomerDao;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcCustomerServiceImpl implements EcCustomerService {
    @Resource
    EcCustomerDao ecCustomerDao;

    @Override
    public List<EcCustomer> getList(EcCustomer record) {
        return ecCustomerDao.getList(record);
    }

    @Override
    public long getCount(EcCustomer record) {
        return ecCustomerDao.getCount(record);
    }

    @Override
    public EcCustomer getObject(EcCustomer record) {//根据EcUser获取EcUser

        return ecCustomerDao.getObject(record);
    }

    @Override
    public Integer insert(EcCustomer record) {
        return ecCustomerDao.insert(record);
    }

    @Override
    public Integer update(EcCustomer record) {
        return ecCustomerDao.update(record);
    }

}
