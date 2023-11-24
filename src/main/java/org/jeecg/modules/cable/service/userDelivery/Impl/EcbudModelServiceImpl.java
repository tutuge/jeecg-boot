package org.jeecg.modules.cable.service.userDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudModelMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EcbudModelServiceImpl implements EcbudModelService {
    @Resource
    EcbudModelMapper ecbudModelMapper;

    @Override
    public Integer insert(EcbudModel record) {
        return ecbudModelMapper.insert(record);
    }


    @Cacheable(value = {CustomerCacheConstant.CUSTOMER_WEIGHT_CACHE}, key = "#record.ecbudId", unless = "#result == null ")
    @Override
    public EcbudModel getObject(EcbudModel record) {
        return ecbudModelMapper.getObject(record);
    }

    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_WEIGHT_CACHE}, key = "#record.ecbudId")
    @Override
    public Integer update(EcbudModel record) {
        return ecbudModelMapper.update(record);
    }


    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_WEIGHT_CACHE}, key = "#record.ecbudId")
    @Override
    public Integer delete(EcbudModel record) {
        return ecbudModelMapper.delete(record);
    }

}
