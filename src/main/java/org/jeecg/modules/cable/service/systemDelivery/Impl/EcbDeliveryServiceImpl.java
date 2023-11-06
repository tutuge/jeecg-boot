package org.jeecg.modules.cable.service.systemDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbDeliveryMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcbDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbDeliveryServiceImpl implements EcbDeliveryService {
    @Resource
    EcbDeliveryMapper ecbDeliveryMapper;

    @Override
    public List<EcbDelivery> getList(EcbDelivery record) {
        return ecbDeliveryMapper.getList(record);
    }

    @Override
    public long getCount(EcbDelivery record) {
        return ecbDeliveryMapper.getCount(record);
    }

    @Override
    public EcbDelivery getObject(EcbDelivery record) {
        return ecbDeliveryMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbDelivery record) {
        return ecbDeliveryMapper.insert(record);
    }

    @Override
    public Integer update(EcbDelivery record) {
        return ecbDeliveryMapper.updateById(record);
    }

    @Override
    public Integer delete(EcbDelivery record) {
        return ecbDeliveryMapper.deleteById(record);
    }
}
