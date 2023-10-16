package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcduciPositionDao;
import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.service.userCommon.EcduciPositionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcduciPositionServiceImpl implements EcduciPositionService {
    @Resource
    EcduciPositionDao ecduciPositionDao;

    @Override
    public EcduciPosition getObject(EcduciPosition record) {
        return ecduciPositionDao.getObject(record);
    }

    @Override
    public int insert(EcduciPosition record) {
        return ecduciPositionDao.insert(record);
    }

    @Override
    public int delete(EcduciPosition record) {
        return ecduciPositionDao.delete(record);
    }

    @Override
    public int update(EcduciPosition record) {
        return ecduciPositionDao.update(record);
    }
}
