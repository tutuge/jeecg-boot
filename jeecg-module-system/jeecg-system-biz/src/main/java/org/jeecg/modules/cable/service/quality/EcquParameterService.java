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
    int insert(EcquParameter record);

    //updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(EcquParameter record);

    //deleteByPrimaryKey
    int deleteByPrimaryKey(int ecbudmId);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);
    //getLatestObject
    EcquParameter getLatestObject(EcquParameter record);
}
