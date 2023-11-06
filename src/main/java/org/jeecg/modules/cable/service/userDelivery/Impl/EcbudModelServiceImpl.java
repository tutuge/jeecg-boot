package org.jeecg.modules.cable.service.userDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudModelMapper;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcbudModelServiceImpl implements EcbudModelService {
    @Resource
    EcbudModelMapper ecbudModelMapper;

    @Override
    public Integer insert(EcbudModel record) {
        return ecbudModelMapper.insert(record);
    }

    @Override
    public EcbudModel getObject(EcbudModel record) {
        return ecbudModelMapper.getObject(record);
    }

    @Override
    public Integer update(EcbudModel record) {
        return ecbudModelMapper.update(record);
    }

    @Override
    public Integer delete(EcbudModel record) {
        return ecbudModelMapper.delete(record);
    }

}
