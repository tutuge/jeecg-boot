package org.jeecg.modules.cable.service.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;

import java.util.List;

public interface EcblUnitService {

    List<EcblUnit> getList(EcblUnit record);


    long getCount(EcblUnit record);


    EcblUnit getObject(EcblUnit record);


    Integer insert(EcblUnit record);

    //update
    Integer update(EcblUnit record);

    //delete
    Integer delete(EcblUnit record);
}
