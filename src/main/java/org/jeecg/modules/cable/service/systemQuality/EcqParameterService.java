package org.jeecg.modules.cable.service.systemQuality;


import org.jeecg.modules.cable.entity.systemQuality.EcqParameter;

import java.util.List;

public interface EcqParameterService {

    List<EcqParameter> getList(EcqParameter record);


    long getCount(EcqParameter record);


    EcqParameter getObject(EcqParameter record);


    Integer insert(EcqParameter record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcqParameter record);


    Integer deleteByPrimaryKey(Integer ecbudmId);


    List<EcqParameter> getListGreaterThanSortId(EcqParameter record);

    //getObjectPassEcqulIdAndEcbusId
    EcqParameter getObjectPassEcqulIdAndEcbusId(EcqParameter record);

    EcqParameter getLatestObject(EcqParameter record);
}
