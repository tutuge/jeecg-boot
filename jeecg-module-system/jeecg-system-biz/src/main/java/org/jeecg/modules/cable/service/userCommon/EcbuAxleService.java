package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;

import java.util.List;

public interface EcbuAxleService {

    List<EcbuAxle> getList(EcbuAxle record);

    //getCount
    long getCount(EcbuAxle record);

    //getObject
    EcbuAxle getObject(EcbuAxle record);

    //insert
    Integer insert(EcbuAxle record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcbuAxle record);

    //deleteByPrimaryKey
    Integer deleteByPrimaryKey(Integer ecbuaId);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuAxle> getListGreaterThanSortId(EcbuAxle record);

    //getObjectPassAxleName
    EcbuAxle getObjectPassAxleName(EcbuAxle record);

    //getLatestObject
    EcbuAxle getLatestObject(EcbuAxle record);
}
