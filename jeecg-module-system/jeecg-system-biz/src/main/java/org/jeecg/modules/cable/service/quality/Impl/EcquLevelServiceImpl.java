package org.jeecg.modules.cable.service.quality.Impl;

import org.jeecg.modules.cable.mapper.dao.quality.EcquLevelDao;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcquLevelServiceImpl implements EcquLevelService {
    @Resource
    EcquLevelDao ecquLevelDao;

    @Override
    public List<EcquLevel> getList(EcquLevel record) {
        return ecquLevelDao.getList(record);
    }

    @Override
    public long getCount(EcquLevel record) {
        return ecquLevelDao.getCount(record);
    }

    @Override
    public EcquLevel getObject(EcquLevel record) {
        return ecquLevelDao.getObject(record);
    }

    @Override
    public Integer insert(EcquLevel record) {
        return ecquLevelDao.insert(record);
    }

    @Override
    public Integer update(EcquLevel record) {
        return ecquLevelDao.update(record);
    }

    @Override
    public Integer delete(EcquLevel record) {
        return ecquLevelDao.delete(record);
    }

}
