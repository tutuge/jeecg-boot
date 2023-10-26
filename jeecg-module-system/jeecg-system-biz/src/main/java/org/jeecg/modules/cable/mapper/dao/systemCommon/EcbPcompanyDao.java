package org.jeecg.modules.cable.mapper.dao.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbPcompanyDao {
    //getList
    List<EcbPcompany> getList(EcbPcompany record);

    //getCount
    long getCount(EcbPcompany record);

    //getObject
    EcbPcompany getObject(EcbPcompany record);

    //insert
    Integer insert(EcbPcompany record);

    //update
    Integer update(EcbPcompany record);

    //delete
    Integer delete(EcbPcompany record);
}
