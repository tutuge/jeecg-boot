package org.jeecg.modules.cable.service.systemDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdMoneyDao;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.service.systemDelivery.EcbdMoneyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbdMoneyServiceImpl implements EcbdMoneyService {
    @Resource
    EcbdMoneyDao ecbdMoneyDao;

    @Override
    public List<EcbdMoney> getList(EcbdMoney record) {
        return ecbdMoneyDao.getList(record);
    }

    @Override
    public long getCount(EcbdMoney record) {
        return ecbdMoneyDao.getCount(record);
    }

    @Override
    public EcbdMoney getObject(EcbdMoney record) {
        return ecbdMoneyDao.getObject(record);
    }

    @Override
    public Integer insert(EcbdMoney record) {
        return ecbdMoneyDao.insert(record);
    }

    @Override
    public Integer update(EcbdMoney record) {
        return ecbdMoneyDao.update(record);
    }
}
