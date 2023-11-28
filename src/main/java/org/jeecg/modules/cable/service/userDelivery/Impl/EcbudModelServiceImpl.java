package org.jeecg.modules.cable.service.userDelivery.Impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudModelMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_WEIGHT_CACHE;

@Service
public class EcbudModelServiceImpl implements EcbudModelService {
    @Resource
    EcbudModelMapper ecbudModelMapper;

    @Override
    public Integer insert(EcbudModel record) {
        record.setAddTime(new Date());
        return ecbudModelMapper.insert(record);
    }


    @Cacheable(value = {CUSTOMER_WEIGHT_CACHE}, key = "#record.ecbudId", unless = "#result == null ")
    @Override
    public EcbudModel getObject(EcbudModel record) {
        return ecbudModelMapper.getObject(record);
    }

    @CacheEvict(value = {CUSTOMER_WEIGHT_CACHE}, key = "#record.ecbudId")
    @Override
    public Integer update(EcbudModel record) {
        record.setUpdateTime(new Date());
        return ecbudModelMapper.updateByEcbudId(record);
    }


    @Override
    public Integer delete(EcbudModel record) {
        LambdaQueryWrapper<EcbudModel> eq = Wrappers.lambdaQuery(EcbudModel.class)
                .eq(ObjUtil.isNotNull(record.getEcbudId()), EcbudModel::getEcbudId, record.getEcbudId())
                .eq(ObjUtil.isNotNull(record.getEcbudmId()), EcbudModel::getEcbudmId, record.getEcbudmId());
        List<EcbudModel> ecbudModels = ecbudModelMapper.selectList(eq);
        for (EcbudModel model : ecbudModels) {
            CacheUtils.evict(CUSTOMER_WEIGHT_CACHE, model.getEcbudId());
        }
        return ecbudModelMapper.deleteByEcbudId(record);
    }

}
