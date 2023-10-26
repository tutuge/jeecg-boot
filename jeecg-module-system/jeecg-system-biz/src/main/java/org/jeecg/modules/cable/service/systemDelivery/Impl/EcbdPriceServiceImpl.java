package org.jeecg.modules.cable.service.systemDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdPriceDao;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.service.systemDelivery.EcbdPriceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbdPriceServiceImpl implements EcbdPriceService {
    @Resource
    EcbdPriceDao ecbdPriceDao;

    @Override
    public List<EcbdPrice> getList(EcbdPrice record) {
        return ecbdPriceDao.getList(record);
    }

    @Override
    public long getCount(EcbdPrice record) {
        return ecbdPriceDao.getCount(record);
    }

    @Override
    public EcbdPrice getObject(EcbdPrice record) {
        return ecbdPriceDao.getObject(record);
    }

    @Override
    public Integer insert(EcbdPrice record) {
        return ecbdPriceDao.insert(record);
    }

    @Override
    public Integer update(EcbdPrice record) {
        return ecbdPriceDao.update(record);
    }
}
