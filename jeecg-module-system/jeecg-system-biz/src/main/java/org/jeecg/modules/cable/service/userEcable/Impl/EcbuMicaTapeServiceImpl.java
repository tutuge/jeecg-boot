package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMicaTapeDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.jeecg.modules.cable.service.userEcable.EcbuMicaTapeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuMicaTapeServiceImpl implements EcbuMicaTapeService {
    @Resource
    EcbuMicaTapeDao ecbuMicaTapeDao;

    @Override
    public EcbuMicaTape getObject(EcbuMicaTape record) {
        return ecbuMicaTapeDao.getObject(record);
    }

    @Override
    public Integer insert(EcbuMicaTape record) {
        return ecbuMicaTapeDao.insert(record);
    }

    @Override
    public Integer update(EcbuMicaTape record) {
        return ecbuMicaTapeDao.update(record);
    }

    @Override
    public List<EcbuMicaTape> getList(EcbuMicaTape record) {
        return ecbuMicaTapeDao.getList(record);
    }

    @Override
    public Integer delete(EcbuMicaTape record) {
        return ecbuMicaTapeDao.delete(record);
    }
}
