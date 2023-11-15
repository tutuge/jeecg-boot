package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuqInput;

import java.util.List;

public interface EcuqInputService {

    List<EcuqInput> getList(EcuqInput record);


    Long getCount(EcuqInput record);


    EcuqInput getObject(EcuqInput record);

    EcuqInput getById(Integer ecuqiId);


    Integer insert(EcuqInput record);


    List<EcuqInput> getListGreaterThanSortId(EcuqInput record);

    Integer delete(Integer id);

    Integer update(EcuqInput record);


    EcuqInput getLatestObject(EcuqInput record);

    void reduceSort(Integer ecuqId, Integer sortId);
}
