package org.jeecg.modules.cable.service.systemDelivery.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdModelMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcbdModelService;
import org.springframework.stereotype.Service;

@Service
public class EcbdModelServiceImpl implements EcbdModelService {
    @Resource
    EcbdModelMapper ecbdModelMapper;

    @Override
    public EcbdModel getObject(Integer ecbdId) {
        LambdaQueryWrapper<EcbdModel> eq = Wrappers.lambdaQuery(EcbdModel.class).eq(EcbdModel::getEcbdId, ecbdId);
        return ecbdModelMapper.selectOne(eq);
    }

    @Override
    public Integer insert(EcbdModel record) {
        return ecbdModelMapper.insert(record);
    }

    @Override
    public Integer update(EcbdModel record) {
        return ecbdModelMapper.updateById(record);
    }

}
