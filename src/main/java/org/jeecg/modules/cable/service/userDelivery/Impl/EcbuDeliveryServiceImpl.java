package org.jeecg.modules.cable.service.userDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbuDeliveryMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbuDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuDeliveryServiceImpl implements EcbuDeliveryService {
    @Resource
    EcbuDeliveryMapper ecbuDeliveryMapper;

    @Override
    public List<EcbuDelivery> getList(EcbuDelivery record) {
        return ecbuDeliveryMapper.getList(record);
    }

    @Override
    public long getCount(EcbuDelivery record) {
        return ecbuDeliveryMapper.getCount(record);
    }

    @Override
    public EcbuDelivery getObject(EcbuDelivery record) {
        return ecbuDeliveryMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuDelivery record) {
        return ecbuDeliveryMapper.insert(record);
    }

    @Override
    public Integer update(EcbuDelivery record) {
        return ecbuDeliveryMapper.update(record);
    }

    @Override
    public Integer delete(EcbuDelivery record) {
        return ecbuDeliveryMapper.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbuDelivery> getListGreaterThanSortId(EcbuDelivery record) {
        return ecbuDeliveryMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassDeliveryName
    @Override
    public EcbuDelivery getObjectPassDeliveryName(EcbuDelivery record) {
        return ecbuDeliveryMapper.getObjectPassDeliveryName(record);
    }


    @Override
    public EcbuDelivery getLatestObject(EcbuDelivery record) {
        return ecbuDeliveryMapper.getLatestObject(record);
    }

    @Override
    public void reduceSort(Integer ecCompanyId, Integer sortId) {
        ecbuDeliveryMapper.reduceSort(ecCompanyId, sortId);
    }
}
