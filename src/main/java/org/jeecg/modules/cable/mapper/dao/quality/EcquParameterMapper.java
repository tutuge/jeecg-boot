package org.jeecg.modules.cable.mapper.dao.quality;

import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcquParameterMapper {

    List<EcquParameter> getList(EcquParameter record);

    long getCount(EcquParameter record);

    EcquParameter getObject(EcquParameter record);

    Integer insert(EcquParameter record);
    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcquParameter record);

    Integer deleteByPrimaryKey(Integer ecqulId);
    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);

    EcquParameter getLatestObject(EcquParameter record);
}
