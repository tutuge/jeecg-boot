package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSheathMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuSheathServiceImpl implements EcbuSheathService {
    @Resource
    EcbuSheathMapper ecbuSheathMapper;


    @Override
    public EcbuSheath getObject(EcbuSheath record) {
        return ecbuSheathMapper.getObject(record);
    }

    @Cacheable(value = {CustomerCacheConstant.CUSTOMER_SHEATH_CACHE}, key = "#ecbusId", unless = "#result == null ")
    @Override
    public EcbuSheath getObjectById(Integer ecbusId) {
        return ecbuSheathMapper.selectById(ecbusId);
    }


    @Override
    public Integer insert(EcbuSheath record) {
        return ecbuSheathMapper.insert(record);
    }


    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_SHEATH_CACHE}, key = "#record.ecbusId")
    @Override
    public Integer update(EcbuSheath record) {
        return ecbuSheathMapper.update(record);
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
        for (EcbuSheath sheath:list){
            CacheUtils.evict(CustomerCacheConstant.CUSTOMER_SHEATH_CACHE,sheath.getEcbusId());
        }
        return ecbuSheathMapper.deleteByCompanyId(record);
    }
}
