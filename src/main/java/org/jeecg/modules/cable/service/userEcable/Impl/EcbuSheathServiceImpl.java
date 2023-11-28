package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSheathMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_SHEATH_CACHE;

@Service
public class EcbuSheathServiceImpl implements EcbuSheathService {
    @Resource
    EcbuSheathMapper ecbuSheathMapper;


    @Override
    public EcbuSheath getObject(EcbuSheath record) {
        return ecbuSheathMapper.getObject(record);
    }

    @Cacheable(value = {CUSTOMER_SHEATH_CACHE}, key = "#ecbusId", unless = "#result == null ")
    @Override
    public EcbuSheath getObjectById(Integer ecbusId) {
        return ecbuSheathMapper.selectById(ecbusId);
    }


    @Override
    public Integer insert(EcbuSheath record) {
        return ecbuSheathMapper.insert(record);
    }


    @Override
    public Integer updateById(EcbuSheath record) {
        EcbuSheath object = getObject(record);
        CacheUtils.evict(CUSTOMER_SHEATH_CACHE, object.getEcbusId());
        return ecbuSheathMapper.updateById(record);
    }

    @Override
    public List<EcbuSheath> getList(EcbuSheath record) {
        return ecbuSheathMapper.getList(record);
    }


    @Override
    public Integer deleteByCompanyId(EcbuSheath record) {
        EcbuSheath sheaths = new EcbuSheath();
        sheaths.setEcCompanyId(record.getEcCompanyId());
        List<EcbuSheath> list = getList(sheaths);
        for (EcbuSheath sheath : list) {
            CacheUtils.evict(CUSTOMER_SHEATH_CACHE, sheath.getEcbusId());
        }
        return ecbuSheathMapper.deleteByCompanyId(record);
    }
}
