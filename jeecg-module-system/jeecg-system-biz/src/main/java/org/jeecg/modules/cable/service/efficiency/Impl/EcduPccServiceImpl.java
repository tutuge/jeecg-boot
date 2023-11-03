package org.jeecg.modules.cable.service.efficiency.Impl;

import org.jeecg.modules.cable.mapper.dao.efficiency.EcduPccMapper;
import org.jeecg.modules.cable.entity.efficiency.EcduPcc;
import org.jeecg.modules.cable.service.efficiency.EcduPccService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcduPccServiceImpl implements EcduPccService {
    @Resource
    EcduPccMapper ecduPccMapper;

    //getObject
    @Override
    public EcduPcc getObject(EcduPcc record) {
        return ecduPccMapper.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcduPcc record) {
        return ecduPccMapper.insert(record);
    }

    //deletePassEcCompanyIdAndTypeId
    @Override
    public Integer delete(EcduPcc record) {
        return ecduPccMapper.delete(record);
    }

}
