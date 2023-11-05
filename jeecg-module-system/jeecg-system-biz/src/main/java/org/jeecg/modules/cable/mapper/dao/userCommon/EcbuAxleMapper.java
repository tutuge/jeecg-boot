package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;

import java.util.List;

@Mapper
public interface EcbuAxleMapper {

    List<EcbuAxle> getList(EcbuAxle record);

    // getCount
    long getCount(EcbuAxle record);


    EcbuAxle getObject(EcbuAxle record);

    // insert
    Integer insert(EcbuAxle record);

    // updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcbuAxle record);

    // deleteByPrimaryKey
    Integer deleteByPrimaryKey(Integer ecbuaId);

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuAxle> getListGreaterThanSortId(EcbuAxle record);

    // getObjectPassAxleName
    EcbuAxle getObjectPassAxleName(EcbuAxle record);

    // getLatestObject
    EcbuAxle getLatestObject(EcbuAxle record);
}
