package org.jeecg.modules.cable.service.systemDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbDeliveryDao;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.service.systemDelivery.EcbDeliveryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbDeliveryServiceImpl implements EcbDeliveryService {
    @Resource
    EcbDeliveryDao ecbDeliveryDao;

    @Override
    public List<EcbDelivery> getList(EcbDelivery record) {
        return ecbDeliveryDao.getList(record);
    }

    @Override
    public long getCount(EcbDelivery record) {
        return ecbDeliveryDao.getCount(record);
    }

    @Override
    public EcbDelivery getObject(EcbDelivery record) {
        return ecbDeliveryDao.getObject(record);
    }

    @Override
    public int insert(EcbDelivery record) {
        return ecbDeliveryDao.insert(record);
    }

    @Override
    public int update(EcbDelivery record) {
        return ecbDeliveryDao.update(record);
    }

    @Override
    public int delete(EcbDelivery record) {
        return ecbDeliveryDao.delete(record);
    }
}
