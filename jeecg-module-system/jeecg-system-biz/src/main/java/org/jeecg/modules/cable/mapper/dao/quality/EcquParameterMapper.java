package org.jeecg.modules.cable.mapper.dao.quality;

import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcquParameterMapper {

    List<EcquParameter> getList(EcquParameter record);
    //getCount
    long getCount(EcquParameter record);
    //getObject
    EcquParameter getObject(EcquParameter record);
    //insert
    Integer insert(EcquParameter record);
    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcquParameter record);
    //deleteByPrimaryKey
    Integer deleteByPrimaryKey(Integer ecqulId);
    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);
    //getLatestObject
    EcquParameter getLatestObject(EcquParameter record);
}
