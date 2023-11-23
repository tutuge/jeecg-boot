package org.jeecg.modules.cable.service.userCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcbulUnitMapper;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbulUnitServiceImpl implements EcbulUnitService {
    @Resource
    EcbulUnitMapper ecbulUnitMapper;

    @Override
    public List<EcbulUnit> getList(EcbulUnit record) {
        return ecbulUnitMapper.getList(record);
    }

    @Override
    public long getCount(EcbulUnit record) {
        return ecbulUnitMapper.getCount(record);
    }


    @Cacheable(value = CustomerCacheConstant.CUSTOMER_UNIT_CACHE, key = "#ecbuluId", unless = "#result == null ")
    @Override
    public EcbulUnit getObjectById(Integer ecbuluId) {
        return ecbulUnitMapper.selectById(ecbuluId);
    }

    @Override
    public EcbulUnit getObject(EcbulUnit record) {
        return ecbulUnitMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbulUnit record) {
        return ecbulUnitMapper.insert(record);
    }

    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_UNIT_CACHE}, key = "#record.ecbuluId")
    @Override
    public Integer update(EcbulUnit record) {
        return ecbulUnitMapper.update(record);
    }

    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_UNIT_CACHE}, key = "#record.ecbuluId")
    @Override
    public Integer delete(EcbulUnit record) {
        return ecbulUnitMapper.delete(record);
    }


    @Override
    public List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record) {
        return ecbulUnitMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassProvinceName
    @Override
    public EcbulUnit getObjectPassLengthName(EcbulUnit record) {
        return ecbulUnitMapper.getObjectPassLengthName(record);
    }


    @Override
    public EcbulUnit getLatestObject(EcbulUnit record) {
        return ecbulUnitMapper.getLatestObject(record);
    }

    @Override
    public void reduceSort(Integer ecbuluId, Integer sortId) {
        ecbulUnitMapper.reduceSort(ecbuluId, sortId);
    }

}
