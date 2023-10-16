package org.jeecg.modules.cable.service.systemDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdModelDao;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.service.systemDelivery.EcbdModelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcbdModelServiceImpl implements EcbdModelService {
    @Resource
    EcbdModelDao ecbdModelDao;
    @Override
    public EcbdModel getObject(EcbdModel record)
    {
        return ecbdModelDao.getObject(record);
    }
    @Override
    public int insert(EcbdModel record) {
        return ecbdModelDao.insert(record);
    }

    @Override
    public int update(EcbdModel record) {
        return ecbdModelDao.update(record);
    }

}
