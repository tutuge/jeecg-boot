package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuqInput;

import java.util.List;

public interface EcuqInputService {

    List<EcuqInput> getList(EcuqInput record);


    long getCount(EcuqInput record);


    EcuqInput getObject(EcuqInput record);


    Integer insert(EcuqInput record);

    //getListGreaterThanSortId
    List<EcuqInput> getListGreaterThanSortId(EcuqInput record);

    Integer delete(EcuqInput record);

    Integer update(EcuqInput record);


    EcuqInput getLatestObject(EcuqInput record);

    void reduceSort(Integer ecuqId, Integer sortId);
}
