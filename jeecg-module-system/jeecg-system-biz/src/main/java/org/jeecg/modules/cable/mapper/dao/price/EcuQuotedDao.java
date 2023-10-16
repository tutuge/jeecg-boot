package org.jeecg.modules.cable.mapper.dao.price;

import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuQuotedDao {
    //getList
    List<EcuQuoted> getList(EcuQuoted record);

    //getCount
    long getCount(EcuQuoted record);

    //getObject
    EcuQuoted getObject(EcuQuoted record);

    //getLatestObject
    EcuQuoted getLatestObject(EcuQuoted record);

    //insert
    int insert(EcuQuoted record);

    //deleteByPrimaryKey
    int deleteByPrimaryKey(int ecuqId);

    int update(EcuQuoted record);
}
