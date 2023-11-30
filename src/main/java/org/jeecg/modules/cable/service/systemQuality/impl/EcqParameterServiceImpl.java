package org.jeecg.modules.cable.service.systemQuality.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemQuality.EcqParameter;
import org.jeecg.modules.cable.mapper.dao.systemQuality.EcqParameterMapper;
import org.jeecg.modules.cable.service.systemQuality.EcqParameterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcqParameterServiceImpl implements EcqParameterService {
    @Resource
    EcqParameterMapper ecqParameterMapper;


    @Override
    public List<EcqParameter> getList(EcqParameter record) {
        return ecqParameterMapper.getList(record);
    }


    @Override
    public long getCount(EcqParameter record) {
        return ecqParameterMapper.getCount(record);
    }


    @Override
    public EcqParameter getObject(EcqParameter record) {
        return ecqParameterMapper.getObject(record);
    }

    // insert
    @Override
    public Integer insert(EcqParameter record) {
        return ecqParameterMapper.insert(record);
    }

    // updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcqParameter record) {
        return ecqParameterMapper.updateById(record);
    }


    @Override
    public Integer deleteByPrimaryKey(Integer ecbudmId) {
        return ecqParameterMapper.deleteById(ecbudmId);
    }

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcqParameter> getListGreaterThanSortId(EcqParameter record) {
        return ecqParameterMapper.getListGreaterThanSortId(record);
    }

    // getObjectPassEcqulIdAndEcbusId
    @Override
    public EcqParameter getObjectPassEcqulIdAndEcbusId(EcqParameter record) {
        return ecqParameterMapper.getObjectPassEcqulIdAndEcbusId(record);
    }


    @Override
    public EcqParameter getLatestObject(EcqParameter record) {
        return ecqParameterMapper.getLatestObject(record);
    }

    @Override
    public void delete(Integer ecqpId) {
        ecqParameterMapper.deleteById(ecqpId);
    }

}
