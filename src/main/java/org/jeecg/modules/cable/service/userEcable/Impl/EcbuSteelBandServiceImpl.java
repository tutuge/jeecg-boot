package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSteelBandMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_STEEL_BAND_CACHE;

@Service
public class EcbuSteelBandServiceImpl implements EcbuSteelbandService {
    @Resource
    private EcbuSteelBandMapper ecbuSteelBandMapper;

    @Override
    public EcbuSteelband getObject(EcbuSteelband record) {
        return ecbuSteelBandMapper.getObject(record);
    }

    @Cacheable(value = {CUSTOMER_STEEL_BAND_CACHE}, key = "#ecbusId", unless = "#result == null ")
    @Override
    public EcbuSteelband getObjectById(Integer ecbusId) {
        return ecbuSteelBandMapper.selectById(ecbusId);
    }

    @Override
    public Integer insert(EcbuSteelband record) {
        return ecbuSteelBandMapper.insert(record);
    }

    @Override
    public Integer update(EcbuSteelband record) {
        List<EcbuSteelband> list = ecbuSteelBandMapper.getList(record);
        for (EcbuSteelband ecbuSteelband : list) {
            CacheUtils.evict(CUSTOMER_STEEL_BAND_CACHE, ecbuSteelband.getEcbusId());
        }
        return ecbuSteelBandMapper.updateById(record);
    }

    @Override
    public List<EcbuSteelband> getList(EcbuSteelband record) {
        return ecbuSteelBandMapper.getList(record);
    }

    @Override
    public Integer deleteByEcCompanyId(EcbuSteelband record) {
        List<EcbuSteelband> list = ecbuSteelBandMapper.getList(record);
        for (EcbuSteelband ecbuSteelband : list) {
            CacheUtils.evict(CUSTOMER_STEEL_BAND_CACHE, ecbuSteelband.getEcbusId());
        }
        return ecbuSteelBandMapper.deleteByEcCompanyId(record);
    }
}
