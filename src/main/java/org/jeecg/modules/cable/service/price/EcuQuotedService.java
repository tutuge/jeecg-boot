package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuQuoted;

import java.util.List;

public interface EcuQuotedService {

    List<EcuQuoted> getList(EcuQuoted record);

    //getCount
    long getCount(EcuQuoted record);

    //getObject
    EcuQuoted getObject(EcuQuoted record);

    //getLatestObject
    EcuQuoted getLatestObject(EcuQuoted record);

    //insert
    Integer insert(EcuQuoted record);

    //deleteByPrimaryKey
    Integer deleteByPrimaryKey(Integer ecuqId);

    Integer update(EcuQuoted record);
}
