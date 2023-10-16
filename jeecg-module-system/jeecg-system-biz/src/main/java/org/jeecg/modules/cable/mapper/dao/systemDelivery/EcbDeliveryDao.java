package org.jeecg.modules.cable.mapper.dao.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbDeliveryDao {
    //getList
    List<EcbDelivery> getList(EcbDelivery record);

    //getCount
    long getCount(EcbDelivery record);

    //getObject
    EcbDelivery getObject(EcbDelivery record);

    //insert
    int insert(EcbDelivery record);

    //update
    int update(EcbDelivery record);

    //delete
    int delete(EcbDelivery record);
}
