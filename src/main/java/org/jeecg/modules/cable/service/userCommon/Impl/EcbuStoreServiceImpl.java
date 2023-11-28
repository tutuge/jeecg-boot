package org.jeecg.modules.cable.service.userCommon.Impl;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuStoreMapper;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_STORE_CACHE;

@Service
public class EcbuStoreServiceImpl implements EcbuStoreService {
    @Resource
    private EcbuStoreMapper ecbuStoreMapper;


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

    @Cacheable(value = {CUSTOMER_STORE_CACHE}, key = "#ecbusId", unless = "#result == null ")
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
        record.setAddTime(new Date());
        return ecbuStoreMapper.insert(record);
    }


    @Override
    public Integer update(EcbuStore record) {
        EcbuStore object = getObject(record);
        if (ObjUtil.isNotNull(object)) {
            CacheUtils.evict(CUSTOMER_STORE_CACHE, object.getEcbusId());
        }
        record.setUpdateTime(new Date());
        return ecbuStoreMapper.updateRecord(record);
    }


    @Override
    public EcbuStore getLatestObject(EcbuStore record) {
        return ecbuStoreMapper.getLatestObject(record);
    }

    @Override
    public Integer delete(EcbuStore record) {
        List<EcbuStore> list = getList(record);
        for (EcbuStore store : list) {
            CacheUtils.evict(CUSTOMER_STORE_CACHE, store.getEcbusId());
        }
        return ecbuStoreMapper.deleteByIdOrCompanyId(record);
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
