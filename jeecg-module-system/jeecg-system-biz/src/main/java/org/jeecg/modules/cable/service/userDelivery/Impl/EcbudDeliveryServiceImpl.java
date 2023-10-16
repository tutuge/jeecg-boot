package org.jeecg.modules.cable.service.userDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudDeliveryDao;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcbudDeliveryServiceImpl implements EcbudDeliveryService {
    @Resource
    EcbudDeliveryDao ecbudDeliveryDao;

    //getObject
    @Override
    public EcbudDelivery getObject(EcbudDelivery record) {
        return ecbudDeliveryDao.getObject(record);
    }

    //insert
    @Override
    public int insert(EcbudDelivery record) {
        return ecbudDeliveryDao.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public int updateByPrimaryKeySelective(EcbudDelivery record) {
        return ecbudDeliveryDao.updateByPrimaryKeySelective(record);
    }

}
