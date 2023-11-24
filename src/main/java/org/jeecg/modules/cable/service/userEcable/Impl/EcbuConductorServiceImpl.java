package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuConductorMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_CONDUCTOR_CACHE;
import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_STEEL_BAND_CACHE;

@Service
public class EcbuConductorServiceImpl implements EcbuConductorService {
    @Resource
    EcbuConductorMapper ecbuConductorMapper;

    @Override
    public EcbuConductor getObject(EcbuConductor record) {
        return ecbuConductorMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuConductor record) {
        return ecbuConductorMapper.insert(record);
    }

    @Override
    public Integer update(EcbuConductor record) {
        List<EcbuConductor> list = ecbuConductorMapper.getList(record);
        for (EcbuConductor ecbuConductor : list) {
            CacheUtils.evict(CUSTOMER_CONDUCTOR_CACHE, ecbuConductor.getEcbucId());
        }
        return ecbuConductorMapper.update(record);
    }

    @Override
    public List<EcbuConductor> getList(EcbuConductor record) {
        return ecbuConductorMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuConductor record) {
        List<EcbuConductor> list = ecbuConductorMapper.getList(record);
        for (EcbuConductor ecbuConductor : list) {
            CacheUtils.evict(CUSTOMER_CONDUCTOR_CACHE, ecbuConductor.getEcbucId());
        }
        return ecbuConductorMapper.delete(record);
    }


    @Cacheable(value = {CUSTOMER_CONDUCTOR_CACHE}, key = "#ecbucId", unless = "#result == null ")
    @Override
    public EcbuConductor getObjectById(Integer ecbucId) {
        return ecbuConductorMapper.selectById(ecbucId);
    }
}
