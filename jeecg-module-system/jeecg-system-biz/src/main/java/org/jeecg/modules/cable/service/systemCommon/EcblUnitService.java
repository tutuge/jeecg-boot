package org.jeecg.modules.cable.service.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;

import java.util.List;

public interface EcblUnitService {
    //getList
    List<EcblUnit> getList(EcblUnit record);

    //getCount
    long getCount(EcblUnit record);

    //getObject
    EcblUnit getObject(EcblUnit record);

    //insert
    int insert(EcblUnit record);

    //update
    int update(EcblUnit record);

    //delete
    int delete(EcblUnit record);
}
