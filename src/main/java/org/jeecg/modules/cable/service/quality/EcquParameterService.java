package org.jeecg.modules.cable.service.quality;

import org.jeecg.modules.cable.entity.quality.EcquParameter;

import java.util.List;

public interface EcquParameterService {

    List<EcquParameter> getList(EcquParameter record);


    long getCount(EcquParameter record);


    EcquParameter getObject(EcquParameter record);


    Integer insert(EcquParameter record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcquParameter record);


    Integer deleteByPrimaryKey(Integer ecbudmId);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);

    EcquParameter getLatestObject(EcquParameter record);
}
