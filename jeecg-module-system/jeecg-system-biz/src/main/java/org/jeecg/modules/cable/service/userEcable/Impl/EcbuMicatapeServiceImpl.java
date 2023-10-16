package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMicatapeDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;
import org.jeecg.modules.cable.service.userEcable.EcbuMicatapeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuMicatapeServiceImpl implements EcbuMicatapeService {
    @Resource
    EcbuMicatapeDao ecbuMicatapeDao;

    @Override
    public EcbuMicatape getObject(EcbuMicatape record) {
        return ecbuMicatapeDao.getObject(record);
    }

    @Override
    public int insert(EcbuMicatape record) {
        return ecbuMicatapeDao.insert(record);
    }

    @Override
    public int update(EcbuMicatape record) {
        return ecbuMicatapeDao.update(record);
    }

    @Override
    public List<EcbuMicatape> getList(EcbuMicatape record) {
        return ecbuMicatapeDao.getList(record);
    }

    @Override
    public int delete(EcbuMicatape record) {
        return ecbuMicatapeDao.delete(record);
    }
}
