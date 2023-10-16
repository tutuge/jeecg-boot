package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuQuoted;

import java.util.List;

public interface EcuQuotedService {
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
