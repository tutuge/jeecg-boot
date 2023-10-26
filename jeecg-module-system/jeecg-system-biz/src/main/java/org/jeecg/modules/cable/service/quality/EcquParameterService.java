package org.jeecg.modules.cable.service.quality;

import org.jeecg.modules.cable.entity.quality.EcquParameter;

import java.util.List;

public interface EcquParameterService {
    //getList
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
    Integer deleteByPrimaryKey(Integer ecbudmId);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);
    //getLatestObject
    EcquParameter getLatestObject(EcquParameter record);
}
