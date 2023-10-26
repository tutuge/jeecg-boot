package org.jeecg.modules.cable.service.userDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbuDeliveryDao;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.service.userDelivery.EcbuDeliveryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuDeliveryServiceImpl implements EcbuDeliveryService {
    @Resource
    EcbuDeliveryDao ecbuDeliveryDao;

    @Override
    public List<EcbuDelivery> getList(EcbuDelivery record) {
        return ecbuDeliveryDao.getList(record);
    }

    @Override
    public long getCount(EcbuDelivery record) {
        return ecbuDeliveryDao.getCount(record);
    }

    @Override
    public EcbuDelivery getObject(EcbuDelivery record) {
        return ecbuDeliveryDao.getObject(record);
    }

    @Override
    public Integer insert(EcbuDelivery record) {
        return ecbuDeliveryDao.insert(record);
    }

    @Override
    public Integer update(EcbuDelivery record) {
        return ecbuDeliveryDao.update(record);
    }

    @Override
    public Integer delete(EcbuDelivery record) {
        return ecbuDeliveryDao.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbuDelivery> getListGreaterThanSortId(EcbuDelivery record) {
        return ecbuDeliveryDao.getListGreaterThanSortId(record);
    }

    //getObjectPassDeliveryName
    @Override
    public EcbuDelivery getObjectPassDeliveryName(EcbuDelivery record) {
        return ecbuDeliveryDao.getObjectPassDeliveryName(record);
    }

    //getLatestObject
    @Override
    public EcbuDelivery getLatestObject(EcbuDelivery record) {
        return ecbuDeliveryDao.getLatestObject(record);
    }

}
