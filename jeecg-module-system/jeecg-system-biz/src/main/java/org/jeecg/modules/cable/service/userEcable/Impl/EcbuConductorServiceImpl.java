package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuConductorDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuConductorServiceImpl implements EcbuConductorService {
    @Resource
    EcbuConductorDao ecbuConductorDao;

    @Override
    public EcbuConductor getObject(EcbuConductor record) {
        return ecbuConductorDao.getObject(record);
    }

    @Override
    public int insert(EcbuConductor record) {
        return ecbuConductorDao.insert(record);
    }

    @Override
    public int update(EcbuConductor record) {
        return ecbuConductorDao.update(record);
    }

    @Override
    public List<EcbuConductor> getList(EcbuConductor record) {
        return ecbuConductorDao.getList(record);
    }

    @Override
    public int delete(EcbuConductor record) {
        return ecbuConductorDao.delete(record);
    }
}
