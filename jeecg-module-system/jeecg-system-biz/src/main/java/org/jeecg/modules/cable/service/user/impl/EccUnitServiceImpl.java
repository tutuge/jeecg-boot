package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EccUnitDao;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.service.user.EccUnitService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EccUnitServiceImpl implements EccUnitService {
    @Resource
    EccUnitDao eccUnitDao;

    @Override
    public EccUnit getObject(EccUnit record) {
        return eccUnitDao.getObject(record);
    }

    @Override
    public List<EccUnit> getList(EccUnit record) {
        return eccUnitDao.getList(record);
    }

    @Override
    public long getCount(EccUnit record) {
        return eccUnitDao.getCount(record);
    }

    @Override
    public int insert(EccUnit record) {
        return eccUnitDao.insert(record);
    }

    @Override
    public int update(EccUnit record) {
        return eccUnitDao.update(record);
    }

    @Override
    public int delete(EccUnit record) {
        return eccUnitDao.delete(record);
    }
}
