package org.jeecg.modules.cable.service.userDelivery.Impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudWeightMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbudWeightService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_WEIGHT_CACHE;

@Service
public class EcbudWeightServiceImpl implements EcbudWeightService {
    @Resource
    EcbudWeightMapper ecbudWeightMapper;

    @Override
    public Integer insert(EcbudWeight record) {
        record.setAddTime(new Date());
        return ecbudWeightMapper.insert(record);
    }


    @Cacheable(value = {CUSTOMER_WEIGHT_CACHE}, key = "#record.ecbudId", unless = "#result == null ")
    @Override
    public EcbudWeight getObject(EcbudWeight record) {
        return ecbudWeightMapper.getObject(record);
    }

    @CacheEvict(value = {CUSTOMER_WEIGHT_CACHE}, key = "#record.ecbudId")
    @Override
    public Integer update(EcbudWeight record) {
        record.setUpdateTime(new Date());
        return ecbudWeightMapper.updateByEcbudId(record);
    }


    @Override
    public Integer delete(EcbudWeight record) {
        LambdaQueryWrapper<EcbudWeight> eq = Wrappers.lambdaQuery(EcbudWeight.class)
                .eq(ObjUtil.isNotNull(record.getEcbudId()), EcbudWeight::getEcbudId, record.getEcbudId())
                .eq(ObjUtil.isNotNull(record.getEcbudmId()), EcbudWeight::getEcbudmId, record.getEcbudmId());
        List<EcbudWeight> ecbudWeights = ecbudWeightMapper.selectList(eq);
        for (EcbudWeight model : ecbudWeights) {
            CacheUtils.evict(CUSTOMER_WEIGHT_CACHE, model.getEcbudId());
        }
        return ecbudWeightMapper.deleteByEcbudId(record);
    }

}
