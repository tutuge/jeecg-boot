package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuInsulationMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_INSULATION_CACHE;

@Service
public class EcbuInsulationServiceImpl implements EcbuInsulationService {
    @Resource
    EcbuInsulationMapper ecbuInsulationMapper;

    @Override
    public EcbuInsulation getObject(EcbuInsulation record) {
        return ecbuInsulationMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuInsulation record) {
        return ecbuInsulationMapper.insert(record);
    }

    @Override
    public Integer update(EcbuInsulation record) {
        List<EcbuInsulation> list = ecbuInsulationMapper.getList(record);
        for (EcbuInsulation insulation : list) {
            CacheUtils.evict(CUSTOMER_INSULATION_CACHE, insulation.getEcbuiId());
        }
        return ecbuInsulationMapper.updateById(record);
    }

    @Override
    public List<EcbuInsulation> getList(EcbuInsulation record) {
        return ecbuInsulationMapper.getList(record);
    }

    @Override
    public Integer deleteByEcCompanyId(EcbuInsulation record) {
        List<EcbuInsulation> list = ecbuInsulationMapper.getList(record);
        for (EcbuInsulation insulation : list) {
            CacheUtils.evict(CUSTOMER_INSULATION_CACHE, insulation.getEcbuiId());
        }
        return ecbuInsulationMapper.deleteByEcCompanyId(record);
    }


    @Cacheable(value = {CUSTOMER_INSULATION_CACHE}, key = "#ecbuiId", unless = "#result == null ")
    @Override
    public EcbuInsulation getObjectById(Integer ecbuiId) {
        return ecbuInsulationMapper.selectById(ecbuiId);
    }
}
