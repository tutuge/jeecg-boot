package org.jeecg.modules.cable.service.userDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudModelDao;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcbudModelServiceImpl implements EcbudModelService {
    @Resource
    EcbudModelDao ecbudModelDao;

    @Override
    public int insert(EcbudModel record) {
        return ecbudModelDao.insert(record);
    }

    @Override
    public EcbudModel getObject(EcbudModel record) {
        return ecbudModelDao.getObject(record);
    }

    @Override
    public int update(EcbudModel record) {
        return ecbudModelDao.update(record);
    }

    @Override
    public int delete(EcbudModel record) {
        return ecbudModelDao.delete(record);
    }

}
