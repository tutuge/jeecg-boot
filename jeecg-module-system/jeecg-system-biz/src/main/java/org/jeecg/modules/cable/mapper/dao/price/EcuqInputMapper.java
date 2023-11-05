package org.jeecg.modules.cable.mapper.dao.price;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.price.EcuqInput;

import java.util.List;

@Mapper
public interface EcuqInputMapper {
    //getList
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
