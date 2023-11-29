package org.jeecg.modules.cable.service.systemDelivery.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdWeight;
import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdModelMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcbdWeightService;
import org.springframework.stereotype.Service;

@Service
public class EcbdWeightServiceImpl implements EcbdWeightService {
    @Resource
    EcbdModelMapper ecbdModelMapper;

    @Override
    public EcbdWeight getObject(Integer ecbdId) {
        LambdaQueryWrapper<EcbdWeight> eq = Wrappers.lambdaQuery(EcbdWeight.class).eq(EcbdWeight::getEcbdId, ecbdId);
        return ecbdModelMapper.selectOne(eq);
    }

    @Override
    public Integer insert(EcbdWeight record) {
        return ecbdModelMapper.insert(record);
    }

    @Override
    public Integer update(EcbdWeight record) {
        return ecbdModelMapper.updateById(record);
    }

}
