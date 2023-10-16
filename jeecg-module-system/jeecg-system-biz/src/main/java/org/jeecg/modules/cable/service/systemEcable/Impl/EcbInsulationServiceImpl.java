package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbInsulationDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.service.systemEcable.EcbInsulationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbInsulationServiceImpl implements EcbInsulationService {
    @Resource
    EcbInsulationDao ecbInsulationDao;

    @Override
    public List<EcbInsulation> getList(EcbInsulation record) {//插入

        return ecbInsulationDao.getList(record);
    }

    @Override
    public List<EcbInsulation> getListStart(EcbInsulation record) {
        return ecbInsulationDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbInsulationDao.getCount();
    }

    @Override
    public EcbInsulation getObject(EcbInsulation record) {
        return ecbInsulationDao.getObject(record);
    }
}
