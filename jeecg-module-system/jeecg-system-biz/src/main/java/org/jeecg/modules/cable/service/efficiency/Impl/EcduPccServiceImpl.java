package org.jeecg.modules.cable.service.efficiency.Impl;

import org.jeecg.modules.cable.mapper.dao.efficiency.EcduPccDao;
import org.jeecg.modules.cable.entity.efficiency.EcduPcc;
import org.jeecg.modules.cable.service.efficiency.EcduPccService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcduPccServiceImpl implements EcduPccService {
    @Resource
    EcduPccDao ecduPccDao;

    //getObject
    @Override
    public EcduPcc getObject(EcduPcc record) {
        return ecduPccDao.getObject(record);
    }

    //insert
    @Override
    public int insert(EcduPcc record) {
        return ecduPccDao.insert(record);
    }

    //deletePassEcCompanyIdAndTypeId
    @Override
    public int delete(EcduPcc record) {
        return ecduPccDao.delete(record);
    }

}
