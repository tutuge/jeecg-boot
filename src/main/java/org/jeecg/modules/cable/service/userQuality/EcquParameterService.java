package org.jeecg.modules.cable.service.userQuality;

import org.jeecg.modules.cable.entity.userQuality.EcquParameter;

import java.util.List;

public interface EcquParameterService {

    List<EcquParameter> getList(EcquParameter record);


    long getCount(EcquParameter record);


    EcquParameter getObject(EcquParameter record);


    Integer insert(EcquParameter record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcquParameter record);


    Integer deleteByPrimaryKey(Integer ecbudmId);

    
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);

    EcquParameter getLatestObject(EcquParameter record);
}
