package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuDeliveryDao {
    //getList
    List<EcbuDelivery> getList(EcbuDelivery record);

    //getCount
    long getCount(EcbuDelivery record);

    //getObject
    EcbuDelivery getObject(EcbuDelivery record);

    //insert
    int insert(EcbuDelivery record);

    int update(EcbuDelivery record);

    int delete(EcbuDelivery record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuDelivery> getListGreaterThanSortId(EcbuDelivery record);

    //getObjectPassDeliveryName
    EcbuDelivery getObjectPassDeliveryName(EcbuDelivery record);

    //getLatestObject
    EcbuDelivery getLatestObject(EcbuDelivery record);
}
