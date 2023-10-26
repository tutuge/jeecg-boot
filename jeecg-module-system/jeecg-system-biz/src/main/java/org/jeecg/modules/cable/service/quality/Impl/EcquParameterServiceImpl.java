package org.jeecg.modules.cable.service.quality.Impl;

import org.jeecg.modules.cable.mapper.dao.quality.EcquParameterDao;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.service.quality.EcquParameterService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcquParameterServiceImpl implements EcquParameterService {
    @Resource
    EcquParameterDao ecquParameterDao;
    //getList
    @Override
    public List<EcquParameter> getList(EcquParameter record) {
        return ecquParameterDao.getList(record);
    }
    //getCount
    @Override
    public long getCount(EcquParameter record) {
        return ecquParameterDao.getCount(record);
    }
    //getObject
    @Override
    public EcquParameter getObject(EcquParameter record)
    {
        return ecquParameterDao.getObject(record);
    }
    //insert
    @Override
    public Integer insert(EcquParameter record)
    {
        return ecquParameterDao.insert(record);
    }
    //updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcquParameter record) {
        return ecquParameterDao.updateByPrimaryKeySelective(record);
    }
    //deleteByPrimaryKey
    @Override
    public Integer deleteByPrimaryKey(Integer ecbudmId) {
       return ecquParameterDao.deleteByPrimaryKey(ecbudmId);
    }
    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcquParameter> getListGreaterThanSortId(EcquParameter record){
        return ecquParameterDao.getListGreaterThanSortId(record);
    }
    //getObjectPassEcqulIdAndEcbusId
    @Override
    public EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record)
    {
        return ecquParameterDao.getObjectPassEcqulIdAndEcbusId(record);
    }
    //getLatestObject
    @Override
    public EcquParameter getLatestObject(EcquParameter record) {
        return ecquParameterDao.getLatestObject(record);
    }

}
