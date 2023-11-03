package org.jeecg.modules.cable.service.quality.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.mapper.dao.quality.EcquParameterMapper;
import org.jeecg.modules.cable.service.quality.EcquParameterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcquParameterServiceImpl implements EcquParameterService {
    @Resource
    EcquParameterMapper ecquParameterMapper;

    // getList
    @Override
    public List<EcquParameter> getList(EcquParameter record) {
        return ecquParameterMapper.getList(record);
    }

    // getCount
    @Override
    public long getCount(EcquParameter record) {
        return ecquParameterMapper.getCount(record);
    }

    // getObject
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
        return ecquParameterMapper.updateByPrimaryKeySelective(record);
    }

    // deleteByPrimaryKey
    @Override
    public Integer deleteByPrimaryKey(Integer ecbudmId) {
        return ecquParameterMapper.deleteByPrimaryKey(ecbudmId);
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

    // getLatestObject
    @Override
    public EcquParameter getLatestObject(EcquParameter record) {
        return ecquParameterMapper.getLatestObject(record);
    }

}
