package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuInfillingDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.service.userEcable.EcbuInfillingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuInfillingServiceImpl implements EcbuInfillingService {
    @Resource
    EcbuInfillingDao ecbuInfillingDao;

    @Override
    public EcbuInfilling getObject(EcbuInfilling record) {
        return ecbuInfillingDao.getObject(record);
    }

    //insert
    @Override
    public int insert(EcbuInfilling record) {
        return ecbuInfillingDao.insert(record);
    }

    @Override
    public int update(EcbuInfilling record) {
        return ecbuInfillingDao.update(record);
    }

    @Override
    public List<EcbuInfilling> getList(EcbuInfilling record) {
        return ecbuInfillingDao.getList(record);
    }

    @Override
    public int delete(EcbuInfilling record) {
        return ecbuInfillingDao.delete(record);
    }
}
