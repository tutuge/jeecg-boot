package org.jeecg.modules.cable.service.systemCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.systemCommon.EcblUnitDao;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.service.systemCommon.EcblUnitService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcblUnitServiceImpl implements EcblUnitService {
    @Resource
    EcblUnitDao ecblUnitDao;

    @Override
    public List<EcblUnit> getList(EcblUnit record) {
        return ecblUnitDao.getList(record);
    }

    @Override
    public long getCount(EcblUnit record) {
        return ecblUnitDao.getCount(record);
    }

    @Override
    public EcblUnit getObject(EcblUnit record) {
        return ecblUnitDao.getObject(record);
    }

    @Override
    public int insert(EcblUnit record) {
        return ecblUnitDao.insert(record);
    }

    @Override
    public int update(EcblUnit record) {
        return ecblUnitDao.update(record);
    }

    @Override
    public int delete(EcblUnit record) {
        return ecblUnitDao.delete(record);
    }
}
