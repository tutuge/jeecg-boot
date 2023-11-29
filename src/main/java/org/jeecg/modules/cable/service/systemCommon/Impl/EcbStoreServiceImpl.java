package org.jeecg.modules.cable.service.systemCommon.Impl;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcbStoreMapper;
import org.jeecg.modules.cable.service.systemCommon.EcbStoreService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.SYSTEM_STORE_CACHE;

@Service
public class EcbStoreServiceImpl implements EcbStoreService {
    @Resource
    private EcbStoreMapper ecbStoreMapper;


    @Override
    public List<EcbStore> getList(EcbStore record) {
        return ecbStoreMapper.getList(record);
    }


    @Override
    public long getCount(EcbStore record) {
        return ecbStoreMapper.getCount(record);
    }


    @Override
    public EcbStore getObject(EcbStore record) {
        return ecbStoreMapper.getObject(record);
    }

    @Cacheable(value = {SYSTEM_STORE_CACHE}, key = "#ecbsId", unless = "#result == null ")
    @Override
    public EcbStore getObjectById(Integer ecbsId) {
        return ecbStoreMapper.selectById(ecbsId);
    }

    //getObjectPassStoreName
    @Override
    public EcbStore getObjectPassStoreName(EcbStore record) {
        return ecbStoreMapper.getObjectPassStoreName(record);
    }


    @Override
    public Integer insert(EcbStore record) {
        record.setCreateTime(new Date());
        return ecbStoreMapper.insert(record);
    }


    @Override
    public Integer update(EcbStore record) {
        EcbStore object = getObject(record);
        if (ObjUtil.isNotNull(object)) {
            CacheUtils.evict(SYSTEM_STORE_CACHE, object.getEcbsId());
        }
        record.setUpdateTime(new Date());
        return ecbStoreMapper.updateRecord(record);
    }


    @Override
    public EcbStore getLatestObject(EcbStore record) {
        return ecbStoreMapper.getLatestObject(record);
    }

    @Override
    public Integer delete(EcbStore record) {
        List<EcbStore> list = getList(record);
        for (EcbStore store : list) {
            CacheUtils.evict(SYSTEM_STORE_CACHE, store.getEcbsId());
        }
        return ecbStoreMapper.deleteByIdOrCompanyId(record);
    }


    @Override
    public List<EcbStore> getListGreaterThanSortId(EcbStore record) {
        return ecbStoreMapper.getListGreaterThanSortId(record);
    }

    @Override
    public void reduceSort(Integer sortId) {
        ecbStoreMapper.reduceSort( sortId);
    }

    @Override
    public void updateNotDefault() {
        ecbStoreMapper.updateNotDefault();
    }

}
