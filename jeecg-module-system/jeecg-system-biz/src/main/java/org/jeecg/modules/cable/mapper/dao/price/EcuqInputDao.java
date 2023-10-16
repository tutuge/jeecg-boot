package org.jeecg.modules.cable.mapper.dao.price;

import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuqInputDao {
    //getList
    List<EcuqInput> getList(EcuqInput record);

    //getCount
    long getCount(EcuqInput record);

    //getObject
    EcuqInput getObject(EcuqInput record);

    //insert
    int insert(EcuqInput record);

    //getListGreaterThanSortId
    List<EcuqInput> getListGreaterThanSortId(EcuqInput record);

    int delete(EcuqInput record);

    int update(EcuqInput record);

    //getLatestObject
    EcuqInput getLatestObject(EcuqInput record);
}
