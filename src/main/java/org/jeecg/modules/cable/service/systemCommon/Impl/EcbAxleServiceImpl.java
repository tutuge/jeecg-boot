package org.jeecg.modules.cable.service.systemCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemCommon.EcbAxle;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcbAxleMapper;
import org.jeecg.modules.cable.service.systemCommon.EcbAxleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbAxleServiceImpl implements EcbAxleService {
    @Resource
    EcbAxleMapper ecbAxleMapper;


    @Override
    public List<EcbAxle> getList(EcbAxle record) {
        return ecbAxleMapper.getList(record);
    }


    @Override
    public long getCount(EcbAxle record) {
        return ecbAxleMapper.getCount(record);
    }


    @Override
    public EcbAxle getObject(EcbAxle record) {
        return ecbAxleMapper.getObject(record);
    }


    @Override
    public Integer insert(EcbAxle record) {
        return ecbAxleMapper.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcbAxle record) {
        return ecbAxleMapper.updateById(record);
    }


    @Override
    public Integer deleteByPrimaryKey(Integer ecbaId) {
        return ecbAxleMapper.deleteById(ecbaId);
    }

    
    @Override
    public List<EcbAxle> getListGreaterThanSortId(EcbAxle record) {
        return ecbAxleMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassAxleName
    @Override
    public EcbAxle getObjectPassAxleName(EcbAxle record) {
        return ecbAxleMapper.getObjectPassAxleName(record);
    }


    @Override
    public EcbAxle getLatestObject(EcbAxle record) {
        return ecbAxleMapper.getLatestObject(record);
    }

}
