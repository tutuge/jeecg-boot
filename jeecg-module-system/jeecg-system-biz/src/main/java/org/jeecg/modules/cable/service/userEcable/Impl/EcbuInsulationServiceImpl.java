package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuInsulationDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuInsulationServiceImpl implements EcbuInsulationService {
    @Resource
    EcbuInsulationDao ecbuInsulationDao;

    @Override
    public EcbuInsulation getObject(EcbuInsulation record) {
        return ecbuInsulationDao.getObject(record);
    }

    @Override
    public Integer insert(EcbuInsulation record) {
        return ecbuInsulationDao.insert(record);
    }

    @Override
    public Integer update(EcbuInsulation record) {
        return ecbuInsulationDao.update(record);
    }

    @Override
    public List<EcbuInsulation> getList(EcbuInsulation record) {
        return ecbuInsulationDao.getList(record);
    }

    @Override
    public Integer delete(EcbuInsulation record) {
        return ecbuInsulationDao.delete(record);
    }
}
