package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;

import java.util.List;

public interface EcbuAxleService {
    //getList
    List<EcbuAxle> getList(EcbuAxle record);

    //getCount
    long getCount(EcbuAxle record);

    //getObject
    EcbuAxle getObject(EcbuAxle record);

    //insert
    int insert(EcbuAxle record);

    //updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(EcbuAxle record);

    //deleteByPrimaryKey
    int deleteByPrimaryKey(int ecbuaId);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuAxle> getListGreaterThanSortId(EcbuAxle record);

    //getObjectPassAxleName
    EcbuAxle getObjectPassAxleName(EcbuAxle record);

    //getLatestObject
    EcbuAxle getLatestObject(EcbuAxle record);
}
