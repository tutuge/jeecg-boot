package org.jeecg.modules.cable.service.userQuality.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userQuality.EcquParameter;
import org.jeecg.modules.cable.mapper.dao.userQuality.EcquParameterMapper;
import org.jeecg.modules.cable.service.userQuality.EcquParameterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcquParameterServiceImpl implements EcquParameterService {
    @Resource
    EcquParameterMapper ecquParameterMapper;


    @Override
    public List<EcquParameter> getList(EcquParameter record) {
        return ecquParameterMapper.getList(record);
    }


    @Override
    public long getCount(EcquParameter record) {
        return ecquParameterMapper.getCount(record);
    }


    @Override
    public EcquParameter getObject(EcquParameter record) {
        return ecquParameterMapper.getObject(record);
    }

    // insert
    @Override
    public Integer insert(EcquParameter record) {
        return ecquParameterMapper.insert(record);
    }

    // updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcquParameter record) {
        return ecquParameterMapper.updateById(record);
    }


    @Override
    public Integer deleteByPrimaryKey(Integer ecbudmId) {
        return ecquParameterMapper.deleteById(ecbudmId);
    }

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcquParameter> getListGreaterThanSortId(EcquParameter record) {
        return ecquParameterMapper.getListGreaterThanSortId(record);
    }

    // getObjectPassEcqulIdAndEcbusId
    @Override
    public EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record) {
        return ecquParameterMapper.getObjectPassEcqulIdAndEcbusId(record);
    }


    @Override
    public EcquParameter getLatestObject(EcquParameter record) {
        return ecquParameterMapper.getLatestObject(record);
    }

}
