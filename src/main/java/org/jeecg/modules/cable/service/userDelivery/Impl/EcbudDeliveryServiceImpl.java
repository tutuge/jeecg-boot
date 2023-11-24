package org.jeecg.modules.cable.service.userDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudDeliveryMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import org.springframework.stereotype.Service;

@Service
public class EcbudDeliveryServiceImpl implements EcbudDeliveryService {
    @Resource
    EcbudDeliveryMapper ecbudDeliveryMapper;


    @Override
    public EcbudDelivery getObject(EcbudDelivery record) {
        return ecbudDeliveryMapper.getObject(record);
    }


    @Override
    public Integer insert(EcbudDelivery record) {
        return ecbudDeliveryMapper.insert(record);
    }

    @Override
    public Integer updateByPrimaryKeySelective(EcbudDelivery record) {
        return ecbudDeliveryMapper.updateById(record);
    }

}
