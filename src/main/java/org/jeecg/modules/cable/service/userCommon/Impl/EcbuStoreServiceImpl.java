package org.jeecg.modules.cable.service.userCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuStoreMapper;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuStoreServiceImpl implements EcbuStoreService {
    @Resource
    EcbuStoreMapper ecbuStoreMapper;


    @Override
    public List<EcbuStore> getList(EcbuStore record) {
        return ecbuStoreMapper.getList(record);
    }


    @Override
    public long getCount(EcbuStore record) {
        return ecbuStoreMapper.getCount(record);
    }


    @Override
    public EcbuStore getObject(EcbuStore record) {
        return ecbuStoreMapper.getObject(record);
    }

    @Cacheable(value = {CustomerCacheConstant.CUSTOMER_STORE_CACHE}, key = "#ecbusId", unless = "#result == null ")
    @Override
    public EcbuStore getObjectById(Integer ecbusId) {
        return ecbuStoreMapper.selectById(ecbusId);
    }

    //getObjectPassStoreName
    @Override
    public EcbuStore getObjectPassStoreName(EcbuStore record) {
        return ecbuStoreMapper.getObjectPassStoreName(record);
    }


    @Override
    public Integer insert(EcbuStore record) {
        return ecbuStoreMapper.insert(record);
    }


    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_STORE_CACHE}, key = "#record.ecbusId")
    @Override
    public Integer update(EcbuStore record) {
        return ecbuStoreMapper.update(record);
    }


    @Override
    public EcbuStore getLatestObject(EcbuStore record) {
        return ecbuStoreMapper.getLatestObject(record);
    }

    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_STORE_CACHE}, key = "#record.ecbusId")
    @Override
    public Integer delete(EcbuStore record) {
        return ecbuStoreMapper.delete(record);
    }


    @Override
    public List<EcbuStore> getListGreaterThanSortId(EcbuStore record) {
        return ecbuStoreMapper.getListGreaterThanSortId(record);
    }

    //updateNotDefaultPassEcCompanyId
    @Override
    public Integer updateNotDefaultPassEcCompanyId(EcbuStore record) {
        return ecbuStoreMapper.updateNotDefaultPassEcCompanyId(record);
    }

    @Override
    public void reduceSort(Integer ecCompanyId, Integer sortId) {
        ecbuStoreMapper.reduceSort(ecCompanyId, sortId);
    }

}
