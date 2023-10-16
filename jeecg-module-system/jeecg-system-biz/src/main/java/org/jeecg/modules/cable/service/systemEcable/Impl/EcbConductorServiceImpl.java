package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbConductorDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.service.systemEcable.EcbConductorService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbConductorServiceImpl implements EcbConductorService {
    @Resource
    EcbConductorDao ecbConductorDao;

    @Override
    public List<EcbConductor> getList(EcbConductor record) {//插入

        return ecbConductorDao.getList(record);
    }

    @Override
    public List<EcbConductor> getListStart(EcbConductor record) {
        return ecbConductorDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbConductorDao.getCount();
    }

    @Override
    public EcbConductor getObject(EcbConductor record) {
        return ecbConductorDao.getObject(record);
    }
}
