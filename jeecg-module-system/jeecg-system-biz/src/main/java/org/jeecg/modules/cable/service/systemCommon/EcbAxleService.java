package org.jeecg.modules.cable.service.systemCommon;


import org.jeecg.modules.cable.entity.systemCommon.EcbAxle;

import java.util.List;

public interface EcbAxleService {
    //getList
    List<EcbAxle> getList(EcbAxle record);

    //getCount
    long getCount(EcbAxle record);

    //getObject
    EcbAxle getObject(EcbAxle record);

    //insert
    Integer insert(EcbAxle record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcbAxle record);

    //deleteByPrimaryKey
    Integer deleteByPrimaryKey(Integer ecbaId);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbAxle> getListGreaterThanSortId(EcbAxle record);

    //getObjectPassAxleName
    EcbAxle getObjectPassAxleName(EcbAxle record);

    //getLatestObject
    EcbAxle getLatestObject(EcbAxle record);
}
