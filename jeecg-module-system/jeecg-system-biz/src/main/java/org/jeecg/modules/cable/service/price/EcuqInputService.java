package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuqInput;

import java.util.List;

public interface EcuqInputService {

    List<EcuqInput> getList(EcuqInput record);

    //getCount
    long getCount(EcuqInput record);

    //getObject
    EcuqInput getObject(EcuqInput record);

    //insert
    Integer insert(EcuqInput record);

    //getListGreaterThanSortId
    List<EcuqInput> getListGreaterThanSortId(EcuqInput record);

    Integer delete(EcuqInput record);

    Integer update(EcuqInput record);

    //getLatestObject
    EcuqInput getLatestObject(EcuqInput record);
}
