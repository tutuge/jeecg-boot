package org.jeecg.modules.cable.service.systemCommon;


import org.jeecg.modules.cable.entity.systemCommon.EcbAxle;

import java.util.List;

public interface EcbAxleService {

    List<EcbAxle> getList(EcbAxle record);


    long getCount(EcbAxle record);


    EcbAxle getObject(EcbAxle record);


    Integer insert(EcbAxle record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcbAxle record);


    Integer deleteByPrimaryKey(Integer ecbaId);

    
    List<EcbAxle> getListGreaterThanSortId(EcbAxle record);

    //getObjectPassAxleName
    EcbAxle getObjectPassAxleName(EcbAxle record);


    EcbAxle getLatestObject(EcbAxle record);
}
