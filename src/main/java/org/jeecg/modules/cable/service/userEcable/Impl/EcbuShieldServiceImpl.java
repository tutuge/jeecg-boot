package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuShieldMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_SHIELD_CACHE;

@Service
public class EcbuShieldServiceImpl implements EcbuShieldService {
    @Resource
    private EcbuShieldMapper ecbuShieldMapper;

    @Override
    public EcbuShield getObject(EcbuShield record) {
        return ecbuShieldMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuShield record) {
        return ecbuShieldMapper.insert(record);
    }

    @Override
    public Integer update(EcbuShield record) {
        List<EcbuShield> list = ecbuShieldMapper.getList(record);
        for (EcbuShield ecbuShield : list) {
            CacheUtils.evict(CUSTOMER_SHIELD_CACHE, ecbuShield.getEcbusId());
        }
        return ecbuShieldMapper.updateById(record);
    }

    @Override
    public List<EcbuShield> getList(EcbuShield record) {
        return ecbuShieldMapper.getList(record);
    }

    @Override
    public Integer deleteByEcCompanyId(EcbuShield record) {
        List<EcbuShield> list = ecbuShieldMapper.getList(record);
        for (EcbuShield ecbuShield : list) {
            CacheUtils.evict(CUSTOMER_SHIELD_CACHE, ecbuShield.getEcbusId());
        }
        return ecbuShieldMapper.deleteByEcCompanyId(record);
    }

    @Cacheable(value = {CUSTOMER_SHIELD_CACHE}, key = "#ecbusbId", unless = "#result == null ")
    @Override
    public EcbuShield getObjectById(Integer ecbusbId) {
        return ecbuShieldMapper.selectById(ecbusbId);
    }
}
