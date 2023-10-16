package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbInfillingDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.service.systemEcable.EcbInfillingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbInfillingServiceImpl implements EcbInfillingService {
    @Resource
    EcbInfillingDao ecbInfillingDao;

    @Override
    public List<EcbInfilling> getList(EcbInfilling record) {//插入

        return ecbInfillingDao.getList(record);
    }

    @Override
    public List<EcbInfilling> getListStart(EcbInfilling record) {
        return ecbInfillingDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbInfillingDao.getCount();
    }

    @Override
    public EcbInfilling getObject(EcbInfilling record) {
        return ecbInfillingDao.getObject(record);
    }
}
