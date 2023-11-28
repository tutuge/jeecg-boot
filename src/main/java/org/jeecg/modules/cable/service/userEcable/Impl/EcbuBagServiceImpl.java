package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuBagMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_BAG_CACHE;

@Service
public class EcbuBagServiceImpl implements EcbuBagService {
    @Resource
    EcbuBagMapper ecbuBagMapper;

    @Override
    public EcbuBag getObject(EcbuBag record) {
        return ecbuBagMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuBag record) {
        return ecbuBagMapper.insert(record);
    }

    @Override
    public Integer update(EcbuBag record) {
        List<EcbuBag> list = ecbuBagMapper.getList(record);
        for (EcbuBag ecbuBag : list) {
            CacheUtils.evict(CUSTOMER_BAG_CACHE, ecbuBag.getEcbubId());
        }
        return ecbuBagMapper.updateById(record);
    }

    @Override
    public List<EcbuBag> getList(EcbuBag record) {
        return ecbuBagMapper.getList(record);
    }

    @Override
    public Integer deleteByEcCompanyId(EcbuBag record) {
        List<EcbuBag> list = ecbuBagMapper.getList(record);
        for (EcbuBag ecbuBag : list) {
            CacheUtils.evict(CUSTOMER_BAG_CACHE, ecbuBag.getEcbubId());
        }
        return ecbuBagMapper.deleteByEcCompanyId(record);
    }

    @Cacheable(value = {CUSTOMER_BAG_CACHE}, key = "#bagId", unless = "#result == null ")
    @Override
    public EcbuBag getObjectById(Integer bagId) {
        return ecbuBagMapper.selectById(bagId);
    }
}
