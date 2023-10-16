package org.jeecg.modules.cable.mapper.dao.quality;

import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcquParameterDao {
    //getList
    List<EcquParameter> getList(EcquParameter record);
    //getCount
    long getCount(EcquParameter record);
    //getObject
    EcquParameter getObject(EcquParameter record);
    //insert
    int insert(EcquParameter record);
    //updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(EcquParameter record);
    //deleteByPrimaryKey
    int deleteByPrimaryKey(int ecqulId);
    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);
    //getLatestObject
    EcquParameter getLatestObject(EcquParameter record);
}
