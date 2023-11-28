package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMicaTapeMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuMicaTapeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_MICA_TAPE_CACHE;

@Service
public class EcbuMicaTapeServiceImpl implements EcbuMicaTapeService {
    @Resource
    private EcbuMicaTapeMapper ecbuMicaTapeMapper;

    @Override
    public EcbuMicaTape getObject(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.insert(record);
    }

    @Override
    public Integer update(EcbuMicaTape record) {
        List<EcbuMicaTape> list = ecbuMicaTapeMapper.getList(record);
        for (EcbuMicaTape ecbuMicaTape : list) {
            CacheUtils.evict(CUSTOMER_MICA_TAPE_CACHE, ecbuMicaTape.getEcbumId());
        }
        return ecbuMicaTapeMapper.updateById(record);
    }

    @Override
    public List<EcbuMicaTape> getList(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuMicaTape record) {
        List<EcbuMicaTape> list = ecbuMicaTapeMapper.getList(record);
        for (EcbuMicaTape ecbuMicaTape : list) {
            CacheUtils.evict(CUSTOMER_MICA_TAPE_CACHE, ecbuMicaTape.getEcbumId());
        }
        return ecbuMicaTapeMapper.deleteByCompanyId(record);
    }


    @Cacheable(value = {CUSTOMER_MICA_TAPE_CACHE}, key = "#ecbumId", unless = "#result == null ")
    @Override
    public EcbuMicaTape getObjectById(Integer ecbumId) {
        return ecbuMicaTapeMapper.selectById(ecbumId);
    }
}
